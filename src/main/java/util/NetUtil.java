package util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wen
 * @date 2019/11/26 0026-19:50
 */
public class NetUtil {
    /**
     * 获取网表中的木马节点
     * 返回为一个list<String>格式的数据
     */
    public static List<String> exactTrojanNet(List<List<String>> vertexInfo){
        ArrayList<String> list = new ArrayList<>();
        for(List<String> inList: vertexInfo){
            String category = inList.get(3);
            if("TjTrigger".equals(category)){
                list.add(inList.get(0));
            }
        }
        return list;
    }

    public static List<String> exactPayLoadNet(List<List<String>> vertexInfo){
        ArrayList<String> list = new ArrayList<>();
        for(List<String> inList: vertexInfo){
            String category = inList.get(3);
            if("TjPayload".equals(category)){
                list.add(inList.get(0));
            }
        }
        return list;
    }
}
