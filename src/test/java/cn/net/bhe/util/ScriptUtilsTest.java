package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;

class ScriptUtilsTest {

    @Test
    void getJavaScript() {
        try {
            ScriptEngine engine = ScriptUtils.getJavaScript();
            engine.put("x", Math.random());
            Object obj = engine.eval("x + 1");
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}