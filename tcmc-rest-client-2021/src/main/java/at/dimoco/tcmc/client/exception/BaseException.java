package at.dimoco.tcmc.client.exception;

/**
 * Base exception definition.
 * Defined by error code, message parameters and standard exception properties
 */

public class BaseException extends RuntimeException {

    /**
     * generated ID 
     */
    private static final long serialVersionUID = -3759892808338707060L;
    protected final ErrorCodes code;
    protected final Object[] parameters;

    public BaseException( String message ) {
        super( message );
        this.parameters = new Object[0];
        this.code = ErrorCodes.GENERIC_SYSTEM_ERROR;
    }

    public BaseException( ErrorCodes code, String message, Object[] parameters ) {
        super( message );
        this.code = code;
        this.parameters = parameters != null ? parameters.clone() : new Object[0];
    }

    public BaseException(  ErrorCodes code, String message, Object[] parameters, Throwable cause ) {
        super( message, cause );
        this.code = code;
        this.parameters = parameters != null ? parameters.clone() : new Object[0];
    }

    public ErrorCodes getCode() {
        return code;
    }

    public Integer getCodeNumber() {
        return code.getCode();
    }

    public String getName() {
        return code.name();
    }

    public ErrorCodes.Level getLevel() {
        return code.getLevel();
    }

    public Object[] getMessageParameters() {
        return parameters.clone();
    }

}
