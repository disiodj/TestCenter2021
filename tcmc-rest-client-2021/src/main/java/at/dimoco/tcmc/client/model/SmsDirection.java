package at.dimoco.tcmc.client.model;

/**
 * Direction enum holds possible values for Sms direction.
 * Values could be:
 * <ul>
 * <li>incoming</li>
 * <li>outgoing</li>
 * </ul>
 */
public enum SmsDirection {

    INCOMING("incoming"),
    OUTGOING("outgoing");

    private String directionType;

    SmsDirection(String directionType) {
        this.directionType = directionType;
    }

    public String getDirectionType() {
        return directionType;
    }
}
