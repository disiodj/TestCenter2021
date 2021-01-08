package at.dimoco.tcmc.client.model;

/**
 * Represents Handset status entity
 */

public class HandsetStatus extends BaseEntity {

    private String batteryLevel;
    private boolean isCharging;
    private String lastUpdated;
    private String networkType;
    private String signalStrength;
    private String simState;
    private String androidVersion;
    // additional field for /handsetstatus calls
    private String online;
    private boolean charging;
    private String appVersionStatus;
    private String appVersion;
    private String age;

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean getIsCharging() {
        return isCharging;
    }

    public void setCharging(boolean isCharging) {
        this.isCharging = isCharging;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getSimState() {
        return simState;
    }

    public void setSimState(String simState) {
        this.simState = simState;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setIsCharging(boolean isCharging){
        this.isCharging = isCharging;
    }

    public String getAppVersionStatus() {
        return appVersionStatus;
    }

    public void setAppVersionStatus(String appVersionStatus) {
        this.appVersionStatus = appVersionStatus;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}
