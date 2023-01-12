package cn.net.bhe.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author sxydh
 */
public class PdfUtils {

    public static String pdf2base64(InputStream inputStream) throws Exception {
        PDDocument pdDocument = PDDocument.load(inputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        BufferedImage image = pdfRenderer.renderImage(0);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return IOUtils.encodeBase64(os.toByteArray());
    }

}
