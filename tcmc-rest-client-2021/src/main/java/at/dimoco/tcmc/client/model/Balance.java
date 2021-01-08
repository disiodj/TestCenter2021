package at.dimoco.tcmc.client.model;

/**
 * Model class used for balance check
 */
public class Balance extends BaseEntity {

    private String creationDate;
    private String balance;
    private String creationTimestamp;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

}
