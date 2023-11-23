package cn.net.bhe.mutil;

import java.util.function.Supplier;

public class As {

    public static void isTrue(boolean bool) {
        isTrue(bool, StrUtils.EMPTY);
    }

    public static void isTrue(Supplier<Boolean> supplier, String msg) {
        isTrue(supplier.get(), msg);
    }

    public static void isTrue(boolean bool, String msg) {
        if (!bool) {
            throw new IllegalArgumentException(msg);
        }
    }

}
