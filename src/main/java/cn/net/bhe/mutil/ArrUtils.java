package cn.net.bhe.mutil;

public class ArrUtils {

    public static <T> T firstNotNull(T... arr) {
        if (arr == null) {
            return null;
        }
        for (T arg : arr) {
            if (arg != null) {
                return arg;
            }
        }
        return null;
    }

    public static <T> T at(T[] arr, int index) {
        if (arr == null) {
            return null;
        }
        if (index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

}
