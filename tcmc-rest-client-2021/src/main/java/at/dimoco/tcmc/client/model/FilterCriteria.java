package at.dimoco.tcmc.client.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Wrapper class used to include a filter when requesting SMS history
 */
public class FilterCriteria {

    /**
     * Application enum
     */
    private Application application;
    private String dateFrom;
    private String dateTo;
    private String msisdnFrom;
    private String msisdnTo;
    private SmsDirection smsDirection;
    private String msisdn;

    /**
     * Empty constructor
     */
    public FilterCriteria() {
        // default constructor
    }

    /**
     * 
     * @param application Application enum
     * @param dateFrom Format: 2014-05-18 for May 5th, 2014
     * @param dateTo Format: 2014-05-18 for May 5th, 2014
     * @param msisdnFrom Msisdn or parts of it Example: 436649699034 / 9034
     * @param msisdnTo Msisdn or pars of it Example: 436649699034 / 9034
     * @param smsDirection Direction enum incoming or outgoing
     * @param msisdn Msisdn to search history for
     */
    public FilterCriteria(Application application, String dateFrom, String dateTo, String msisdnFrom, String msisdnTo,
            SmsDirection smsDirection, String msisdn) {
        this.application = application;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.msisdnFrom = msisdnFrom;
        this.msisdnTo = msisdnTo;
        this.smsDirection = smsDirection;
        this.msisdn = msisdn;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getMsisdnFrom() {
        return msisdnFrom;
    }

    public void setMsisdnFrom(String msisdnFrom) {
        this.msisdnFrom = msisdnFrom;
    }

    public String getMsisdnTo() {
        return msisdnTo;
    }

    public void setMsisdnTo(String msisdnTo) {
        this.msisdnTo = msisdnTo;
    }

    public SmsDirection getSmsDirection() {
        return smsDirection;
    }

    public void setSmsDirection(SmsDirection smsDirection) {
        this.smsDirection = smsDirection;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * Checks for null inputs and makes request query param for sms box (sms history) request
     * 
     * @return Map of
     */
    public Map<String, String> makeSmsBoxRequestParams() {
        Map<String, String> params = new HashMap<>();
        if (hasValue(getDateFrom())) {
            params.put("dateFrom", getDateFrom());
        }
        if (hasValue(getDateTo())) {
            params.put("dateTo", getDateTo());
        }
        if (hasValue(getMsisdnFrom())) {
            params.put("msisdnFrom", getMsisdnFrom());
        }
        if (hasValue(getMsisdnTo())) {
            params.put("msisdnTo", getMsisdnTo());
        }
        if (hasValue(getMsisdn())) {
            params.put("msisdn", getMsisdn());
        }
        addDirectionType(params);
        addApplication(params);
        return params;
    }

    private void addDirectionType(Map<String, String> params) {
        if (getSmsDirection() != null && hasValue(getSmsDirection().getDirectionType())) {
            params.put("direction", getSmsDirection().getDirectionType());
        }
    }

    private void addApplication(Map<String, String> params) {
        if (getApplication() != null && hasValue(getApplication().getApplicationType())) {
            params.put("application", getApplication().getApplicationType());
        }
    }

    private boolean hasValue(String val) {
        return val != null && !val.isEmpty();
    }
}
