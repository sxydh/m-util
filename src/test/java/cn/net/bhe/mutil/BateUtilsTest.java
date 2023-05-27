package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.util.Date;

class BateUtilsTest {

    @Test
    void startDateOfWeek() {
        Date date = BateUtils.addDays(new Date(), 7);
        System.out.println(BateUtils.startDateOfWeek(date));
    }

    @Test
    void endDateOfWeek() {
        Date date = BateUtils.addDays(new Date(), -1);
        System.out.println(BateUtils.endDateOfWeek(date));
    }

    @Test
    void startDateOfMonth() {
        Date date = BateUtils.addDays(new Date(), -12);
        System.out.println(BateUtils.startDateOfMonth(date));
    }

    @Test
    void endDateOfMonth() {
        Date date = BateUtils.addDays(new Date(), 22);
        System.out.println(BateUtils.endDateOfMonth(date));
    }

    @Test
    void startDateOfYear() {
        Date date = BateUtils.addDays(new Date(), -1);
        System.out.println(BateUtils.startDateOfYear(date));
    }

    @Test
    void endDateOfYear() {
        Date date = BateUtils.addDays(new Date(), -1);
        System.out.println(BateUtils.endDateOfYear(date));
    }

    @Test
    void keep() {
    }

}