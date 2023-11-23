package cn.net.bhe.mutil;

public final class StrUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String toString(Object value) {
        return toString(value, EMPTY);
    }

    public static String toString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    public static int compareTo(String val1, String val2) {
        return toString(val1, EMPTY).length() - toString(val2, EMPTY).length();
    }

}
