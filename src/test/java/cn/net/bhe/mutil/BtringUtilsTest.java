package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class BtringUtilsTest {

    @Test
    void getFirstLetter() {
        try {
            String ret = BtringUtils.getFirstLetter("word汉字word");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getFullLetter() {
        try {
            String ret = BtringUtils.getFullLetter("word汉字word");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void camelToSnake() {
        try {
            String ret = BtringUtils.camelToSnake("SpringBoot");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}