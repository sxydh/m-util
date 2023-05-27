package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.stream.IntStream;

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

    @Test
    void pdf2svg() {
        IntStream.range(0, 1000).forEach(i -> {
            System.out.println(i);
            try (FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Administrator/Desktop/test.svg")) {
                FileInputStream fileInputStream = new FileInputStream("C:/Users/Administrator/Desktop/test.pdf");
                ByteArrayOutputStream[] outputStreams = PdfUtils.pdf2svg(fileInputStream);
                outputStreams[0].writeTo(fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}