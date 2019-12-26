package service;

import domain.NetResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author wen
 * @date 2019/12/23 0023-10:58
 */
public interface ResultService {

    //将数据存入test_count表格中
    public void saveNetResult(NetResult netResult, String tableName);

    public ArrayList<Map<String,Object>> selectAllNetResult(String tableName);
}
