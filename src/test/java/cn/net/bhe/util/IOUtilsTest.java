package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

class IOUtilsTest {

    @Test
    void encodeBase64() {
        try (FileInputStream inputStream = new FileInputStream("C:/Users/Administrator/Desktop/2023-01-12_110510.png")) {
            String ret = IOUtils.encodeBase64(inputStream.readAllBytes());
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}