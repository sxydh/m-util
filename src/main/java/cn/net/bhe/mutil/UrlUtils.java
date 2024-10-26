package cn.net.bhe.mutil;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlUtils {

    public static String encode(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8).replace(StrUtils.PLUS, StrUtils.SPACE_ENCODE);
    }

    public static String decode(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

}
