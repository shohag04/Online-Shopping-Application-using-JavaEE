package com.shohag.shopping.email;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailClass {

		public static void sendEmail(String toEmail,String attachment) {
			
			String fromEmail = "shohag.dummy@gmail.com";
			String userName = "shohag.dummy";
			String password = "V0AXgvJI0dummy";
			String subject = "TestMart order confirmation";
			System.getProperty("line.separator");
			String message = new StringBuilder()
			           .append("Dear Valued Customer,\r")
			           .append("We received your order as attached pdf\r")
			           .append("Let us allow 2 business days to process your order\r")
			           .append("Thanks for shopping with us,\r")
			           .append("TestSale")
			           .toString();
			try{
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", "gsmtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			
			
			Session mailSession = Session.getDefaultInstance(properties, null);
			mailSession.setDebug(true);
			Message mailMessage = new MimeMessage(mailSession);
			mailMessage.setFrom(new InternetAddress(fromEmail));
			mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			
			//mailMessage.setContent(message,"text/html");
			// Create the message part
	        BodyPart messageBodyPart = new MimeBodyPart();

	        // Now set the actual message
	        messageBodyPart.setText(message);

	        // Create a multi-part message
	        Multipart multipart = new MimeMultipart();

	        // Set text message part
	        multipart.addBodyPart(messageBodyPart);

	        // Part two is attachment
	        messageBodyPart = new MimeBodyPart();
	        //String filename = "E:\\MyPDFSample.pdf";
	        DataSource source = new FileDataSource(attachment);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(attachment);
	        multipart.addBodyPart(messageBodyPart);

	        // Send the complete message parts
	        mailMessage.setContent(multipart);
			mailMessage.setSubject(subject);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com",userName, password);
			transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
			} 
			catch (Exception e) {e.printStackTrace();}	
	    }
}
