package cn.net.bhe.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Administrator
 */
public class ScriptUtils {

    static final ScriptEngineManager ENGINE_MANAGER = new ScriptEngineManager(Thread.currentThread().getContextClassLoader());
    static final ScriptEngine JAVA_SCRIPT = ENGINE_MANAGER.getEngineByName("JavaScript");

    public static ScriptEngine getJavaScript() {
        return JAVA_SCRIPT;
    }

}
