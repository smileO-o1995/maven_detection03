package service;

import domain.NetResult;

/**
 * @author wen
 * @date 2019/12/23 0023-10:58
 */
public interface ResultService {

    //将数据存入test_count表格中
    public void saveNetResult(NetResult netResult, String tableName);
}
