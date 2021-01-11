package camunda.onboarding.workflow.adapter.service;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CloudStorageService {

	@Value("${azure.storage.connectionString}")
	private String storageConnectionString;

	public void storeDocument(InputStream fileContent, String employeeNumber) throws Exception {

		// Use the CloudStorageAccount object to connect to your storage account
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

	}
}
