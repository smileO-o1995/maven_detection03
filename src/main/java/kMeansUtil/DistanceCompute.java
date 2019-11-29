package kMeansUtil;

import java.util.List;

/**
 * 求两个点的欧式距离
 * @author wen
 * @date 2019/11/28 0028-10:51
 */
public class DistanceCompute {

    /**
     * 求欧式距离
     */
    public double getEuclideanDis(Point p1, Point p2) {
        double count_dis = 0;
        float[] p1_local_array = p1.getlocalArray();
        float[] p2_local_array = p2.getlocalArray();

        if (p1_local_array.length != p2_local_array.length) {
            throw new IllegalArgumentException("length of array must be equal!");
        }

        for (int i = 0; i < p1_local_array.length; i++) {
            count_dis += Math.pow(p1_local_array[i] - p2_local_array[i], 2);
        }

        return Math.sqrt(count_dis);
    }

    public double getEuclideanDis2(float[] arr1, float[] arr2){
        double count_dis = 0;
        for (int i = 0; i < arr1.length; i++) {
            count_dis += Math.pow(arr1[i] - arr2[i], 2);
        }
        return Math.sqrt(count_dis);
    }

    public double pow2(double a, double b){
        double rst = Math.pow((a - b), 2);
        return rst;
    }

    public double mean(float[] in){
        double sum = 0;
        for(float item : in){
            sum += item;
        }
        return sum/(in.length);
    }

    /*
     * 变异性量数: 全局标准差
     */
    public double variance(float[] in) {
        double mean = mean(in);
        double sumPerPow = 0;
        for (int i = 0; i < in.length; i++) {
            sumPerPow += pow2(in[i] , mean);
        }
        sumPerPow = Math.sqrt(sumPerPow/in.length);
        return sumPerPow;
    }

    /**
     * 计算两个簇之间的最短距离是否小于threshold
     */
    public boolean between2dis(List<Point> pointSet1, List<Point> pointSet2, double threshold){

        double dist;
        for(Point point1 : pointSet1){
            for(Point point2 : pointSet2){
                dist = getEuclideanDis(point1, point2);
                if(dist < threshold){
                    return true;
                }
            }
        }
        return false;
    }
}
