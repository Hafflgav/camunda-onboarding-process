package camunda.mail.service;

import java.util.List;

public class Mail {
    private String mailBody;
    private String mailSubject;
    private List<String> mailRecipients;

    public List<String> getMailRecipients() { return mailRecipients; }

    public void setMailRecipients(List<String> mailRecipients) { this.mailRecipients = mailRecipients; }

    public String getMailBody() {
        return mailBody;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public Mail(String mailBody, String mailSubject,  List<String> mailRecipients) {
        this.mailBody = mailBody;
        this.mailSubject = mailSubject;
        this.mailRecipients = mailRecipients;
    }

    public Mail(){}
}
