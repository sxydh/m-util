package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

class PdfUtilsTest {

    @Test
    void pdf2base64() {
        try (FileInputStream inputStream = new FileInputStream("C:/Users/Administrator/Desktop/test.pdf")) {
            String ret = PdfUtils.pdf2base64(inputStream, 0, 2, "png");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}