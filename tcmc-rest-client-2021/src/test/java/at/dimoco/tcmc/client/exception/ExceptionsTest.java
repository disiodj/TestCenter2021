package at.dimoco.tcmc.client.exception;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ExceptionsTest {

    private static final String ERROR_TEXT = "error";
    private static final ErrorCodes ERROR_CODE = ErrorCodes.GENERIC_SYSTEM_ERROR;

    @Test
    public void shouldPassControllerException() {
        BaseException ex1 = new BaseException(ERROR_TEXT);
        BaseException ex2 = new BaseException(ERROR_CODE, ERROR_TEXT, null);
        BaseException ex3 = new BaseException(ERROR_CODE, ERROR_TEXT, new Object[] {});
        BaseException ex4 = new BaseException(ERROR_CODE, ERROR_TEXT, null, new Exception());
        BaseException ex5 = new BaseException(ERROR_CODE, ERROR_TEXT, new Object[] {}, new Exception());
        assertException(ex1);
        assertException(ex2);
        assertException(ex3);
        assertException(ex4);
        assertException(ex5);
    }

    @Test
    public void shouldPassRestClientException() {
        RestClientException ex1 = new RestClientException(ERROR_TEXT);
        RestClientException ex2 = new RestClientException(ERROR_TEXT, new Object[] {});
        RestClientException ex3 = new RestClientException(ERROR_CODE, ERROR_TEXT, null);
        RestClientException ex4 = new RestClientException(ERROR_CODE, ERROR_TEXT, new Object[] {});
        RestClientException ex5 = new RestClientException(ERROR_CODE, ERROR_TEXT, null, new Exception());
        RestClientException ex6 = new RestClientException(ERROR_CODE, ERROR_TEXT, new Object[] {}, new Exception());
        assertException(ex1);
        assertException(ex2);
        assertException(ex3);
        assertException(ex4);
        assertException(ex5);
        assertException(ex6);
    }

    private void assertException(BaseException ex) {
        assertNotNull(ex);
        assertNotNull(ex.getMessage());
        assertNotNull(ex.getCode());
        assertNotNull(ex.getCodeNumber());
        assertNotNull(ex.getName());
        assertNotNull(ex.getMessageParameters());
        assertNotNull(ex.getLevel());
    }

}
