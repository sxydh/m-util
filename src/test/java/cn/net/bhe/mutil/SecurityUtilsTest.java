package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilsTest {

    @Test
    void encode() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String ret = SecurityUtils.encode(String.valueOf(Math.random()));
            System.out.println(ret);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    void matches() {
        String password = String.valueOf(Math.random());
        System.out.println(password);
        String encodedPassword = SecurityUtils.encode(password);
        System.out.println(encodedPassword);
        boolean ret = SecurityUtils.matches(password, encodedPassword);
        System.out.println(ret);
    }
}