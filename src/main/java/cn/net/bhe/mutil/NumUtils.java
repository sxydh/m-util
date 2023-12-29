package cn.net.bhe.mutil;

import java.util.Random;

public class NumUtils {

    public static final Integer MINUS_ONE = -1;
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer FOUR = 4;
    public static final Integer FIVE = 5;

    private static final Random RANDOM = new Random();

    public static int ranInt() {
        return RANDOM.nextInt();
    }

}
