package cn.net.bhe.mutil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AddrUtilsTest {

    @BeforeAll
    static void beforeAll() {
        AddrUtils.init(new int[]{1, 1, 2, 1, 1});
    }

    @Test
    void randomAddr() {
        for (int i = 0; i < 10; i++) {
            System.out.println(AddrUtils.randomAddr());
        }
    }

    @Test
    void get() {
        System.out.println(AddrUtils.get());
    }

}