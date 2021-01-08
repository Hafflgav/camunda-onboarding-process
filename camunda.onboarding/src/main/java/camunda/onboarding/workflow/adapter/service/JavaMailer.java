package camunda.onboarding.workflow.adapter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JavaMailer {

	@Value("${mail.from}")
	private String MAILFROM;
	
	@Value("${mail.username}")
	private String USERNAME;
	
	@Value("${mail.password}")
	private String PASSPHRASE;
	
	@Value("${mail.smtp.host}")
	private String HOST;
	
	@Value("${mail.smtp.port}")
	private String PORT;

	public void sendmail(Mail mail) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSPHRASE);
			}
		});

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(MAILFROM, false));

		msg.setRecipients(Message.RecipientType.TO, encodeRecipientsToInternetAddresses(mail.getMailRecipients()));
		msg.setSubject(mail.getMailSubject());
		msg.setContent(mail.getMailBody(), "text/html;charset=UTF-8");

		Transport.send(msg);
	}

	private InternetAddress[] encodeRecipientsToInternetAddresses(List<String> recipients) {
		ArrayList<InternetAddress> internetAddresses = new ArrayList<>();
		for (String recipient : recipients) {
			try {
				internetAddresses.add(new InternetAddress(recipient));
			} catch (AddressException e) {
				log.error("Exception occured", e);
			}
		}
		return internetAddresses.toArray(InternetAddress[]::new);
	}
}
