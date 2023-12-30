package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class AddrUtilsTest {


    @Test
    void ranChn() {
        for (int i = 0; i < 100; i++) {
            System.out.println(AddrUtils.ranChn(3));
        }
    }

}