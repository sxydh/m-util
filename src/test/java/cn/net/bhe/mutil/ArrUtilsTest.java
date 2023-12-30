package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrUtilsTest {

    @Test
    void isEmpty() {
        String[] args = new String[0];
        assertTrue(ArrUtils.isEmpty(args));
    }

    @Test
    void findRi() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(0, ArrUtils.findRi(arr, -2));
        assertEquals(0, ArrUtils.findRi(arr, 0.1));
        assertEquals(0, ArrUtils.findRi(arr, 1));
        assertEquals(1, ArrUtils.findRi(arr, 1.1));
        assertEquals(5, ArrUtils.findRi(arr, 10));
    }
}