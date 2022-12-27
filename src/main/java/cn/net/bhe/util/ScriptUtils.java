package cn.net.bhe.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Administrator
 */
public class ScriptUtils {

    static final ScriptEngineManager ENGINE_MANAGER = new ScriptEngineManager(Thread.currentThread().getContextClassLoader());

    public static ScriptEngine getJavaScript() {
        return ENGINE_MANAGER.getEngineByName("JavaScript");
    }

}
