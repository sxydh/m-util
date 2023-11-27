package cn.net.bhe.mutil;

import java.io.BufferedInputStream;
import java.io.InputStream;
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
                byte[] bytes = new byte[1024];
                StringBuilder stringBuilder = new StringBuilder();
                while (bufferedInputStream.read(bytes) != -1) {
                    stringBuilder.append(new String(bytes, StandardCharsets.UTF_8));
                }
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtils.EMPTY;
        }
    }

}
