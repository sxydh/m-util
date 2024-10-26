package cn.net.bhe.mutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class FlUtils {

    public static final String USER_HOME = "user.home";
    public static final String USER_DIR = "user.dir";
    public static final String DESKTOP = "Desktop";

    public static String combine(String... paths) {
        if (ArrUtils.isEmpty(paths)) {
            return StrUtils.EMPTY;
        }

        StringBuilder combinePath = new StringBuilder(StrUtils.EMPTY);
        for (int i = 0; i < paths.length; i++) {
            if (i == 0) {
                combinePath.append(paths[i]);
                continue;
            }
            combinePath.append(File.separator).append(paths[i]);
        }
        return combinePath.toString();
    }

    public static String getRoot() {
        return System.getProperty(USER_DIR);
    }

    public static String getRootTmp() {
        return combine(getRoot(), StrUtils.TMP);
    }

    public static String getRootTmpWithPkg(Class<?> clazz) {
        return combine(getRootTmp(), clazz.getPackageName().replace(StrUtils.DOT, StrUtils.UNDERSCORE));
    }

    public static String getDesktop() {
        return combine(System.getProperty(USER_HOME), DESKTOP);
    }

    public static String getDesktopRand() {
        return combine(getDesktop(), DtUtils.ts17());
    }

    public static boolean mkdir(String path) {
        String dir = StrUtils.EMPTY;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            dir += c;
            if (c == File.separatorChar || i == path.length() - 1) {
                File file = new File(dir);
                if (!file.exists()) {
                    if (!file.mkdir()) {
                        return Boolean.FALSE;
                    }
                }
            }
        }
        return Boolean.TRUE;
    }

    public static void writeToDesktop(String value) throws IOException {
        writeToDesktop(value, String.valueOf(new Snowflake().nextId()), false);
    }

    public static void writeToDesktop(String value, String fileName, boolean append) throws IOException {
        fileName = combine(getDesktop(), fileName);
        write(value, fileName, append);
    }

    public static void write(String value, String path, boolean append) throws IOException {
        write(value.getBytes(StandardCharsets.UTF_8), path, append);
    }

    public static void write(byte[] bytes, String path, boolean append) throws IOException {
        write(bytes, path, append, false);
    }

    public static void write(byte[] bytes, String path, boolean append, boolean checkPath) throws IOException {
        if (checkPath) {
            mkdir(path);
        }
        try (FileOutputStream fos = new FileOutputStream(path, append)) {
            fos.write(bytes);
        }
    }

    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            return Boolean.FALSE;
        }
        for (File ifile : childFiles) {
            if (!delete(ifile)) {
                return Boolean.FALSE;
            }
        }
        return file.delete();
    }

}
