package me.puyodead1.abuseemailer;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class SendEmail {

	public static void SendMail(String recipent, String domain, String full_link, String otherlinks, String screenshots, String comments) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");
		Session session = Session.getInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("stopscammersnow2018@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipent, false));
		msg.setSubject("Scamming Site Popup");
		msg.setText("Dear Recipient,\r\n"
				+ "I am part of a community called Scammer.info that is dedicated to fighting scammers.\r\n"
				+ "I am emailing you regarding a scam site that is associated with your service.\r\n" + "\r\n"
				+ "Domain:\r\n" + "${DOMAIN_PLACEHOLDER}\r\n".replace("${DOMAIN_PLACEHOLDER}", domain) + "\r\n"
				+ "Full Link:\r\n" + "${LINK_PLACEHOLDER}\r\n".replace("${LINK_PLACEHOLDER}", full_link) + "\r\n"
				+ "Other Links (if any):\r\n" + "${OTHERURLS}\r\n".replace("${OTHERURLS}", otherlinks) + "\r\n" + "Screen Shot:\r\n"
				+ "${SCREENSHOT_URL}\r\n".replace("${SCREENSHOT_URL}", screenshots) + "\r\n" + "Comments (if any):\r\n" + "${COMMENTS}\r\n".replace("${COMMENTS}", comments) + "\r\n"
				+ "Thank You,\r\n"
				+ "StopScammersNow2018\r\n");
		msg.setHeader("X-Mailer", "Tov Are's program");
		msg.setSentDate(new Date());
		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
		t.connect("smtp.gmail.com", "stopscammersnow2018@gmail.com", "EMAILpassword");
		t.sendMessage(msg, msg.getAllRecipients());
		System.out.println("Response: " + t.getLastServerResponse());
		t.close();
	}
}
