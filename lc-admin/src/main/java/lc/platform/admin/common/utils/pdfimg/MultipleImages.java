package lc.platform.admin.common.utils.pdfimg;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 多图片转pdf
 */
public class MultipleImages {
    public static final String[] IMAGES = {
            "/Users/abc/Desktop/20180408_190331.jpg",
            "/Users/abc/Desktop/秦学教育协会/系统设计/督导评估.jpg",
            "/Users/abc/Desktop/秦学教育协会/系统设计/指标管理.jpg"
    };
    public static final String DEST = "src/main/resources/demo-images.pdf";

    //images --> pdf
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MultipleImages().createPdf(Arrays.asList(IMAGES),DEST,"秦学教育协会");
    }

    public void createPdf(java.util.List<String> images, String dest, String imgText) throws IOException, DocumentException {

        Image img = Image.getInstance(images.get(0));
        Document document = new Document(img);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        writer.setPageEvent(new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                cb.saveState();
                cb.beginText();
                BaseFont bf = null;
                try {
                    bf =  BaseFont.createFont("STSongStd-Light",
                            "UniGB-UCS2-H", false);
                } catch (Exception e) {
                    throw new RuntimeException("字体创建出现异常！");
                }
                cb.setFontAndSize(bf, 10);
                // Footer
                float y = document.bottom(-20);
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                        "第 "+ writer.getPageNumber() + " 页",
                        (document.right() + document.left()) / 2, y, 0);
                cb.endText();
                cb.restoreState();
            }
        });

        PdfContentByte cb = writer.getDirectContent();

        for (String image : images) {

            img = Image.getInstance(image);
            img.scaleToFit(523, 770);
            float offsetX = (523 - img.getScaledWidth()) / 2;
            float offsetY = (770 - img.getScaledHeight()) ;
            img.setAbsolutePosition(36 + offsetX, 36 + offsetY);

            document.setPageSize(PageSize.A4);
            document.newPage();
            document.add(img);
            //
            BaseFont bf =  BaseFont.createFont("STSongStd-Light",
                    "UniGB-UCS2-H", false);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, imgText,
                    (document.right() + document.left()) / 2, offsetY, 0);
            cb.endText();
        }
        document.close();
    }


    public static void imgToPdf(java.util.List<Image> images) throws IOException, DocumentException {
        Image img = images.get(0);
        Document document = new Document(img);
        File file = ResourceUtils.getFile("classpath:demo-images.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        writer.setPageEvent(new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                cb.saveState();
                cb.beginText();
                BaseFont bf = null;
                try {
                    bf =  BaseFont.createFont("STSongStd-Light",
                            "UniGB-UCS2-H", false);
                } catch (Exception e) {
                    throw new RuntimeException("字体创建出现异常！");
                }
                cb.setFontAndSize(bf, 10);
                // Footer
                float y = document.bottom(-20);
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                        "第 "+ writer.getPageNumber() + " 页",
                        (document.right() + document.left()) / 2, y, 0);
                cb.endText();
                cb.restoreState();
            }
        });

        PdfContentByte cb = writer.getDirectContent();
        for(int i=0;i<images.size();i++){
            images.get(i).scaleToFit(523, 770);
            float offsetX = (523 - images.get(i).getScaledWidth()) / 2;
            float offsetY = (770 - images.get(i).getScaledHeight()) ;
            images.get(i).setAbsolutePosition(36 + offsetX, 36 + offsetY);

            document.setPageSize(PageSize.A4);
            document.newPage();
            document.add(images.get(i));
            //
            BaseFont bf =  BaseFont.createFont("STSongStd-Light",
                    "UniGB-UCS2-H", false);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "",
                    (document.right() + document.left()) / 2, offsetY, 0);
            cb.endText();
            }
        document.close();
        }



}