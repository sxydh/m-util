package cn.net.bhe.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class CompreFaceUtilsTest {

    @Test
    void faceDetect() {
        try {
            String ret = CompreFaceUtils.faceDetect(
                    "eda569dc-64ef-4338-a1a3-7c633f5f928c",
                    IOUtils.resourceToString("/CompreFaceUtilsTest_faceDetect01.txt", StandardCharsets.UTF_8));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void faceVerify() {
        try {
            String ret = CompreFaceUtils.faceVerify(
                    "37240f60-4d10-4db3-89d2-fcccd8c1d6c2",
                    IOUtils.resourceToString("/CompreFaceUtilsTest_faceDetect01.txt", StandardCharsets.UTF_8),
                    IOUtils.resourceToString("/CompreFaceUtilsTest_faceDetect02.txt", StandardCharsets.UTF_8));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}