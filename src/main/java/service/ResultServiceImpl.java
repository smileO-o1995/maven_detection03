package service;

import dao.TestCount;
import dao.TestCountImpl;
import domain.NetResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author wen
 * @date 2019/12/23 0023-11:00
 */
public class ResultServiceImpl implements ResultService{
    @Override
    public void saveNetResult(NetResult netResult, String tableName) {
        if(tableName.equals("test_count")){
            TestCount testCount = new TestCountImpl();
            testCount.saveNetList(netResult);
        }
    }

    @Override
    public ArrayList<Map<String,Object>> selectAllNetResult(String tableName) {
        ArrayList<Map<String,Object>> rtnData = null;
        if(tableName.equals("test_count")){
            TestCount testCount = new TestCountImpl();
            rtnData = testCount.selectAll();
        }
        return rtnData;
    }
}
