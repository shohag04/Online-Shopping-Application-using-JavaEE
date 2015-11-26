package com.shohag.shopping.transformer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {
	
public static boolean createPdf(String user, String orderDetails, String fileName) {
		
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date orderDate = new Date();
		Date deliveryDate = new Date(orderDate.getTime() + (2 * DAY_IN_MS));
		Document document = new Document();
		 
        try {
            PdfWriter.getInstance(document,
                new FileOutputStream(fileName));
            document.open();
            document.add(new Paragraph("\rName: "+user));
            document.add(new Paragraph("\rOrder Date: "+orderDate.toString()));
            document.add(new Paragraph("\rDelivery Date: "+deliveryDate.toString()));
            document.add(new Paragraph("\r\rOrder details:"+orderDetails));
            document.close(); 		
            return true;
        } catch (DocumentException e) {
            e.printStackTrace(); return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace(); return false;
        }
	}
}
