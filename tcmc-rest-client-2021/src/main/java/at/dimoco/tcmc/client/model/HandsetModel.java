package at.dimoco.tcmc.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Handset model entity
 */
public class HandsetModel extends BaseEntity {

    @SerializedName("class")
    private String claz;
    private String manufactorer; // rest typo
    private String model;

    public String getClaz() {
        return claz;
    }

    public String getModel() {
        return model;
    }
    
    public String getManufactorer() {
        return manufactorer;
    }

    public void setClaz(String claz) {
        this.claz = claz;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public void setManufactorer(String manufactorer) {
        this.manufactorer = manufactorer;
    }

}
