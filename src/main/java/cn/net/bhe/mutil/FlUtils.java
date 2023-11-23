package cn.net.bhe.mutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class FlUtils {

    public static final String USER_HOME = "user.home";
    public static final String DESKTOP = "Desktop";

    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        File[] ifiles = file.listFiles();
        for (File ifile : ifiles) {
            if (!delete(ifile)) {
                return Boolean.FALSE;
            }
        }
        return file.delete();
    }

    public static void writeToDesktop(String value) throws Exception {
        writeToDesktop(value, String.valueOf(new Snowflake().nextId()), false);
    }

    public static void writeToDesktop(String value, String fileName, boolean append) throws Exception {
        fileName = System.getProperty(USER_HOME) + File.separator + DESKTOP + File.separator + fileName;
        write(value, fileName, append);
    }

    public static void write(String value, String path, boolean append) throws Exception {
        FileWriter fileWriter = new FileWriter(path, append);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(value);
        bufferedWriter.flush();
        fileWriter.close();
        bufferedWriter.close();
    }

}
