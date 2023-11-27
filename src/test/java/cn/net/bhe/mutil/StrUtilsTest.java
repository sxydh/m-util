package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrUtilsTest {

    @Test
    void trim() {
        assertEquals("abcd", StrUtils.trim("abcd", null));
        assertEquals("bcd", StrUtils.trim("abcd", "a"));
        assertEquals("abc", StrUtils.trim("abcd", "d"));
        assertEquals("abcd", StrUtils.trim("abcd", "b"));

        assertEquals("d", StrUtils.trim("abcd", "abc"));
        assertEquals("a", StrUtils.trim("abcd", "bcd"));
        assertEquals("abcd", StrUtils.trim("abcd", "acd"));

        assertEquals("", StrUtils.trim("abcd", "abcd"));
        assertEquals("abcd", StrUtils.trim("abcd", "abce"));
        assertEquals("abcd", StrUtils.trim("abcd", "bcde"));
        assertEquals("abcd", StrUtils.trim("abcd", "abcde"));
    }

}