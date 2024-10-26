package cn.net.bhe.mutil;

import java.util.Random;

public final class StrUtils {

    public static final String EMPTY = "";
    public static final String ZERO = "0";
    public static final String UNDERSCORE = "_";
    public static final String PLUS = "+";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String SPACE = " ";

    public static final String SPACE_ENCODE = "%20";
    public static final String HELLO_WORLD = "Hello, World!";
    public static final String TMP = "tmp";

    private static final Random RANDOM = new Random();

    private static final char[] CHS_ARR;
    private static final String[] PHONE_CODE;

    static {
        try {
            CHS_ARR = ZipUtils.deComp("StrUtils.CHS_ARR").toCharArray();
            PHONE_CODE = ZipUtils.deComp("StrUtils.PHONE_CODE").split("#");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomChs(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(CHS_ARR[RANDOM.nextInt(CHS_ARR.length)]);
        }
        return sb.toString();
    }

    public static String randomNum(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomPhone() {
        String phoneCode = PHONE_CODE[RANDOM.nextInt(PHONE_CODE.length)];
        return phoneCode + randomNum(11 - phoneCode.length());
    }

    public static String randomEn(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char) ('a' + RANDOM.nextInt(26)));
        }
        return sb.toString().toUpperCase();
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

    public static String trim(String str) {
        return trim(str, SPACE);
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
