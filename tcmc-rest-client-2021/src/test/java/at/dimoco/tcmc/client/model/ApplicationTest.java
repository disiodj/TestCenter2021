package at.dimoco.tcmc.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ApplicationTest {

    private static final String TCUI_APP_TYPE = "tcui";

    @Test
    public void shouldPassApplicationBasics() {
        assertNotNull(Application.values());
        assertEquals(Application.TCUI, Application.valueOf(TCUI_APP_TYPE.toUpperCase()));
        assertEquals("TCUI", Application.TCUI.name());
        assertEquals(TCUI_APP_TYPE, Application.TCUI.getApplicationType());
    }

}
