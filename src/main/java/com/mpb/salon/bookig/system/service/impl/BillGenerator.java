package com.mpb.salon.bookig.system.service.impl;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mpb.salon.bookig.system.ResourceBundleUtil;
import com.mpb.salon.bookig.system.entity.AdvancedPayment;
import com.mpb.salon.bookig.system.entity.BookedService;
import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.entity.Services;
import org.bouncycastle.mime.encoding.Base64OutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class BillGenerator {


    static String pattern = "MM-dd-yyyy";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private static Integer SERVICE_MARGIN_Y =300;


    public static String generate(Booking booking, String name){

        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        final Base64OutputStream out = new Base64OutputStream(bout);
        try
        {
            String file = ResourceBundleUtil.getYmlStringForActive("application","app.bill-path");
            String tempBillPath = ResourceBundleUtil.getYmlStringForActive("application","app.temp-bill-path");


            pdfReader = new PdfReader(file);
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(tempBillPath+name+".pdf"));
//            pdfStamper = new PdfStamper(pdfReader, out);
            PdfContentByte firstPageOver = pdfStamper.getOverContent(1);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);


            firstPageOver.beginText();
            firstPageOver.setFontAndSize(bf, 10);


            int y = 416;
            List<BookedService> bookedServices = booking.getBookedServices();
            int size = bookedServices.size();
            drawHLine(firstPageOver, 45, 370, y-23);
            for (int i = 1; i <= size; i++) {
                BookedService bookedService = bookedServices.get(i - 1);

                int _y = y- ((i+1)*15)- 8;

                firstPageOver.setTextMatrix(45, _y);
                firstPageOver.showText(bookedService.getServices().getName());

                String amount = bookedService.getServices().getPrice().toString();
                if (amount.split(".").length == 1){
                    amount = amount+".00";
                }
                firstPageOver.setTextMatrix(190, _y);
                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,amount,224, _y, 0 );

                firstPageOver.setTextMatrix(260, _y);

                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,"0.00",290, _y, 0 );


                firstPageOver.setTextMatrix(345, _y);
                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,amount,370, _y, 0 );
            }

            SERVICE_MARGIN_Y = y - (size*15 ) -5 ;


            String totalAmount = booking.getTotalAmount().toString();
            Optional<Double> advancedSum = booking.getAdvancedPayments().stream().map(AdvancedPayment::getAmount).reduce(Double::sum);
            if (totalAmount.split(".").length == 1){
                totalAmount = totalAmount+".00";
            }


            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-25);

            firstPageOver.setTextMatrix(258, SERVICE_MARGIN_Y-37);
            firstPageOver.showText("Total Amount:");
            firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-37);
            firstPageOver.showTextAligned(Element.ALIGN_RIGHT,totalAmount,370, SERVICE_MARGIN_Y-37, 0 );
            drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-44);

            advancedSum.ifPresent(sum -> {
                String sumS = String.valueOf(sum);
                if (sumS.split(".").length == 1){
                    sumS = sumS+".00";
                }
                firstPageOver.setTextMatrix(231, SERVICE_MARGIN_Y-56);
                firstPageOver.showText("Advanced Payment:");
                firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-56);
                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,sumS+"",370, SERVICE_MARGIN_Y-56, 0 );
                drawHLine(firstPageOver, 45, 370, SERVICE_MARGIN_Y-63);


                String remaining = String.valueOf(booking.getTotalAmount() - sum);
                if (remaining.split(".").length == 1){
                    remaining = remaining+".00";
                }else if(remaining.split(".").length ==2 && remaining.split(".")[1] == "0"){
                    remaining = remaining.split(".")[0] = ".00";
                }

                firstPageOver.setTextMatrix(229, SERVICE_MARGIN_Y-75);
                firstPageOver.showText("Remaining Payment:");
                firstPageOver.setTextMatrix(323, SERVICE_MARGIN_Y-75);
                firstPageOver.showTextAligned(Element.ALIGN_RIGHT,remaining+"",370, SERVICE_MARGIN_Y-75, 0 );

            });



            drawTBoarder(firstPageOver,SERVICE_MARGIN_Y-100,(y-SERVICE_MARGIN_Y)+105);


            setCustomerDetails(booking, firstPageOver);
            setFooter(firstPageOver,SERVICE_MARGIN_Y-120 );



            pdfStamper.getWriter().setCloseStream(false);
            pdfStamper.close();
            pdfReader.close();
//            out.flush();
        } catch (IOException | DocumentException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }finally {
            try {
                bout.close();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return name;
    }


    private static void setCustomerDetails(Booking booking, PdfContentByte cb){
        BaseFont bf = null;

        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(28, 464);
        cb.showText(booking.getCustomer().getName());
        cb.setTextMatrix(28, 450);
        cb.showText(booking.getCustomer().getAddress());
        cb.setTextMatrix(28, 435);
        cb.showText(booking.getCustomer().getMobileOne()+" , "+booking.getCustomer().getMobileTwo());

        cb.setTextMatrix(336, 464);
        cb.showText(simpleDateFormat.format(booking.getCreatedDate()));
    }

    private static void setFooter(PdfContentByte cb, int y){

        BaseFont bf = null;

        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        cb.beginText();
        cb.setFontAndSize(bf, 10);

        cb.setTextMatrix(28, y);
        cb.showText("Term and Condition");


        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        cb.beginText();
        cb.setFontAndSize(bf, 10);

        Rectangle rect = new Rectangle(28, y-2, 400, y-100);
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(rect);
        ct.addElement(new Paragraph("Hello this is your bill Creating a Terms & Conditions for your application or welcome this is your bill Creating a Terms & Conditions for your application or this this is your bill Creating a Terms &"));
        try {
            ct.go();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        cb.setTextMatrix(276, y-80);
        cb.showText("Final payment by cash");

        cb.setTextMatrix(291, y-123);
        cb.showText("Customer Signature");

        drawHDashLine(cb, 271,410,y-108);



    }

    private static void drawHLine(PdfContentByte cb, int sx, int ex, int y ){
        cb.setRGBColorStroke(0, 0, 0);
        cb.setLineWidth(0.5);

        cb.moveTo(sx, y);
        cb.lineTo(ex, y);
        cb.stroke();
    }



    private static void drawHDashLine(PdfContentByte cb, int sx, int ex, int y ){
        cb.setRGBColorStroke(0, 0, 0);
        cb.setLineDash(1.5, 1.5);

        cb.moveTo(sx, y);
        cb.lineTo(ex, y);
        cb.stroke();
    }

    private static void drawTBoarder(PdfContentByte cb, int sy, int h ){
        cb.setColorFill(BaseColor.WHITE);
        cb.setLineWidth(0.1);
        cb.setCMYKColorStrokeF(0f, 0f, 0f, 1f);
        cb.roundRectangle(27, sy, 365, h, 5);
        cb.setColorFill(BaseColor.BLACK);
        cb.stroke();
    }


    //--------------------------------------

    public static int triggerNewPage(PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        stamper.insertPage(pagecount, pagesize);
        PdfContentByte canvas = stamper.getOverContent(pagecount);
        column.setCanvas(canvas);
        column.setSimpleColumn(rect);
        return column.go();
    }


}
