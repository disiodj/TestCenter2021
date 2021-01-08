package at.dimoco.tcmc.client.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorCodesTest {

    @Test
    public void testErrorCode() {
        assertNotNull(ErrorCodes.values());
        assertEquals(ErrorCodes.GENERIC_SYSTEM_ERROR, ErrorCodes.valueOf("generic_system_error".toUpperCase()));
        assertEquals("GENERIC_SYSTEM_ERROR", ErrorCodes.GENERIC_SYSTEM_ERROR.name());
    }

    @Test
    public void testLevelName() {
        assertNotNull(ErrorCodes.Level.values());
        assertEquals("FATAL", ErrorCodes.Level.FATAL.name());
        assertEquals("SYSTEM", ErrorCodes.GENERIC_SYSTEM_ERROR.getLevel().name());
        assertEquals(ErrorCodes.Level.FATAL, ErrorCodes.Level.valueOf("fatal".toUpperCase()));
    }

}
