package camunda.onboarding.workflow.adapter;

import java.io.InputStream;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;

import camunda.onboarding.workflow.ProcessConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SaveDocumentInSharePointTaskListener implements TaskListener {

	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=hrdemo;AccountKey=RjwtFAch+WwhFXSmQg71ZlRYUVWZQGsY2QO6B4krwGPy4vjiMU7bvCuZtzUL63mcxB+nAY3DsZFjdYSZjLr7qA==;EndpointSuffix=core.windows.net";

	@Override
	public void notify(DelegateTask delegateTask) {

		String employeeNumber = (String) delegateTask.getVariable(ProcessConstants.EMPLOYEE_NUMBER);
		
		FileValue retrievedTypedFileValue = (FileValue) delegateTask.getVariableTyped(ProcessConstants.TAX_DOCUMENT);
		InputStream fileContent = retrievedTypedFileValue.getValue(); // a byte stream of the file contents
		

		log.info("Uploading the document to SharePoint now via Microsoft Graph REST API!");

		// Use the CloudStorageAccount object to connect to your storage account
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// Create the Azure Files client.
			CloudFileClient fileClient = storageAccount.createCloudFileClient();

			// Get a reference to the file share
			CloudFileShare share = fileClient.getShareReference("hrdata");

			if (share.createIfNotExists()) {
				log.info("New share created");
			}

			// Get a reference to the root directory for the share.
			CloudFileDirectory rootDir = share.getRootDirectoryReference();

			// Get a reference to the sampledir directory
			CloudFileDirectory employeeDir = rootDir.getDirectoryReference(employeeNumber);

			if (employeeDir.createIfNotExists()) {
				log.info("Directory for " + employeeNumber + " created");
			} else {
				log.info("Directory for " + employeeNumber + " already exists...");
			}

			CloudFile cloudFile = employeeDir.getFileReference("taxDocument-" + employeeNumber + ".pdf");
			cloudFile.upload(fileContent, fileContent.available());

		} catch (Exception e) {
			log.error("Ooops", e);
		}

	}

}
