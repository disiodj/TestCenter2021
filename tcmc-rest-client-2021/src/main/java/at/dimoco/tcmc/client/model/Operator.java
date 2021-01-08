package at.dimoco.tcmc.client.model;

/**
 * Operator model
 */
public class Operator extends BaseEntity {

    private String internalName;

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return internalName;
    }
    
}
