package at.dimoco.tcmc.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Wraps lock call response from REST api
 */
public class LockStatusResponse extends BaseEntity {

    @SerializedName("status")
    private LockStatus status;

    public LockStatus getStatus() {
        return status;
    }

    public void setStatus(LockStatus status) {
        this.status = status;
    }
}
