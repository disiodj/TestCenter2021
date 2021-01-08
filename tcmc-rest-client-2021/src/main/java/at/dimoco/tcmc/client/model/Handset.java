package at.dimoco.tcmc.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Handset model
 */
public class Handset extends BaseEntity {

    private String hostname;
    private String serialNumber;
    private SimCard simcard;
    private String vncport;
    private String password;
    @SerializedName("status")
    private HandsetStatus handsetStatus;
    @SerializedName("model")
    private HandsetModel handsetModel;
    @SerializedName("lock")
    private Lock lock;
    private boolean isLocked;
    private String lockedBy;
    private Balance balance;


    public String getHostName() {
        return hostname;
    }

    public void setHostName(String hostName) {
        this.hostname = hostName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public SimCard getSimCard() {
        return simcard;
    }

    public void setSimCard(SimCard simCard) {
        this.simcard = simCard;
    }

    public String getVncPort() {
        return vncport;
    }

    public void setVncPort(String vncPort) {
        this.vncport = vncPort;
    }
    
    public String toString() {
        return hostname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HandsetStatus getHandsetStatus() {
        return handsetStatus;
    }

    public void setHandsetStatus(HandsetStatus handsetStatus) {
        this.handsetStatus = handsetStatus;
    }
    
    public HandsetModel getHandsetModel() {
        return handsetModel;
    }
    
    public void setHandsetModel(HandsetModel handsetModel) {
        this.handsetModel = handsetModel;
    }
    
    public Lock getLock() {
        return lock;
    }
    
    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}
