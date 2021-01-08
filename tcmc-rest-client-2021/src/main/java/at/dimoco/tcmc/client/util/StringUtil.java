package at.dimoco.tcmc.client.util;

/**
 * Util class to manipulate Strings
 */
public class StringUtil {
    
    private StringUtil() {
        // private constructor of utility class
    }

    /**
     * Checks null and empty values
     * @param value Value to check
     * @return Value or empty String
     */
    public static String checkStringValue(String value){
        return value != null && !value.isEmpty() ? value : "";
    }
}
