package kMeansUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * step3
 * @author wen
 * @date 2019/11/28 0028-16:25
 */
public class ReCluster {
    private DistanceCompute disC = new DistanceCompute();
    /**
     * 通过标准差重新对clusterSet进行划分
     * @param clusterSet : 为kmeans的聚类结果
     * @return
     */
    public ArrayList<Integer> reClusterMethod(Set<Cluster> clusterSet){
        ArrayList<Integer> rst = new ArrayList<>();
        /*
         1、遍历ClusterSet找到cluster.center.location距离原点最近的簇
         */
        Cluster[] clusters = new Cluster[3];
        int i = 0;
        int min = 0;
        int max = 0;
        double mindist = 0;
        double maxdist = 0;
        float[] zero = {0, 0}; //原点
        for(Cluster cluster : clusterSet){
            //获取当前簇的簇中心点
            float[] centerData = cluster.getCenter().getlocalArray();
            //计算centerData到原点的欧式距离
            double dist = disC.getEuclideanDis2(centerData, zero);
            if(i == 0){
               min = i;
               max = i;
               mindist = dist;
               maxdist = dist;
            }else{
                if(dist > maxdist){
                    max = i;
                    maxdist = dist;
                }
                if(dist < mindist){
                    min = i;
                    mindist = dist;
                }
            }
            //将cluster放入另一个容器中
            clusters[i] = cluster;
            i++;
        }
        int mid = 3 - min - max;

        /*
        2、已知clusters数组以及距离原点从近到远的min mid max
        现在：计算簇min中所有节点的标准差stdMin
         */
        List<Point> members = clusters[min].getMembers();//该簇的测试点
        int length = members.size();
        float[] nearest = new float[length * 2];
        i = 0;
        for(Point point : members){
            nearest[i++] = point.getlocalArray()[0];
            nearest[i++] = point.getlocalArray()[1];
        }
        double stdMin = disC.variance(nearest);
        nearest = null;

        double threshold = stdMin * 3;

        /*
        3、获取min和mid的最短距离，是否小于threshold，如果是，则返回true；如果不是则返回false
         */
        boolean less = disC.between2dis(clusters[min].getMembers(), clusters[mid].getMembers(), threshold);

        /*
        4、如果less为false，则将mid和max都看作是木马节点
         */
        if(!less){
            for(Point point: clusters[mid].getMembers()){
                rst.add(point.getId());
            }
            for(Point point: clusters[max].getMembers()){
                rst.add(point.getId());
            }
        }else{
            /*
            5、计算min和mid的标准差
             */
            length = members.size() + clusters[mid].getMembers().size();
            float[] nearest1 = new float[length * 2];
            i = 0;
            for(Point point : members){
                nearest1[i++] = point.getlocalArray()[0];
                nearest1[i++] = point.getlocalArray()[1];
            }
            for(Point point: clusters[mid].getMembers()){
                nearest1[i++] = point.getlocalArray()[0];
                nearest1[i++] = point.getlocalArray()[1];
            }
            stdMin = disC.variance(nearest1);
            nearest1 = null;
            threshold = stdMin * 3;
            /*
            6、获取min+mid与max之间的最短距离是否小于threshold
             */
            List<Point> countPoint = new ArrayList<>();
            countPoint.addAll(clusters[min].getMembers());
            countPoint.addAll(clusters[mid].getMembers());
            boolean less1 = disC.between2dis(countPoint, clusters[max].getMembers(), threshold);
            if(!less1){
                for(Point point: clusters[max].getMembers()){
                    rst.add(point.getId());
                }
            }
        }

        return rst;
    }
}
