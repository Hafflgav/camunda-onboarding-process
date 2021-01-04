package camunda.onboarding.workflow.adapter;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.listener.EmployeeRecruitmentConsumer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SaveDocumentInSharePointTaskListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		log.info("Uploading the document to SharePoint now via Microsoft Graph REST API!");
	}

}
