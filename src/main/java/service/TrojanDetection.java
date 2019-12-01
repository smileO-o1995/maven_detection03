package service;

import domain.NetInfo;

import java.util.*;

import kMeansUtil.Cluster;
import kMeansUtil.KMeansRun;
import kMeansUtil.Point;
import kMeansUtil.ReCluster;
import util.CaculateUtil;

/**
 * step3:木马检测
 * @author wen
 * @date 2019/11/28 0028-14:37
 */
public class TrojanDetection {
    /**
    servlet的入口函数
     */
    public Map<String, Object> detectEnter(ArrayList<NetInfo> netInfos){
        /*
        1、遍历netInfos:netInfo，根据netInfo.comform的值，将false对应的对象摘取出来，放入另一个容器中
           ArrayList<Point> datas:用于输入到kmeans网络中，进行木马检测
           其中，Point.id表示该节点在netInfos中的位置
         */
        ArrayList<Point> datas = new ArrayList<>();//初始化容器

        for(int i = 0; i < netInfos.size(); i++){
            NetInfo netInfo = netInfos.get(i);
            if(!netInfo.getComform()){
                //(2)装载容器2
                float CC = netInfo.getCC();
                float CO = netInfo.getCO();
                float[] data = {CC, CO};
                Point point = new Point(i, data);
                datas.add(point);
            }
        }

        /*
        2、将datas数据输入到KMeansRun中，开始进行kmeans聚类分析
         */
        KMeansRun kRun = new KMeansRun(3, datas);
        Set<Cluster> clusterSet = kRun.run();
        //***测试运行位置的代码
        for(Cluster cluster : clusterSet){
            System.out.println(cluster);
        }

        /*
        3、对聚类的结果进行阈值判断
        返回一个结果集：ArrayList<Integer> infos
        记录被判断为木马节点的point_id
         */
        ReCluster reCluster = new ReCluster();
        ArrayList<Integer> infos = reCluster.reClusterMethod(clusterSet);

        /*
        4、遍历infos，将netInfos对应位置的netInfo.comform转变为true;
         */
        for(int info : infos){
            //***测试运行位置的代码
            System.out.println(info);
            netInfos.get(info).setComform(true);
        }

        /*
        5、封装返回的数据
         */
        Map<String, Object> rstData = exactResData2(netInfos);
        return rstData;
    }

    public Map<String,Object> exactResData2(List<NetInfo> netInfos){
        Map<String, Object> map = new HashMap<>();
        //设置标签数组
        String[] legend = {"trojanNet","normalNet"};
        //0、设置数据集合
        ArrayList<int[]> trojanNetData = new ArrayList<>();
        ArrayList<int[]> normalNetData = new ArrayList<>();

        //1、检测出的木马节点名称集合
        ArrayList<String> netSet = new ArrayList<>();
        ArrayList<String> netSet2 = new ArrayList<>();
        //2、成功检测到的木马触发节点数的条件是：netInfo.trojanType = "isTrojan" && netInfo.comform = true
        int successNum = 0;
        //3、源数据中的木马激励电路节点数originTrojan和木马有效载荷数originPayLoad，分别对应netInfo.trojanType = "isTrojan"或"isPayLoad"
        int originTrojan = 0;
//        int originPayLoad = 0;
        //5、被误判为木马的节点：netInfo.trojanType != "isTrojan"或!="isPayLoad"而netInfo.comfore = true
        ArrayList<String> errorNet = new ArrayList<>();

        //遍历数组
        for(NetInfo netInfo : netInfos){
            //0、设置分组数据集
            int[] arr = new int[2];
            arr[0] = netInfo.getCC();
            arr[1] = netInfo.getCO();

            if(netInfo.getComform()){ //被检测为木马节点
                //0、添加分组数据
                trojanNetData.add(arr);
                //1、检测出的木马节点名称结合添加
                netSet.add(netInfo.getNetName());
                netSet2.add(netInfo.getNetName() + "_" + netInfo.getVerId());
                //2、成功检测到的木马数
                if("TjTrigger".equals(netInfo.getType())){
                    successNum++;
                }
                //5、被误判的木马节点
                if(!("TjTrigger".equals(netInfo.getType())) && !("TjPayload".equals(netInfo.getType())) ){
                    errorNet.add(netInfo.getNetName());
                }
                //***测试运行位置的代码
                System.out.println(netInfo.getType());
            }else{
                //0
                normalNetData.add(arr);
            }
            //3、源数据中的木马激励
            if("TjTrigger".equals(netInfo.getType())){
                originTrojan++;
            }
//            //3、木马负载电路
//            if("TjPayload".equals(netInfo.getType())){
//                originPayLoad++;
//            }
        }

        //6、首先定性判断检测是否正确 有——无， 有——有， 无——有，无——无
        if(originTrojan == 0){//无——
            if(trojanNetData.size() != 0){
                //无——有
                map.put("stat", false);
                map.put("msg", "检测错误，该无木马网表被检测为木马网表");

            }else{
                map.put("stat",true);
                map.put("normal",true);
                map.put("msg", "检测正确，该网表被判断为无木马网表");
            }
        }else{//有
            if(successNum == 0){
                map.put("stat", false);
                map.put("msg","检测错误，该有木马网表被检测为无木马网表");

            }else {
                map.put("stat", true);
                map.put("normal", false);
                map.put("msg","检测正确，该有木马网表被检测为有木马网表");
                //4、计算
                //4.1计算成功检测到木马触发电路的正确率
                String successRate = CaculateUtil.myPercent(successNum, originTrojan);
                //4.2计算误判率
                String errorRate = CaculateUtil.myPercent(errorNet.size(), netInfos.size());
                map.put("successRate", successRate);
                map.put("errorRate", errorRate);
            }
        }

        ArrayList<ArrayList<int[]>> sets = new ArrayList<>();
        sets.add(trojanNetData);
        sets.add(normalNetData);

        map.put("legend", legend);
        map.put("sets", sets);
        map.put("netSet", netSet);
        map.put("netSet2",netSet2);

        return map;
    }
}
