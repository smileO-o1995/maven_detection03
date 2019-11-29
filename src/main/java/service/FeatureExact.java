package service;

import domain.NetInfo;
import domain.VertexNode;
import util.NetListRead;
import util.OListDG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * step2:特征提取的后端
 * @author wen
 * @date 2019/11/26 0026-16:53
 */
public class FeatureExact {
    //提取特征向量集合
    // 每个对象：g3222_14,1,1,15,inputPin NetInfo的集合
    public ArrayList<NetInfo> exactFeature(NetListRead netList, String savePath){
        //1、创建十字链表对象，其中netList.vertexInfo是net节点信息，存放的数据有netName,gateType,netType
        //netList.edgesInfo是连接边信息，存放的数据有tailvex,headvex,inStation
        OListDG listDG = new OListDG(netList.vertexInfo,netList.edgesInfo);

        //3、初始化顶点表中的CC0\CC1\CO值
        listDG.InitialC(netList.inputInfo , netList.outputInfo);

        //释放对象：netList
        netList = null;

        //2、寻找强连通分支，同一环中的节点的id相同，可以查看顶点表中的id参数，获得网表的拓扑结构，
        //返回值表示id共有多少层，其大小：比id的最大值加1
        int count = listDG.ConnectBranch();
        System.out.println(count);

        //4、将相同id编号的节点放入同一列表中，再将所有链表放入一个列表中，排序依据为id值从大到小，作为计算CC0\CC1的遍历列表
        List<List<Integer>> rankListforCC = listDG.RankListforCC(count);

        //5、查看网表的结构，先将数据保存到txt文件中
//        try {
//            listDG.readOrthogonalList(savePath, 1);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

        //6、调用CC0与CC1的入口函数
        listDG.rankEnter(rankListforCC);

        //查看网表的结构，先将数据CC0和CC1保存到txt文件中
//        try {
//            listDG.readOrthogonalList(savePath, 2);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

        //7、相同id编号的节点放入同一列表中，在将所有链表放入一个列表中，排序依据为id值从小到大，作为计算CO的遍历列表
        List<List<Integer>> rankListforCO = listDG.RankListforCO(count);

        //8、调用CO的入口函数
        listDG.rankOut(rankListforCO);

        //查看网表的结构，先将数据CC0和CC1保存到txt文件中
        /**
         * 20、该方法得到的得到一个文件，该文件剔除了孤立的不可测试点、常量点、没有使用的输入端口、没有使用的输出端口
         * 然后得到的是：节点名（节点的索引号是否会更加简单）、CC0\CC1\CO、节点类型
         */
        ArrayList<NetInfo> netInfos = null;
        try {
            netInfos = listDG.readOrthogonalList1(savePath);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return netInfos;
    }

    public Map<String,Object> exactResData(List<NetInfo> netInfos){
        //设置标签数组
        String[] legend = {"noTrojan","inputPin","outputPin","TjTrigger","TjPayload"};
        //设置数据集合
        ArrayList<int[]> noTrojanData = new ArrayList<>();
        ArrayList<int[]> inputPinData = new ArrayList<>();
        ArrayList<int[]> outputPinData = new ArrayList<>();
        ArrayList<int[]> TjTriggerData = new ArrayList<>();
        ArrayList<int[]> TjPayloadData = new ArrayList<>();

        for(NetInfo netInfo : netInfos){
            int[] arr = new int[2];
            arr[0] = netInfo.getCC();
            arr[1] = netInfo.getCO();
            switch (netInfo.getType()){
                case "noTrojan" : noTrojanData.add(arr); break;
                case "inputPin" : inputPinData.add(arr); break;
                case "outputPin" : outputPinData.add(arr); break;
                case "TjTrigger" : TjTriggerData.add(arr); break;
                case "TjPayload" : TjPayloadData.add(arr); break;
                default: System.out.println("出现误差");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("legend", legend);
        ArrayList<ArrayList<int[]>> sets = new ArrayList<>();
        sets.add(noTrojanData);
        sets.add(inputPinData);
        sets.add(outputPinData);
        sets.add(TjTriggerData);
        sets.add(TjPayloadData);
        map.put("sets", sets);

        return map;
    }
}
