package cn.net.bhe.mutil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("CallToPrintStackTrace")
public class HttpUtils {

    public static String get(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try (InputStream inputStream = conn.getInputStream();
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                return read(bufferedInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtils.EMPTY;
        }
    }

    public static String post(String urlStr, String body) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
                os.write(bodyBytes, 0, bodyBytes.length);
            }
            try (InputStream inputStream = conn.getInputStream();
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                return read(bufferedInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtils.EMPTY;
        }
    }

    public static String read(BufferedInputStream bufferedInputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int readLen;
        while ((readLen = bufferedInputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, readLen, StandardCharsets.UTF_8));
        }
        return stringBuilder.toString();
    }

}
