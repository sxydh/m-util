package cn.net.bhe.mutil;

import java.util.Random;

public class WdUtils {

    private static final StringBuilder[] WORD_ARR;
    private static final Random RANDOM = new Random();

    static {
        try {
            String[] wordArr = ZipUtils.deComp("WdUtils.WORD_ARR").split("#");
            WORD_ARR = new StringBuilder[wordArr.length];
            for (int i = 0; i < wordArr.length; i++) {
                WORD_ARR[i] = new StringBuilder().append(wordArr[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String random() {
        return random(NumUtils.ONE, null, true);
    }

    public static String random(int len) {
        return random(len, null, true);
    }

    public static String random(int len, Character separator, boolean pascal) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (separator != null && i != 0) {
                ret.append(separator);
            }
            StringBuilder word = WORD_ARR[RANDOM.nextInt(WORD_ARR.length)];
            if (pascal) {
                word.setCharAt(0, Character.toUpperCase(word.charAt(0)));
            }
            ret.append(word);
        }
        return ret.toString();
    }

}
