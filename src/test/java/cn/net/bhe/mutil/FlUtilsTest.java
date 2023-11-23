package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class FlUtilsTest {

    @Test
    void writeToDesktop() throws Exception {
        FlUtils.writeToDesktop(String.valueOf(new Snowflake().nextId()));
    }

}