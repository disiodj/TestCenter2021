package at.dimoco.tcmc.client.model;


/**
 * Simcard model
 */
public class SimCard extends BaseEntity {

    private String balance;
    private boolean balanceCheck;
    private String msisdn;
    private PaymentType paymentType;
    private Operator operator;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean isBalanceCheck() {
        return balanceCheck;
    }

    public void setBalanceCheck(boolean balanceCheck) {
        this.balanceCheck = balanceCheck;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

}


