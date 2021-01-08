package at.dimoco.tcmc.client.model;

/**
 * Operator lock status
 */
public class LockStatus extends BaseEntity {

    private String msisdn;
    private boolean locked;
    private String user;
    private String operator;
    private String application;
    private String sessionId;

    /**
     * Empty constructor
     */
    public LockStatus() {
        // default constructor
    }

    public LockStatus(String msisdn, boolean locked, String user, String operator, String application) {
        this.msisdn = msisdn;
        this.locked = locked;
        this.user = user;
        this.operator = operator;
        this.application = application;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
