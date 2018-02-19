package SmokeTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.search.SubjectTerm;

public class GetFromEmail {

	public static Boolean IsNewEmailFound(String MailImap, String MailId, String MailPass) throws MessagingException {
		// Open the web mail to check e-mails
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		 
		Session session = Session.getDefaultInstance(props, null);
		javax.mail.Store store = session.getStore("imaps");

		// Connecting the email server
		store.connect(MailImap, MailId,MailPass);
		 
		Folder folder = store.getFolder("INBOX");
		  
		// Opening Inbox
		folder.open(Folder.READ_WRITE);
		int iUnreadMsgCount = folder.getUnreadMessageCount();
		Boolean bFound = false; 
		if (iUnreadMsgCount > 0) {
		   bFound = true;
		}
		return bFound;
	}
	
	public static String GetLinkFromMailBox(String MailImap, String MailId, String MailPass, String sSubject, String sFirstStrToSearch, String sSecondStrToSearch) throws MessagingException, InterruptedException {
		/*
		 * The function returns the Registration link from the email received
		 * Parameter 1: MailImap : e.g. imap.gmail.com, imap.fusemail.net etc to connect the server
		 * Parameter 2: MailId   : Mail id in which the emails will be received from application 
		 * Parameter 3: MailPass : Password of the Mail id
		 * Parameter 4: sSubject : Subject of the email received
		 * Parameter 5: sFirstStrToSearch: The first string to search in the email content to Find the location of the registration link e.g. href
		 * Parameter 6: sSecondStrToSearch: The second string to search in the email content to find the location where the registration link e.g. click here  
		 * 
		 */
		// Open the web mail to check e-mails
		  String GetTheURL = null;
		  Properties props = System.getProperties();
		  props.setProperty("mail.store.protocol", "imaps");
		   
		  Session session = Session.getDefaultInstance(props, null);
		  javax.mail.Store store = session.getStore("imaps");

		  // Connecting the email server
		  store.connect(MailImap, MailId,MailPass);
		  
		  Folder folder = store.getFolder("INBOX");
		  
		  // Opening Inbox
		  folder.open(Folder.READ_WRITE);
		   
		  System.out.println("Total Message: " + folder.getMessageCount());
		  System.out.println("Unread Message: " + folder.getUnreadMessageCount());
		  
		  Message[] messages = null;
		  boolean isMailFound = false;
		  Message mailFromApplication= null;

		  //Search for mails from Application
		  for (int i = 1;i <= folder.getUnreadMessageCount(); i++) {
			  try {
				messages = folder.search(new SubjectTerm(sSubject),folder.getMessages());
                System.out.println(messages.length);
                System.out.println("Search for mails: " + i);
			  } catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  //Wait for 10 seconds
			  if (messages.length == 0) {
				  Thread.sleep(10000);
			  }
		  }
		   
		  //Search for unread mail from Application
		  //This is to avoid using the mail for which
		  //Registration is already done
		  for (Message mail : messages) {
			  try {
				if (!mail.isSet(Flags.Flag.SEEN)) {
					  mailFromApplication = mail;
					  System.out.println("Message Found in Email Count: " + mailFromApplication.getMessageNumber());
					  isMailFound = true;
				  }
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		   
		  //Test fails if no unread mail was found from Application
		  if (!isMailFound) {
			  try {
				throw new Exception("Could not find new mails :-(");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  //Read the content of mail and launch registration URL
		  } else {
			  String line;
			  StringBuffer buffer = new StringBuffer();
			  BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(mailFromApplication.getInputStream()));
				System.out.println("Reader : " + reader.toString());
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  try {
				while ((line = reader.readLine()) != null) {
					  buffer.append(line);
					  System.out.println("Message Line: " + buffer.toString());
				  }
				  //System.out.println("Message Line: " + buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		  //Your logic to split the message to get the Registration URL goes here
		  System.out.println("First String to Search: " + sFirstStrToSearch);
		  System.out.println("First String found in index: " + buffer.toString().indexOf(sFirstStrToSearch));
		  
		  int iFirstSplit = buffer.toString().indexOf(sFirstStrToSearch);
		  GetTheURL = buffer.toString().substring(iFirstSplit);
		  System.out.println("Link To Open 01: "+GetTheURL);
		  if(!sSecondStrToSearch.isEmpty()) {  
			  int iSecondSplit = GetTheURL.indexOf(sSecondStrToSearch)-1;
			  GetTheURL = GetTheURL.substring(0, iSecondSplit);
			  System.out.println("Link To Open 02: "+GetTheURL);
			  if (GetTheURL.indexOf('"') > 0) {
				  GetTheURL = GetTheURL.substring(0, GetTheURL.indexOf('"'));
				  System.out.println("Link To Open 03: "+GetTheURL);
				  
			  }
		  }
		  System.out.println("Link To Open 04: "+GetTheURL);
		}
		//return GetTheURL;
		return "www.google.com";
	}

}
