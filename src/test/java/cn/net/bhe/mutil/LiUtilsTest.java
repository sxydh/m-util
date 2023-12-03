package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiUtilsTest {

    @Test
    void def() {
        List<Object> list = new ArrayList<>();
        LiUtils.def(list, false, 1, 2.0, StrUtils.EMPTY);
        assertEquals(false, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2.0, list.get(2));
        assertEquals(StrUtils.EMPTY, list.get(3));
    }
}