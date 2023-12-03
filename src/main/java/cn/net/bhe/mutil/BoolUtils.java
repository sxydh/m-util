package cn.net.bhe.mutil;

import java.util.Random;

public class BoolUtils {

    private static final Random RANDOM = new Random();

    public static boolean random() {
        return RANDOM.nextBoolean();
    }

}
