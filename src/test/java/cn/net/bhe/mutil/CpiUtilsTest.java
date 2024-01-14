package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.io.File;

class CpiUtilsTest {

    @Test
    void compile() throws Exception {
        String root = FlUtils.getRootTmp() + File.separatorChar + "CpiUtilsTest";
        String outputDir = root + File.separatorChar + "output" + File.separator;
        String packageName = "compiledemo";
        String inputDir = root + File.separatorChar + "input" + File.separator + packageName;
        As.isTrue(FlUtils.mkdir(inputDir));
        FlUtils.write(String.format("package %s; public class Hello {}", packageName), inputDir + File.separatorChar + "Hello.java", false);
        FlUtils.write(String.format("package %s; public class World {}", packageName), inputDir + File.separatorChar + "World.java", false);
        boolean compile = CpiUtils.compile(inputDir, outputDir);
        As.isTrue(compile);
    }
}