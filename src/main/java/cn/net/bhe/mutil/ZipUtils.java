package cn.net.bhe.mutil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static final String FILE_SUFFIX = "zip";

    public static void comp(String name) throws Exception {
        FlUtils.mkdir(FlUtils.getRootTmp());
        try (InputStream inputStream = Files.newInputStream(Paths.get(FlUtils.getRootTmp() + File.separator + name))) {
            try (FileOutputStream outputStream = new FileOutputStream(FlUtils.getRootTmp() + File.separator + name + StrUtils.DOT + FILE_SUFFIX)) {
                try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
                    zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);
                    ZipEntry zipEntry = new ZipEntry(name);
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    public static String deComp(String name) throws Exception {
        return deComp(ZipUtils.class.getResourceAsStream("/" + name + StrUtils.DOT + FILE_SUFFIX));
    }

    public static String deComp(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                zipInputStream.getNextEntry();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zipInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
            }
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        }
    }

}
