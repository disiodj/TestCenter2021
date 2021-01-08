package at.dimoco.tcmc.client.exception;


/**
 * Service error codes with level
 */
public enum ErrorCodes {

    GENERIC_SYSTEM_ERROR(10, Level.SYSTEM),

    HTTP_CALL_ERROR(11, Level.SYSTEM),

    JSON_PARSE_ERROR(13, Level.SYSTEM),

    ENTITY_NOT_FOUND_ERROR(14, Level.USER), 
    
    SECURITY_CONFIG(23, Level.FATAL);

    int code;
    Level level;

    ErrorCodes(int code, Level level) {
        this.code = code;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public Level getLevel() {
        return level;
    }

    public enum Level {
        USER, SYSTEM, FATAL
    }

}


