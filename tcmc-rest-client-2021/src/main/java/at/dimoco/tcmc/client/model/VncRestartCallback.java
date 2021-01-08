package at.dimoco.tcmc.client.model;


/**
 * Restart VNC callback entity
 */
public class VncRestartCallback extends BaseEntity {

    private String status;
    private String timeout;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
