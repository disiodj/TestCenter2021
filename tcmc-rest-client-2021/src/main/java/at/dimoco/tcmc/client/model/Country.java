package at.dimoco.tcmc.client.model;

/**
 * Country model
 */
public class Country extends BaseEntity {

    private String iso;
    private String name;
    private Handset handset;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Handset getHandset() {
        return handset;
    }

    public void setHandset(Handset handset) {
        this.handset = handset;
    }
}
