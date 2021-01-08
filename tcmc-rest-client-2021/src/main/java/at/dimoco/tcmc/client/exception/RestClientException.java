package at.dimoco.tcmc.client.exception;

/**
 * General REST client exception
 */
public class RestClientException extends BaseException {

    private static final long serialVersionUID = 1L;

    public RestClientException(String message) {
        super(ErrorCodes.GENERIC_SYSTEM_ERROR, message, null);
    }

    public RestClientException(String message, Object[] parameters) {
        super(ErrorCodes.GENERIC_SYSTEM_ERROR, message, parameters);
    }

    public RestClientException(ErrorCodes code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

    public RestClientException(ErrorCodes code, String message, Object[] parameters, Throwable cause) {
        super(code, message, parameters, cause);
    }

}
