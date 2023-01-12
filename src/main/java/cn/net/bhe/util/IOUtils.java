package cn.net.bhe.util;

import java.util.Base64;

/**
 * @author sxydh
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
