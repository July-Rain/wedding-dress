package lc.platform.admin.common.utils.pdfimg;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf 转 图片
 */
public class PdfBoxUtil {

    public static final String DEST = "src/main/resources/demo-images.pdf";

    //pdf-->images
    public static void main(String[] args) throws IOException {
        List<BufferedImage> bufferedImages = convertToImage(new File(DEST));
        int idex = 0 ;
        for (BufferedImage img : bufferedImages) {
            ImageIOUtil.writeImage(img, "src/main/resources/"+(idex++) + ".jpg", 300);
        }
    }

    public static List<BufferedImage> convertToImage(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<BufferedImage> bufferedImageList = new ArrayList<>();

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage img = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            bufferedImageList.add(img);
        }
        document.close();

        return bufferedImageList;
    }
    public static List<BufferedImage> convertToImageByte(byte[] input) throws IOException {
        PDDocument document = PDDocument.load(input);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<BufferedImage> bufferedImageList = new ArrayList<>();

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage img = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            bufferedImageList.add(img);
        }
        document.close();

        return bufferedImageList;
    }

    public static BufferedImage concat(List<BufferedImage> images) throws IOException {
        int heightTotal = 0;
        for (BufferedImage bImg : images) {
            heightTotal += bImg.getHeight();
        }

        int heightCurr = 0;
        BufferedImage concatImage = new BufferedImage(images.get(0).getWidth(), heightTotal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for (BufferedImage bImg : images) {
            g2d.drawImage(bImg, 0, heightCurr, null);
            heightCurr += bImg.getHeight();
        }
        g2d.dispose();

        return concatImage;
    }
}
