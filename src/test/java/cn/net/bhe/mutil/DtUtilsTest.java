package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

class DtUtilsTest {

    @Test
    void format() {
        System.out.println(DtUtils.format());
    }

    @Test
    void addDays() {
        Date date = DtUtils.addDays(DtUtils.date(), new Random().nextInt(10));
        System.out.println(DtUtils.format(date));
    }

}