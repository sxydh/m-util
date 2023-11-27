package cn.net.bhe.mutil;

import java.util.Arrays;
import java.util.List;

public class LiUtils {

    public static <T> List<T> of(T... args) {
        return Arrays.asList(args);
    }

}
