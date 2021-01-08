package at.dimoco.tcmc.client.model;

/**
 * Model for TC detailed status
 */
public class TestcenterDetailedStatus {

    private String msg;
    private String status;
    private String type;
    private String ping;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }
}
