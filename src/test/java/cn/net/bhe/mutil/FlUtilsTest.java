package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class FlUtilsTest {

    @Test
    void getRoot() {
        System.out.println(FlUtils.getRoot());
    }

    @Test
    void getDesktop() {
        System.out.println(FlUtils.getDesktop());
    }

    @Test
    void writeToDesktop() throws Exception {
        FlUtils.writeToDesktop(String.valueOf(new Snowflake().nextId()));
    }

}