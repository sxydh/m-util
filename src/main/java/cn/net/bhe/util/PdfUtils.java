package cn.net.bhe.util;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
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

    public static String pdf2base64(InputStream inputStream, int pageIndex, float scale, String formatName) throws Exception {
        PDDocument pdDocument = PDDocument.load(inputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        BufferedImage image = pdfRenderer.renderImage(pageIndex, scale);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, os);
        return IOUtils.encodeBase64(os.toByteArray());
    }

    public static ByteArrayOutputStream[] pdf2svg(InputStream inputStream) throws Exception {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromBytes(inputStream.readAllBytes());
        return pdf.saveToStream(FileFormat.SVG);
    }

}
