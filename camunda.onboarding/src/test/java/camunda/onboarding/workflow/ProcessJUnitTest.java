package camunda.onboarding.workflow;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import camunda.onboarding.workflow.adapter.AddEmployeeToHRSystemDelegate;
import camunda.onboarding.workflow.adapter.GetEmployeeDomainData;
import camunda.onboarding.workflow.adapter.SaveDocumentInSharePointTaskListener;
import camunda.onboarding.workflow.adapter.SendMailDelegate;
import camunda.onboarding.workflow.adapter.service.CloudStorageService;
import camunda.onboarding.workflow.adapter.service.JavaMailer;
import camunda.onboarding.workflow.adapter.service.Mail;
import camunda.onboarding.workflow.entity.Employee;
import camunda.onboarding.workflow.repository.EmployeeRepository;

public class ProcessJUnitTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private CloudStorageService cloudStorageService;

	@Mock
	private JavaMailer mailer;

	@InjectMocks
	private AddEmployeeToHRSystemDelegate addEmployeeToHRSystemDelegate;

	@InjectMocks
	private GetEmployeeDomainData getEmployeeData;

	@InjectMocks
	private SendMailDelegate sendMailDelegate;

	@InjectMocks
	private SaveDocumentInSharePointTaskListener saveDocumentInSharePointTaskListener;

	@ClassRule
	@Rule
	public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

	@Before
	public void setup() {
		init(rule.getProcessEngine());
		MockitoAnnotations.initMocks(this);
		Mocks.register("addEmployeeToHRSystem", addEmployeeToHRSystemDelegate);
		Mocks.register("getEmployeeData", getEmployeeData);
		Mocks.register("saveDocumentInSharePointTaskListener", saveDocumentInSharePointTaskListener);
		Mocks.register("sendMail", sendMailDelegate);
	}

	@Test
	@Deployment(resources = { "onboarding-process.bpmn", "OnboardingDecision.dmn" })
	public void testHappyPath() throws Exception {

		Employee employee = new Employee(//
				"Paule", //
				"Lehmann", //
				"8.0", //
				"M", //
				"Manager", //
				"IT", //
				LocalDate.parse("2021-01-01"), //
				"employee-22", //
				"paule.lehmann@demo.com");
		employee.setId(101L);

		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
		Mockito.when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee));

		String jsonData = "{"//
				+ "                \"startDate\": \"2021-01-01\","//
				+ "                \"employeeNumber\": \"employee-22\","//
				+ "                \"name\": \"Paule\","//
				+ "                \"lastName\": \"Lehmann\","//
				+ "                \"shoeSize\": \"8.5\","//
				+ "                \"tShirtSize\": \"M\","//
				+ "                \"role\": \"Manager\","//
				+ "                \"department\": \"IT\","//
				+ "                \"email\": \"paule.lehmann@demo.com\""//
				+ "            }";//

		JsonValue jsonValue = SpinValues.jsonValue(jsonData).create();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessConstants.EMPLOYEE, jsonValue);

		ProcessInstance processInstance = runtimeService()
				.createMessageCorrelation(ProcessConstants.MESSAGE_EMPLOYEE_RECRUITED).setVariables(variables)
				.correlateStartMessage();

		assertThat(processInstance).isWaitingAt("EmployeeRecruitedStartEvent");
		execute(job());

		assertThat(processInstance).isWaitingAt("SelectTasksBasedOnRoleTask");
		execute(job());

		assertThat(processInstance).isWaitingAt("CompleteOnboardingFormTask", "ProcureLaptopTask",
				"PrepareDeskUIPathTask");

		FileValue typedFileValue = Variables.fileValue("taxDoxument.pdf").file(new File("src/test/resources/dummy.pdf"))
				.mimeType("application/pdf").create();

		complete(task(), withVariables(ProcessConstants.TAX_DOCUMENT, typedFileValue));

		complete(externalTask("PrepareDeskUIPathTask"));
		execute(job("PrepareDeskUIPathTask"));
		complete(externalTask("ProcureLaptopTask"));

		List<Job> jobs = processEngine().getManagementService().createJobQuery().activityId("MergingTasksGateway")
				.active().list();
		for (Job job : jobs) {
			managementService().executeJob(job.getId());
		}

		assertThat(processInstance).isWaitingAt("SendFirstDayWelcomeMailTask");
		execute(job());
		
		assertThat(processInstance).isWaitingAt("WaitForDecisionEvent");

		runtimeService().createMessageCorrelation(ProcessConstants.MESSAGE_EMPLOYMENT_DECISION)
				.setVariable(ProcessConstants.PERMANENTLY_EMPLOYED, true).correlate();

		Mockito.verify(employeeRepository, Mockito.times(1)).save(Mockito.any(Employee.class));
		Mockito.verify(employeeRepository, Mockito.times(1)).findById(101L);
		Mockito.verify(cloudStorageService, Mockito.times(1)).storeDocument(Mockito.any(InputStream.class),
				Mockito.eq("employee-22"));
		Mockito.verify(mailer, Mockito.times(1)).sendmail(Mockito.any(Mail.class));

	}

}