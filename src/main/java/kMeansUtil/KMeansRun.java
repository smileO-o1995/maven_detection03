package kMeansUtil;

import kMeansUtil.Cluster;
import kMeansUtil.DistanceCompute;

import java.util.*;

/**
 * @author wen
 * @date 2019/11/28 0028-10:52
 */
public class KMeansRun {
    private int kNum;                             //簇的个数
    private int iterNum = 10;                     //迭代次数

    private int iterMaxTimes = 100000;            //单次迭代最大运行次数
    private int iterRunTimes = 0;                 //单次迭代实际运行次数
    private float disDiff = (float) 0.01;         //单次迭代终止条件，两次运行中类中心的距离差

    private static List<Point> pointList = null;  //用于存放，原始数据集所构建的点集
    private DistanceCompute disC = new DistanceCompute();
    private int len = 0;                          //用于记录每个数据点的维度

    public KMeansRun(int k, List<Point> pointList) {
        this.pointList = null;
        this.kNum = k;
        this.pointList = pointList;
        //检查规范
        check();
        this.len = pointList.get(0).getlocalArray().length;
    }

    /**
     * 检查规范
     */
    private void check() {
        if (kNum == 0){
            throw new IllegalArgumentException("k must be the number > 0");
        }
        if (pointList == null){
            throw new IllegalArgumentException("program can't get real data");
        }
    }

    /**
     * 随机选取中心点（真实存在的测试点），构建成中心类。（后续需要修改）
     * kNum为簇的个数
     * pointList为数据集
     */
    private Set<Cluster> chooseCenterCluster() {
        Set<Cluster> clusterSet = new HashSet<Cluster>();
        Random random = new Random();
        for (int id = 0; id < kNum; ) {
            Point point = pointList.get(random.nextInt(pointList.size()));
            // 用于标记是否已经选择过该数据。
            boolean flag =true;
            for (Cluster cluster : clusterSet) {
                if (cluster.getCenter().equals(point)) {
                    flag = false;
                }
            }
            // 如果随机选取的点没有被选中过，则生成一个cluster
            if (flag) {
                Cluster cluster =new Cluster(id, point);
                clusterSet.add(cluster);
                id++;
            }
        }
        return clusterSet;
    }

    /**
     * 固定选取中心点（真实存在的测试点），构建成中心类。（后续需要修改）
     * kNum为簇的个数
     * pointList为数据集
     */
    private Set<Cluster> setCenterCluster() {
        Set<Cluster> clusterSet = new HashSet<Cluster>();
        //找横坐标最大值maxCC与纵坐标最大值maxCO
        float maxCC = 0;
        float maxCO = 0;
        for(Point point : pointList){
            if(point.getlocalArray()[0] > maxCC){
                maxCC = point.getlocalArray()[0];
            }
            if(point.getlocalArray()[1] > maxCO){
                maxCO = point.getlocalArray()[1];
            }
        }
        float[] data = {0, 0};
        float[] data1 = {maxCC, 0};
        float[] data2 = {0, maxCO};

        Point point = new Point(-1, data);
        Cluster cluster = new Cluster(0, new Point(-1, data));
        Cluster cluster1 = new Cluster(1, new Point(-1, data1));
        Cluster cluster2 = new Cluster(2, new Point(-1, data2));
        clusterSet.add(cluster);
        clusterSet.add(cluster1);
        clusterSet.add(cluster2);

        return clusterSet;
    }

    /**
     * 为每个点分配一个类！
     */
    public void cluster(Set<Cluster> clusterSet){
        // 计算每个点到K个中心的距离，并且为每个点标记类别号
        for (Point point : pointList) {
            float min_dis = Integer.MAX_VALUE;
            for (Cluster cluster : clusterSet) {
                float tmp_dis = (float) Math.min(disC.getEuclideanDis(point, cluster.getCenter()), min_dis);
                if (tmp_dis != min_dis) {
                    min_dis = tmp_dis;
                    point.setClusterId(cluster.getId());
                    point.setDist(min_dis);
                }
            }
        }
        // 新清除原来所有的类中成员。把所有的点，分别加入每个类别
        for (Cluster cluster : clusterSet) {
            cluster.getMembers().clear(); //清除所有成员
            for (Point point : pointList) {
                if (point.getClusterid()==cluster.getId()) {
                    cluster.addPoint(point);
                }
            }
        }
    }

    /**
     * 计算每个类的中心位置！
     */
    public boolean calculateCenter(Set<Cluster> clusterSet) {
        boolean ifNeedIter = false;
        for (Cluster cluster : clusterSet) {
            List<Point> point_list = cluster.getMembers();
            float[] sumAll =new float[len];
            // 所有点，对应各个维度进行求和
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < point_list.size(); j++) {
                    sumAll[i] += point_list.get(j).getlocalArray()[i];
                }
            }
            // 计算平均值
            for (int i = 0; i < sumAll.length; i++) {
                sumAll[i] = (float) sumAll[i]/point_list.size();
            }
            // 计算两个新、旧中心的距离，如果任意一个类中心移动的距离大于dis_diff则继续迭代。
            if(disC.getEuclideanDis(cluster.getCenter(), new Point(sumAll)) > disDiff){
                ifNeedIter = true;
            }
            // 设置新的类中心位置
            cluster.setCenter(new Point(sumAll));
        }
        return ifNeedIter;
    }

    /**
     * 运行 k-means
     */
    public Set<Cluster> run() {
        Set<Cluster> clusterSet= setCenterCluster(); //仅使用一次
        boolean ifNeedIter = true;
        while (ifNeedIter) {
            cluster(clusterSet);
            ifNeedIter = calculateCenter(clusterSet);
            iterRunTimes ++ ;
        }
        return clusterSet;
    }

    /**
     * 返回实际运行次数
     */
    public int getIterTimes() {
        return iterRunTimes;
    }
}
