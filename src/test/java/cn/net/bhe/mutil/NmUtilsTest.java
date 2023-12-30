package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class NmUtilsTest {

    @Test
    void randomName() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(NmUtils.randomName(3));
        }
    }

}