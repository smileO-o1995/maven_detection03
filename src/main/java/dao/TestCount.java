package dao;

import domain.NetResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author wen
 * @date 2019/12/23 0023-11:03
 */
public interface TestCount {
    //1、存储一个netList的数据
    public void saveNetList(NetResult netResult);

    //2、读取所有的netList的数据
    public ArrayList<Map<String,Object>> selectAll();
}
