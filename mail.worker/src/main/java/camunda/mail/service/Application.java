package camunda.mail.service;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.logging.Level;

@SpringBootApplication
public class Application {
    private static final String TOPIC = "mailingTopic";
    private static final String CAMUNDAENDPOINT = "http://localhost:8080/engine-rest";
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());
    private static String content = new String();
    private static String recipient = new String();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(CAMUNDAENDPOINT)
                .asyncResponseTimeout(10000)
                .disableBackoffStrategy()
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder subscriptionBuilder = client
                .subscribe(TOPIC)
                .lockDuration(20000)
                .handler((externalTask, externalTaskService) -> {

                    Mail mail = createMailFromVariables(externalTask);
                    JavaMailer mailer = new JavaMailer();
                    try {
                        LOGGER.info("Mail sent to: " + recipient);
                        LOGGER.info("Content: " + content);
                        mailer.sendmail(mail);
                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        LOGGER.info((Marker) Level.SEVERE, "Exception occured", e);
                    }
                });

        client.start();
        subscriptionBuilder.open();
    }

    private static Mail createMailFromVariables(ExternalTask externalTask){
        Mail email = new Mail();
        try {
            String mailRecipients = externalTask.getVariable("mailRecipients");
            email.setMailRecipients( Arrays.asList(mailRecipients.split("\\s*,\\s*")));
            email.setMailBody(externalTask.getVariable("mailBody"));
            email.setMailSubject(externalTask.getVariable("mailSubject"));
        } catch (Exception e) {
            LOGGER.info((Marker) Level.SEVERE, "Exception occur", e);
        }
        return email;
    }
}
