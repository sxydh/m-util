package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;

import static org.junit.jupiter.api.Assertions.*;

class ScriptUtilsTest {

    @Test
    void getJavaScript() {
        try {
            ScriptEngine engine = ScriptUtils.getJavaScript();
            Object obj = engine.eval("1 + 1");
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}