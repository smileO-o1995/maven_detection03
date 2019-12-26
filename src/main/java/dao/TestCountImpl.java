package dao;

import domain.NetResult;
import util.DruidConnection;
import util.NetListRead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
