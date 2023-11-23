package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnowflakeTest {

    @Test
    void nextId() {
        Snowflake snowflake = new Snowflake();
        int size = 10000;
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            set.add(snowflake.nextId());
        }
        assertEquals(set.size(), size);
    }
}