package at.dimoco.tcmc.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SmsDirectionTest {

    private static final String INCOMING_TYPE = "incoming";

    @Test
    public void shouldPassApplicationBasics() {
        assertNotNull(SmsDirection.values());
        assertEquals(SmsDirection.INCOMING, SmsDirection.valueOf(INCOMING_TYPE.toUpperCase()));
        assertEquals("INCOMING", SmsDirection.INCOMING.name());
        assertEquals(INCOMING_TYPE, SmsDirection.INCOMING.getDirectionType());
    }

}
