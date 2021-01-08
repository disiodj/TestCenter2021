package at.dimoco.tcmc.client.model;


import at.dimoco.tcmc.client.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Testcenter model
 */
public class Testcenter extends BaseEntity {

    private List<Handset> handsets;
    private String hostname;
    private String name;
    private String sshPort;
    private String contactAddress;
    private String contactPhone;
    private String contactName;
    private String contactCompany;
    private TestcenterModel model;
    private String status;
    private String contactEmail;
    private String contactMessenger;
    private String contactOfficeHours;
    private List<TestcenterDetailedStatus> detailedStatus;
    // used to provide ping response time in ms. This value does not come from the api directly
    private String pingResponseTime;
    private boolean maintenance;

    public List<Handset> getHandsets() {
        return handsets;
    }

    public void setHandsets(List<Handset> handsets) {
        this.handsets = handsets;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sslPort) {
        this.sshPort = sslPort;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(String contactCompany) {
        this.contactCompany = contactCompany;
    }

    public TestcenterModel getModel() {
        return model;
    }

    public void setModel(TestcenterModel model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMessenger() {
        return contactMessenger;
    }

    public void setContactMessenger(String contactMessenger) {
        this.contactMessenger = contactMessenger;
    }

    public String getContactOfficeHours() {
        return contactOfficeHours;
    }

    public void setContactOfficeHours(String contactOfficeHours) {
        this.contactOfficeHours = contactOfficeHours;
    }

    public List<TestcenterDetailedStatus> getDetailedStatus() {
        return detailedStatus;
    }

    public void setDetailedStatus(List<TestcenterDetailedStatus> detailedStatus) {
        this.detailedStatus = detailedStatus;
    }

    public String getPingResponseTime() {
        return pingResponseTime;
    }

    public void setPingResponseTime(String pingResponseTime) {
        this.pingResponseTime = pingResponseTime;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    /**
     * Testcenter serializer. Serializes model to json representation
     */
    public static class TestcenterSerializer implements JsonSerializer<Testcenter> {

        @Override
        public JsonElement serialize(Testcenter testcenter, Type type,
                JsonSerializationContext jsonSerializationContext) {

            JsonObject result = new JsonObject();
            result.add("id", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getId())));
            result.add("name", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getName())));
            result.add("hostname", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getHostname())));
            result.add("sshPort", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getSshPort())));
            result.add("contactAddress", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactAddress())));
            result.add("contactPhone", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactPhone())));
            result.add("contactName", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactName())));
            result.add("contactCompany", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactCompany())));
            result.add("contactEmail", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactEmail())));
            result.add("contactMessenger", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactMessenger())));
            result.add("contactOfficeHours", new JsonPrimitive(StringUtil.checkStringValue(testcenter.getContactOfficeHours())));
            // serialize handsets
            if (testcenter.getHandsets() != null && !testcenter.getHandsets().isEmpty()) {
                JsonArray handsetsJson = new JsonArray();
                for (Handset handset : testcenter.getHandsets()) {
                    JsonObject handsetObj = new JsonObject();
                    handsetObj.addProperty("id", handset.getId());
                    handsetsJson.add(handsetObj);
                }
                result.add("handsets", handsetsJson);
            }
            // serialize model
            if (testcenter.getModel() != null && testcenter.getModel().getId() != null) {
                JsonObject testcenterModel = new JsonObject();
                testcenterModel.addProperty("id", testcenter.getModel().getId());
                result.add("model", testcenterModel);
            }

            return result;
        }
    }
}
