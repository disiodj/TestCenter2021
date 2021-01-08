package at.dimoco.tcmc.client.model;

/**
 * Component consisting of common fields for all sub entities
 */
public class BaseEntity {

    private String id;

    private String error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isError(){
        return error != null && !error.isEmpty();
    }
}
