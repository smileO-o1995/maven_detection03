package domain;

/**
 * @author wen
 * @date 2019/12/20 0020-15:11
 */
public class NetResult {
    private int id;
    private String netlistName;
    private boolean trojanType;
    private boolean rstState;
    private boolean normal;
    private String detectionRate;
    private String falseAlarmRate;
    private String user;
    private String location;
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public NetResult() {
    }

    public NetResult(String netlistName, boolean trojanType, boolean rstState,
                     boolean normal, String detectionRate, String falseAlarmRate,
                     String user, String location, String method) {
        this.netlistName = netlistName;
        this.trojanType = trojanType;
        this.rstState = rstState;
        this.normal = normal;
        this.detectionRate = detectionRate;
        this.falseAlarmRate = falseAlarmRate;
        this.user = user;
        this.location = location;
        this.method = method;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNetlistName(String netlistName) {
        this.netlistName = netlistName;
    }

    public void setTrojanType(boolean trojanType) {
        this.trojanType = trojanType;
    }

    public void setRstState(boolean rstState) {
        this.rstState = rstState;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public void setDetectionRate(String detectionRate) {
        this.detectionRate = detectionRate;
    }

    public void setFalseAlarmRate(String falseAlarmRate) {
        this.falseAlarmRate = falseAlarmRate;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getNetlistName() {
        return netlistName;
    }

    public boolean isTrojanType() {
        return trojanType;
    }

    public boolean isRstState() {
        return rstState;
    }

    public boolean isNormal() {
        return normal;
    }

    public String getDetectionRate() {
        return detectionRate;
    }

    public String getFalseAlarmRate() {
        return falseAlarmRate;
    }

    public String getUser() {
        return user;
    }

    public String getLocation() {
        return location;
    }
}
