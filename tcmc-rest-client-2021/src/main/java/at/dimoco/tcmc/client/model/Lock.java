package at.dimoco.tcmc.client.model;


import at.dimoco.tcmc.client.util.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Lock information
 */
public class Lock extends BaseEntity {

    private String operator;
    private String user;
    private String sessionId;
    private String msisdn;
    private boolean locked;
    private String application;
    @SerializedName("status")
    private LockStatus status;
    private boolean canUnlock;
    private boolean myLock;

    public Lock(Application application, String operator) {
        this.application = application.getApplicationType();
        this.operator = operator;
    }

    /**
     * Empty constructor
     */
    public Lock() {
        // default constructor
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LockStatus getStatus() {
        return status;
    }

    public void setStatus(LockStatus status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public boolean isCanUnlock() {
        return canUnlock;
    }

    public void setCanUnlock(boolean canUnlock) {
        this.canUnlock = canUnlock;
    }

    public boolean isMyLock() {
        return myLock;
    }

    public void setMyLock(boolean myLock) {
        this.myLock = myLock;
    }

    /**
     * Lock model serializer. Serializes model to json representation
     */
    public static class LockSerializer implements JsonSerializer<Lock> {

        @Override
        public JsonElement serialize(Lock lock, Type type,
            JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.add("application", new JsonPrimitive(StringUtil.checkStringValue(lock.getApplication())));
            result.add("operator", new JsonPrimitive(StringUtil.checkStringValue(lock.getOperator())));
            result.add("user", new JsonPrimitive(StringUtil.checkStringValue(lock.getUser())));
            result.add("sessionId", new JsonPrimitive(StringUtil.checkStringValue(lock.getSessionId())));
            result.add("msisdn", new JsonPrimitive(StringUtil.checkStringValue(lock.getMsisdn())));

            return result;
        }
    }
}


