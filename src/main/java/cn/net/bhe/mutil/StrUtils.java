package cn.net.bhe.mutil;

import java.util.Random;

public final class StrUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String SLASH = "/";
    public static final String DOT = ".";
    public static final String ZERO = "0";
    private static final Random RANDOM = new Random();

    public static String randomChs(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char) (0x4e00 + RANDOM.nextInt(0x9fa5 - 0x4e00 + 1)));
        }
        return sb.toString();
    }

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

    public static String trimLeft(String str, String remove) {
        return trim(str, remove, true, false);
    }

    public static String trimRight(String str, String remove) {
        return trim(str, remove, false, true);
    }

    public static String trim(String str, String remove) {
        return trim(str, remove, true, true);
    }

    public static String trim(String str, String remove, boolean left, boolean right) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        int begin = 0;
        int end = str.length() - 1;
        /* left */
        int i = 0;
        int u = 0;
        if (left) {
            while (i < str.length()) {
                if (str.charAt(i++) == remove.charAt(u++)) {
                    if (u == remove.length()) {
                        begin += remove.length();
                        u = 0;
                    }
                } else {
                    break;
                }
            }
        }
        /* right */
        int j = str.length() - 1;
        int v = remove.length() - 1;
        if (right) {
            while (j >= 0 && j >= i) {
                if (str.charAt(j--) == remove.charAt(v--)) {
                    if (v == -1) {
                        end -= remove.length();
                        v = 0;
                    }
                } else {
                    break;
                }
            }
        }
        return str.substring(begin, end + 1);
    }

}
