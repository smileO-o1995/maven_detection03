package kMeansUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 簇对象
 * @author wen
 * @date 2019/11/28 0028-10:47
 */
public class Cluster {
    private int id;//簇标识
    private Point center; //簇中心
    private List<Point> members = new ArrayList<>();//簇中的测试点成员

    public Cluster(int id, Point center) {
        this.id = id;
        this.center = center;
    }

    public Cluster(int id, Point center, List<Point> members) {
        this.id = id;
        this.center = center;
        this.members = members;
    }

    public void addPoint(Point newPoint) {
        members.add(newPoint);
    }

    public int getId() {
        return id;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public List<Point> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        String toString = "Cluster \n" + "Cluster_id=" + this.id + ", center:{" + this.center.toString()+"}";
        for (Point point : members) {
            toString+="\n"+point.toString();
        }
        return toString+"\n";
    }
}
