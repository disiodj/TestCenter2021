package at.dimoco.tcmc.client.model;


/**
 * Representation of Testcenter model
 */
public class TestcenterModel extends BaseEntity {

    private String manufactorer;
    private String model;

    public String getManufactorer() {
        return manufactorer;
    }

    public void setManufactorer(String manufactorer) {
        this.manufactorer = manufactorer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
