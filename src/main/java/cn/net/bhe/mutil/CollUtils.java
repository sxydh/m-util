package cn.net.bhe.mutil;

import java.util.Collection;

public class CollUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
