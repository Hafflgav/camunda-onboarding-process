package camunda.onboarding.workflow.adapter;

import java.io.InputStream;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import camunda.onboarding.workflow.adapter.service.CloudStorageService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SaveDocumentInSharePointTaskListener implements TaskListener {

	@Autowired
	private CloudStorageService cloudStorageService;

	@Override
	public void notify(DelegateTask delegateTask) {

		String employeeNumber = (String) delegateTask.getVariable(ProcessConstants.EMPLOYEE_NUMBER);

		FileValue retrievedTypedFileValue = (FileValue) delegateTask.getVariableTyped(ProcessConstants.TAX_DOCUMENT);
		InputStream fileContent = retrievedTypedFileValue.getValue(); // a byte stream of the file contents

		log.info("Uploading the document to Azure Storage!");

		try {
			cloudStorageService.storeDocument(fileContent, employeeNumber);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
