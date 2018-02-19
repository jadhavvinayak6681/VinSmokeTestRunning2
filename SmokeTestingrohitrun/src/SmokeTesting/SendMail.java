package SmokeTesting;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.util.*;

public class SendMail {
	public static void execute(String sEmailSetUpSheet, HSSFWorkbook DataBook, String reportFilepath,String reportFileName, String sStringDelimeter) throws Exception {
		
		String sEMailFrom = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 1); // Get From Email Id
		String sEMailPass = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 2); // Get From Email Id's password
		String sEMailSMTP = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 4); // Get From Email Server's SMTP
		String sEMailPort = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 5); // Get From Email Server's Port
		
		String sEMailTO = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 6); // Get TO Email Id's
		String sEMailCC = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 7); // Get CC Email Id's
		String sEMailBCC = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 8); // Get BCC Email Id's
		
		String sEMailSubject = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 9); // Get Email Subject
		String sEMailBody = ExcelFunctions.FindValueInExcelSheet(DataBook, sEmailSetUpSheet, 0, "Start", 10); // Get Email Body
		
		String[] to = UtilityFunctions.SplitValuesIntoArray(sEMailTO, sStringDelimeter);   // Split the TO email id's if more than one exists
		String[] cc = UtilityFunctions.SplitValuesIntoArray(sEMailCC, sStringDelimeter);   // Split the CC email id's if more than one exists
		String[] bcc = UtilityFunctions.SplitValuesIntoArray(sEMailBCC, sStringDelimeter); // Split the BCC email id's if more than one exists

		SendMail.sendMail(sEMailFrom,sEMailPass,sEMailSMTP,sEMailPort,
				"true","true",true,"javax.net.ssl.SSLSocketFactory","false",
				to,cc,bcc,sEMailSubject,sEMailBody,reportFilepath,reportFileName);
	}

	public static boolean sendMail(String userName, String passWord, String host, String port, String starttls,
		String auth, boolean debug, String socketFactoryClass, String fallback, String[] to, String[] cc,
		String[] bcc, String subject, String text, String attachmentPath, String attachmentName){

		//Object Instantiation of a properties file.
		Properties props = new Properties();

		props.put("mail.smtp.user", userName); // From Email Id

		props.put("mail.smtp.host", host); // SMTP Server

		if(!"".equals(port)){
			props.put("mail.smtp.port", port); // SMTP Port
		}

		if(!"".equals(starttls)){
			props.put("mail.smtp.starttls.enable",starttls);
			props.put("mail.smtp.auth", auth);
		}

		if(debug){
			props.put("mail.smtp.debug", "true");
		}else{
			props.put("mail.smtp.debug", "false");
		}

		if(!"".equals(port)){
			props.put("mail.smtp.socketFactory.port", port);
		}
		if(!"".equals(socketFactoryClass)){
			props.put("mail.smtp.socketFactory.class",socketFactoryClass);
		}
		if(!"".equals(fallback)){
			props.put("mail.smtp.socketFactory.fallback", fallback);
		}

		try{
			
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(debug);
			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(subject); // Add subject to the email
	        // Create the message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(text); // Add body text to the email
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			if( !attachmentName.equals("No")) { // if attachment available attach the file to the email
				File att = new File(new File(attachmentPath), attachmentName);
				messageBodyPart.attachFile(att);
				multipart.addBodyPart(messageBodyPart);
				msg.setContent(multipart);
			} else {
			}

			msg.setFrom(new InternetAddress(userName)); // Set From Email Id to the email

			for(int i=0;i<to.length;i++){
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i])); // Add TO email id's one by one
			}

			for(int i=0;i<cc.length;i++){
				if(!cc[i].isEmpty()) { // if CC email id's are available
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i])); // Add CC email id's one by one
				}
			}

			for(int i=0;i<bcc.length;i++){
				if(!bcc[i].isEmpty()) { // if BCC email id's are available
					msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i])); // Add BCC email id's one by one
				}
			}
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			transport.connect(host, userName, passWord);

			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			return true;

		} catch (Exception mex){
			mex.printStackTrace();
			return false;
		}
	}
}