package at.dimoco.tcmc.client.model;

/**
 * Application enum holds possible values for applications.
 * Values could be:
 * <ul>
 * <li>tcui</li>
 * <li>ditesto</li>
 * </ul>
 */
public enum Application {

    TCUI("tcui"),
    DITESTO("ditesto");

    private String applicationType;

    Application(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationType() {
        return applicationType;
    }
}
