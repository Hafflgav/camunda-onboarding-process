package camunda.mail.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailer {

    private LogManager lgmngr = LogManager.getLogManager();
    private Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final  String MAILADDRESS = "demo@camunda.com";
    private static final  String USERNAME = "demo@mx.camunda.com";
    private static final  String PASSPHRASE = "${SHOWCASE_MAIL_PASSWORD}";

    public void sendmail(Mail mail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.camunda.com");
        props.put("mail.smtp.port", "2025");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSPHRASE);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(MAILADDRESS, false));

        msg.setRecipients(Message.RecipientType.TO, encodeRecipientsToInternetAddresses(mail.getMailRecipients()));
        msg.setSubject(mail.getMailSubject());
        msg.setContent(mail.getMailBody(), "text/html;charset=UTF-8");

        Transport.send(msg);
    }

    private InternetAddress[] encodeRecipientsToInternetAddresses(List<String> recipients){
        ArrayList<InternetAddress> internetAddresses = new ArrayList<>();
        for(String recipient : recipients){
            try {
                internetAddresses.add(new InternetAddress(recipient));
            } catch (AddressException e) {
                log.log(Level.SEVERE, "Exception occur", e);
            }
        }
        return internetAddresses.toArray(InternetAddress[]::new);
    }
}
