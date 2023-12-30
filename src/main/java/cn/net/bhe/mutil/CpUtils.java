package cn.net.bhe.mutil;

import java.util.Random;

public class CpUtils {

    private static final String[] CHN_CP;
    private static final Random RANDOM = new Random();

    static {
        try {
            CHN_CP = ZipUtils.deComp("CpUtils.CHN_CP").split("#");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String ranChnCp() {
        return CHN_CP[RANDOM.nextInt(CHN_CP.length)];
    }

}
