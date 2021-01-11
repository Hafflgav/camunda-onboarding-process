package camunda.onboarding.workflow.adapter;

import java.util.Arrays;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import camunda.onboarding.workflow.adapter.service.JavaMailer;
import camunda.onboarding.workflow.adapter.service.Mail;

@Component("sendMail")
public class SendMailDelegate implements JavaDelegate {

	@Autowired
	private JavaMailer mailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String name = (String) execution.getVariable(ProcessConstants.EMPLOYEE_NAME);
		String number = (String) execution.getVariable(ProcessConstants.EMPLOYEE_NUMBER);
		String emailAddress = (String) execution.getVariable(ProcessConstants.EMPLOYEE_EMAIL);
		
		Mail mail = new Mail();
		mail.setMailBody(String.format("Hello %s, "
				+ "<br>We are happy to have you on board and looking forward to have a great time together!"
				+ "<br>Your employee number is <b>%s</b>, you will need it for any conversations with HR department."
				+ "<p>Have a good start!"
				+ "<br>Best regards, your HR Department"
				
				, name, number));
		
		mail.setMailSubject("Welcome on board!");
		mail.setMailRecipients(Arrays.asList(emailAddress));
		mailService.sendmail(mail);

	}

}
