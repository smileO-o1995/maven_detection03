package domain;

/**
 * @author wen
 * @date 2019/11/26 0026-21:46
 */
public class NetInfo {
    //g3222_14,1,1,15,inputPin
    int verId;
    String netName;
    int CC0;
    int CC1;
    int CO;
    int CC;//在计算CC值的时候需要判断CC0与CC1是否为0 或者CO是否为-1
    String type;
    boolean comform;//如果CC0与CC1是否为0 或者CO是否为-1，则需要将comform设定为true，表示提前将该节点设定为木马可疑节点
    //同时，需要改变CC0/CC1/CO的值，让其在第三限位。
    String gateName;//记录输出节点逻辑单元的名称

    public boolean isComform() {
        return comform;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public int getVerId() {
        return verId;
    }

    public void setVerId(int verId) {
        this.verId = verId;
    }

    public boolean getComform() {
        return comform;
    }

    public void setComform(boolean comform) {
        this.comform = comform;
    }

    public void setCC(int CC) {
        this.CC = CC;
    }

    public int getCC() {
        return CC;
    }

    public String getNetName() {
        return netName;
    }

    public String getType() {
        return type;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCC0() {
        return CC0;
    }

    public int getCC1() {
        return CC1;
    }

    public int getCO() {
        return CO;
    }

    public void setCC0(int CC0) {
        this.CC0 = CC0;
    }

    public void setCC1(int CC1) {
        this.CC1 = CC1;
    }

    public void setCO(int CO) {
        this.CO = CO;
    }
}
