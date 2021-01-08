package at.dimoco.tcmc.client.model;

/**
 * Balance check model
 */
public class BalanceCheck extends BaseEntity {

    private String sent;
    private String msisdn;
    private String event;

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
