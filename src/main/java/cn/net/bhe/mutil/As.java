package cn.net.bhe.mutil;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class As {

    public static void isTrue(boolean bool) {
        isTrue(bool, StrUtils.EMPTY);
    }

    public static void isTrue(boolean bool, Class<? extends RuntimeException> exClass) {
        isTrue(bool, StrUtils.EMPTY, exClass);
    }

    public static void isTrue(boolean bool, String msg) {
        isTrue(bool, msg, IllegalArgumentException.class);
    }

    public static void isTrue(Supplier<Boolean> supplier, String msg) {
        isTrue(supplier.get(), msg);
    }

    public static void isTrue(boolean bool, String msg, Class<? extends RuntimeException> exClass) {
        if (!bool) {
            RuntimeException exInstance;
            try {
                exInstance = exClass.getConstructor(String.class).newInstance(msg);
            } catch (NoSuchMethodException |
                     InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            throw exInstance;
        }
    }

}
