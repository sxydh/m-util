package cn.net.bhe.mutil;

public class ArrUtils {

    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

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

    public static int findRi(int[] array, double target) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

}
