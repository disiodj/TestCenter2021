package at.dimoco.tcmc.client.model;

import at.dimoco.tcmc.client.util.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Sms message entity
 */
public class SmsMessage extends BaseEntity {

    private String from;
    private String to;
    private String application;
    private String message;
    private String received;
    private String mobileReceived;
    private String direction;
    private boolean handled;
    private boolean online;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getMobileReceived() {
        return mobileReceived;
    }

    public void setMobileReceived(String mobileReceived) {
        this.mobileReceived = mobileReceived;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Sms message serializer. Serializes model to json representation
     */
    public static class SmsMessageSerializer implements JsonSerializer<SmsMessage> {

        @Override
        public JsonElement serialize(SmsMessage smsMessage, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.add("from", new JsonPrimitive(StringUtil.checkStringValue(smsMessage.getFrom())));
            result.add("to", new JsonPrimitive(StringUtil.checkStringValue(smsMessage.getTo())));
            result.add("application", new JsonPrimitive(StringUtil.checkStringValue(smsMessage.getApplication())));
            result.add("message", new JsonPrimitive(StringUtil.checkStringValue(smsMessage.getMessage())));

            return result;
        }
    }
}
