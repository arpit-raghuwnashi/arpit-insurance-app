package com.impetus.utility;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUtilis {

	private static final Logger logger = LoggerFactory.getLogger(SendEmailUtilis.class);

	private static String headerMessage = "<div class=container><div class=card bg-light p-3>"
			+ "<h3>Hi</h3><h4>Thanks for signing up at INSURANCE APP</h4> <br>";

	private static String footerMessage = "<p>Thanks and Regards<br>" + "  InsuranceApp.com <br>"
			+ " contact- XXXXXXXXXXX<br>" + "</p>" + "</div>" + "</div>";

	private SendEmailUtilis() {
	}

	/**
	 * @param to
	 * @param subject
	 * @param message
	 * @throws MessagingException Sending OTP to User mail address
	 */
	public static void sendmail(String to, String subject, String message) throws MessagingException {
		logger.info("Inside sendmail method in ");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("impinsimpins33@gmail.com", "impinsimpins33@@");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("impinsimpins33@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(subject, "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		msg.setContent(multipart);
		Transport.send(msg);
	}

	/**
	 * Setting proper mail format before send
	 */
	public static void properMailFormat(String email, int otp) {
		String subject = "Here's your One Time Password (OTP)";

		String content = headerMessage
				+ "<p> We have received your forgot passwrod request for new <span style=color: red; font-weight: 100;>Password</span> generation</p>"
				+ "<p>We sent you OPT on you registered mail id.</p><br><br><br>"

				+ "<label>OTP : </label>" + otp + "<br><br><br><br>"

				+ "<p>There will be no need for any further action from your end.</p><p>If you have any further questions, please visit our website.</p>"
				+ "<br><p>We delight to have you with us</p>" + footerMessage;

		try {
			logger.info("Inside properMailFormet method in SendEmailUtilis");
			sendmail(email, subject, content);
		} catch (MessagingException e) {
			logger.error("Error while sending mail in properMailFormat method in SendEmailUtilis");
			throw new ExceptionUtils("Exception while sending OTP mail");
		}
	}

	/**
	 * @param email
	 * @param message
	 * @param policy  Sending approval of pending request
	 */
	public static void policyApprovedMail(String email, String message) {
		String subject = "Policy Status Approved";
		String content = headerMessage
				+ "<p> This mail is about your <span style=color: red; font-weight: 100;>Pending policy status has been changed </span></p>"
				+ "<p>You Policy  </p> has been APPROVED by our executive<br><br><br>"

				+ "<label>Query : </label>" + message + "<br></br><br><br>"

				+ "<p>There will be no need for any further action from your end.</p>"
				+ "<p>If you have any further questions, please visit our website.</p>" + "<br>"
				+ " Have a great day ahead." + "<br>" + "<p>We delight to have you with us</p>" + footerMessage;
		try {
			logger.info("Inside policyApprovedMail method in SendEmailUtilis");
			sendmail(email, subject, content);
		} catch (MessagingException e) {
			logger.error("Error while sending mail in policyApprovedMail method in SendEmailUtilis");
			throw new ExceptionUtils("Exception while sending Approved mail");
		}
	}

	/**
	 * @param email
	 * @param message
	 * @param policy  sending Decline mail of pending policy
	 */

	public static void policyDeclineMail(String email, String message) {
		String subject = "Policy Status Decline";
		String content = headerMessage
				+ "<p> This mail is about your <span style=color: red; font-weight: 100;>Pending policy status has been changed </span></p>"
				+ "<p>You Policy  </p> has been DECLINE by our executive<br><br><br>"
				+ "<p>Certain criteria has not met.</p>"

				+ "<label>Query : </label>" + message + "<br></br><br><br>"

				+ "<p>For More query and information you can contact our executive.</p>"
				+ "<p>There will be no need for any further action from your end.</p>"
				+ "<p>If you have any further questions, please visit our website.</p>" + "<br>"
				+ " Have a great day ahead." + "<br>" + "<p>We delight to have you with us</p>" + footerMessage;

		try {
			logger.info("Inside policyDeclineMail method in SendEmailUtilis");
			sendmail(email, subject, content);
		} catch (MessagingException e) {
			logger.error("Error while sending mail in policyDeclineMail method in SendEmailUtilis");
			throw new ExceptionUtils("Exception while sending Decline mail");
		}
	}

}
