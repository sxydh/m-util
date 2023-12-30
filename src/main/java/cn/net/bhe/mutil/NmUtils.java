package cn.net.bhe.mutil;

import java.util.Random;

public class NmUtils {

    private static final Random RANDOM = new Random();
    private static final char[] FAMILY_NAME_ARR;
    private static final int FAMILY_NAME_ALLOC_SUM;
    private static final int[] FAMILY_NAME_ALLOC;

    static {
        try {
            FAMILY_NAME_ARR = ZipUtils.deComp("NmUtils.FAMILY_NAME_ARR").toCharArray();
            FAMILY_NAME_ALLOC = new int[FAMILY_NAME_ARR.length];
            FAMILY_NAME_ALLOC[0] = FAMILY_NAME_ARR.length;
            for (int i = 1; i < FAMILY_NAME_ARR.length; i++) {
                FAMILY_NAME_ALLOC[i] = FAMILY_NAME_ALLOC[i - 1] + FAMILY_NAME_ARR.length - i;
            }
            FAMILY_NAME_ALLOC_SUM = FAMILY_NAME_ARR.length * (FAMILY_NAME_ARR.length + 1) / 2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomName() {
        return randomName(NumUtils.ONE + RANDOM.nextInt(NumUtils.TWO) + RANDOM.nextInt(NumUtils.THREE));
    }

    public static String randomName(int maxLen) {
        return randomName(maxLen, true);
    }

    public static String randomName(int maxLen, boolean isOrdered) {
        String familyName;
        if (isOrdered) {
            familyName = String.valueOf(FAMILY_NAME_ARR[ArrUtils.findRi(FAMILY_NAME_ALLOC, RANDOM.nextInt(FAMILY_NAME_ALLOC_SUM) + 1)]);
        } else {
            familyName = String.valueOf(FAMILY_NAME_ARR[RANDOM.nextInt(FAMILY_NAME_ARR.length)]);
        }
        int givenNameLen = maxLen - familyName.length();
        if (givenNameLen <= 0) {
            return familyName;
        }
        givenNameLen = RANDOM.nextInt(givenNameLen) + 1;
        String givenName = StrUtils.randomChs(givenNameLen);
        return familyName + givenName;
    }

}
