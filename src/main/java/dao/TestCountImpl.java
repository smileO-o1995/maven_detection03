package dao;

import domain.NetResult;
import util.DruidConnection;
import util.NetListRead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 2019/12/23 0023-11:05
 */
public class TestCountImpl implements TestCount{
    @Override
    public void saveNetList(NetResult netResult) {
        DruidConnection dbp = DruidConnection.getInstace();
        Connection con = null;
        PreparedStatement stmt = null;
//        ResultSet rs = null;

        try{
            con = dbp.getConnection();
            String sql ="";
            if(netResult.isTrojanType()){//源网表有木马
                if(netResult.isRstState()){//检测为有木马
                    sql = "INSERT INTO test_count (netlist_name, method, trojan_type, rst_state, normal, " +
                            "detection_rate, false_alarm_rate) VALUES (?,?,?,?,?,?,?)";

                    stmt = con.prepareStatement(sql);
                    stmt.setString(1,netResult.getNetlistName());
                    stmt.setString(2,netResult.getMethod());
                    stmt.setBoolean(3,netResult.isTrojanType());
                    stmt.setBoolean(4,netResult.isRstState());
                    stmt.setBoolean(5,netResult.isNormal());
                    stmt.setString(6,netResult.getDetectionRate());
                    stmt.setString(7,netResult.getFalseAlarmRate());
                }else{//检测为无木马
                    sql = "INSERT INTO test_count (netlist_name, method, trojan_type, rst_state) VALUES (?,?,?,?)";

                    stmt = con.prepareStatement(sql);
                    stmt.setString(1,netResult.getNetlistName());
                    stmt.setString(2,netResult.getMethod());
                    stmt.setBoolean(3,netResult.isTrojanType());
                    stmt.setBoolean(4,netResult.isRstState());
                }
            }else{//原网表无木马
                if(netResult.isRstState()){//检测为有木马
                    sql = "INSERT INTO test_count (netlist_name, method, trojan_type, rst_state) VALUES (?,?,?,?)";

                    stmt = con.prepareStatement(sql);
                    stmt.setString(1,netResult.getNetlistName());
                    stmt.setString(2,netResult.getMethod());
                    stmt.setBoolean(3,netResult.isTrojanType());
                    stmt.setBoolean(4,netResult.isRstState());
                }else{//检测为无木马
                    sql = "INSERT INTO test_count (netlist_name, method, trojan_type, rst_state, normal) VALUES (?,?,?,?,?)";

                    stmt = con.prepareStatement(sql);
                    stmt.setString(1,netResult.getNetlistName());
                    stmt.setString(2,netResult.getMethod());
                    stmt.setBoolean(3,netResult.isTrojanType());
                    stmt.setBoolean(4,netResult.isRstState());
                    stmt.setBoolean(5,netResult.isNormal());
                }

            }


            stmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
//                rs.close();
                stmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Map<String,Object>> selectAll() {
        DruidConnection dbp = DruidConnection.getInstace();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Map<String, Object>> rstData = new ArrayList<>();
        try{
            con = dbp.getConnection();
            String sql = "SELECT id, netlist_Name, trojan_type, method, rst_state, detection_rate, false_alarm_rate FROM test_count";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                Map<String, Object> map = new HashMap<>();
                int id = rs.getInt(1);
                String netlistName = rs.getString(2);
                Boolean trojanType = rs.getBoolean(3);
                String method = rs.getString(4);
                Boolean rstState = rs.getBoolean(5);
                String detectionRate = rs.getString(6);
                String falseAlarmRate = rs.getString(7);

                map.put("id", id);
                map.put("netlistName", netlistName);
                if(trojanType){
                    map.put("trojanType","是");
                    if(rstState){
                        map.put("detectionType", "木马网表");
                        map.put("rstState", "是");
                    }else{
                        map.put("detectionType", "普通网表");
                        map.put("rstState", "否");
                    }
                }else{
                    map.put("trojanType","否");
                    if(rstState){
                        map.put("detectionType", "普通网表");
                        map.put("rstState", "是");
                    }else{
                        map.put("detectionType", "木马网表");
                        map.put("rstState", "否");
                    }
                }
                map.put("method", method);
                map.put("detectionRate", detectionRate);
                map.put("falseAlarmRate", falseAlarmRate);
//                System.out.println(id + "  " + netlistName + "  " +trojanType + "   " + method+ "   "
//                        + rstState + "   " + detectionRate + "   " +falseAlarmRate);
                rstData.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                stmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        return rstData;
    }
}
