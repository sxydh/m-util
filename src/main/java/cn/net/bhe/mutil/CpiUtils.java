package cn.net.bhe.mutil;

import javax.tools.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CpiUtils {

    public static boolean compile(String inputDir, String outputDir) throws Exception {
        File inputRoot = new File(inputDir);
        if (!inputRoot.exists() || !inputRoot.isDirectory()) return false;
        File[] inputFiles = inputRoot.listFiles();
        if (inputFiles == null || inputFiles.length == 0) return true;
        As.isTrue(FlUtils.mkdir(outputDir));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        As.isTrue(compiler != null, "ToolProvider.getSystemJavaCompiler() == null");
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(inputFiles));
            Iterable<String> options = Arrays.asList("-d", outputDir);

            JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
            Boolean callResult = compilationTask.call();
            if (!callResult) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.out.format("Error on line %d in %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getSource().toUri());
                }
            }
            return callResult;
        }
    }

}
