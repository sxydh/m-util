package cn.net.bhe.mutil;

import java.util.Arrays;
import java.util.List;

public class LiUtils {

    public static <T> List<T> of(T... args) {
        return Arrays.asList(args);
    }

    public static <T> void def(List<T> list, T... args) {
        if (list == null || ArrUtils.isEmpty(args)) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (list.size() <= i) {
                list.add(null);
            }
            if (list.get(i) == null) {
                list.set(i, args[i]);
            }
        }
    }

}
