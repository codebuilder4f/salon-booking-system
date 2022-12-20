//package com.mpb.salon.bookig.system.service.impl;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.*;
//import com.mpb.salon.bookig.system.entity.AdvancedPayment;
//import com.mpb.salon.bookig.system.entity.BookedService;
//import com.mpb.salon.bookig.system.entity.Booking;
//import org.bouncycastle.mime.encoding.Base64OutputStream;
//import org.junit.jupiter.api.Test;
//
//import java.awt.*;
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//class BillGeneratorTest {
//
//    static String pattern = "MM-dd-yyyy";
//    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//    private static Integer SERVICE_MARGIN_Y =300;
//
//    @Test
//    public void test() throws IOException, DocumentException {
//        PdfReader pdfReader = null;
//        PdfStamper pdfStamper = null;
//        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        final Base64OutputStream out = new Base64OutputStream(bout);
//        String pdf = "";
//        try
//        {
//            String file = "/Users/madhuwantha/Documents/Freelancing/Fiverr/ahilendranainka/SalonBooking/salon_new.pdf";
//
//            pdfReader = new PdfReader(file);
//            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("/Users/madhuwantha/Documents/Freelancing/Fiverr/ahilendranainka/SalonBooking/ModifiedTestFile.pdf"));
////            pdfStamper = new PdfStamper(pdfReader, out);
//            PdfContentByte firstPageOver = pdfStamper.getOverContent(1);
//            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
//            Font tdFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
//            Rectangle pageSize = pdfReader.getPageSize(1);
//
//            firstPageOver.beginText();
//            firstPageOver.setFontAndSize(bf, 10);
//
//            int y = 423;
//            int size = 5;
//            drawHLine(firstPageOver, 45, 370, 400);
//            for (int i = 1; i <= size; i++) {
//                int _y = y- ((i+1)*15) - 8;
//                firstPageOver.setTextMatrix(45, _y);
//                firstPageOver.showText("hair-cutting");
//
//                firstPageOver.setTextMatrix(190, _y);
//                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"10",224, _y, 0 );
//
//                firstPageOver.setTextMatrix(260, _y);
//
//                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"10",290, _y, 0 );
//
//
//                firstPageOver.setTextMatrix(345, _y);
//                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"10",370, _y, 0 );
//            }
//
//            SERVICE_MARGIN_Y = y - (size*15 ) -5;
//
//
//
//            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-25);
//
//            firstPageOver.setTextMatrix(258, SERVICE_MARGIN_Y-37);
//            firstPageOver.showText("Total Amount:");
//            firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-37);
//            firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"Rs. "+ 1000,370, SERVICE_MARGIN_Y-37, 0 );
//            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-44);
////
//            firstPageOver.setTextMatrix(231, SERVICE_MARGIN_Y-56);
//            firstPageOver.showText("Advanced Payment:");
//            firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-56);
//            firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"Rs. "+ 100,370, SERVICE_MARGIN_Y-56, 0 );
//            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-63);
////
//            firstPageOver.setTextMatrix(229, SERVICE_MARGIN_Y-75);
//            firstPageOver.showText("Remaining Payment:");
//            firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-75);
//            firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"Rs. "+ 10000,370, SERVICE_MARGIN_Y-75, 0 );
//            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-82);
//
//            drawTBoarder(firstPageOver,SERVICE_MARGIN_Y-100,(y-SERVICE_MARGIN_Y)+105);
//
//
//            setCustomerDetails(null, firstPageOver);
//            setFooter(firstPageOver,SERVICE_MARGIN_Y-120 );
//
//
//
//            pdfStamper.getWriter().setCloseStream(false);
//            pdfStamper.close();
//            pdfReader.close();
//            out.flush();
//            pdf =  bout.toString(StandardCharsets.UTF_8);
//
//        } catch (IOException | DocumentException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                bout.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//    private static void setCustomerDetails(Booking booking, PdfContentByte cb){
//        BaseFont bf = null;
//
//        try {
//            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//
//        cb.beginText();
//        cb.setFontAndSize(bf, 12);
//        cb.setTextMatrix(28, 468);
//        cb.showText("vfd nj ojiojoi joi jio");
//        cb.setTextMatrix(28, 454);
//        cb.showText("vfd nj ojiojoi joi jio");
//        cb.setTextMatrix(28, 439);
//        cb.showText("r43877887887"+" , "+ "r43877887887");
//
//        cb.setTextMatrix(327, 468);
//        cb.showText(simpleDateFormat.format(new Date()));
//    }
//
//    private static void setFooter(PdfContentByte cb, int y){
////        int y = 170;
//
//        BaseFont bf = null;
//
//        try {
//            bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//
//        cb.beginText();
//        cb.setFontAndSize(bf, 10);
//
//        cb.setTextMatrix(28, y);
//        cb.showText("Term and Condition");
//
//
//        try {
//            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//
//        cb.beginText();
//        cb.setFontAndSize(bf, 10);
//
//        Rectangle rect = new Rectangle(28, y-2, 400, y-100);
//        ColumnText ct = new ColumnText(cb);
//        ct.setSimpleColumn(rect);
//        ct.addElement(new Paragraph("Hello this is your bill Creating a Terms & Conditions for your application or welcome this is your bill Creating a Terms & Conditions for your application or this this is your bill Creating a Terms &"));
//        try {
//            ct.go();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        cb.setTextMatrix(276, y-80);
//        cb.showText("Final payment by cash");
//
//        cb.setTextMatrix(291, y-123);
//        cb.showText("Customer Signature");
//
//        drawHDashLine(cb, 271,410,y-108);
//
//
//
//    }
//
//    private static void drawHLine(PdfContentByte cb, int sx, int ex, int y ){
//        cb.setRGBColorStroke(0, 0, 0);
//        cb.setLineWidth(0.5);
//
//        cb.moveTo(sx, y);
//        cb.lineTo(ex, y);
//        cb.stroke();
//    }
//
//
//
//    private static void drawHDashLine(PdfContentByte cb, int sx, int ex, int y ){
//        cb.setRGBColorStroke(0, 0, 0);
//        cb.setLineDash(1.5, 1.5);
//
//        cb.moveTo(sx, y);
//        cb.lineTo(ex, y);
//        cb.stroke();
//    }
//
//
//
//    private static void drawTBoarder(PdfContentByte cb, int sy, int h ){
//        cb.setColorFill(BaseColor.WHITE);
//        cb.setLineWidth(0.1);
//        cb.setCMYKColorStrokeF(0f, 0f, 0f, 1f);
//        cb.roundRectangle(27, sy, 365, h, 5);
//        cb.setColorFill(BaseColor.BLACK);
//        cb.stroke();
//    }
//
//
//
//
//    public static int triggerNewPage(PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
//        stamper.insertPage(pagecount, pagesize);
//        PdfContentByte canvas = stamper.getOverContent(pagecount);
//        column.setCanvas(canvas);
//        column.setSimpleColumn(rect);
//        return column.go();
//    }
//
//
//}
//
//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
