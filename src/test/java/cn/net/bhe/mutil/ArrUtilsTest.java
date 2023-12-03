package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrUtilsTest {

    @Test
    void isEmpty() {
        String[] args = new String[0];
        assertTrue(ArrUtils.isEmpty(args));
    }

}