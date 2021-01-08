package camunda.onboarding.workflow;

public class ProcessConstants {

	public static final String PROCESS_DEFINITION_KEY = "OnboardingProcess"; // BPMN Process ID

	public static final String TOPIC_EMPLOYEE_RECRUITED = "employeeRecruitment";
	public static final String TOPIC_EMPLOYMENT_DECISION = "employmentDecision";
	/*
	 * Activities
	 */
	// add here if needed

	/*
	 * Variables
	 */
	public static final String EMPLOYEE_ID = "employeeId";
	public static final String EMPLOYEE = "employee";
	public static final String EMPLOYEE_ROLE = "role";
	public static final String EMPLOYEE_DEPARTMENT = "department";
	public static final String EMPLOYEE_NAME = "fullname";
	public static final String EMPLOYEE_EMAIL = "email";
	public static final String EMPLOYEE_SHOESIZE = "shoeSize";
	public static final String EMPLOYEE_T_SHIRT_SIZE = "tShirtSize";
	public static final String EMPLOYEE_NUMBER = "employeeNumber";
	public static final String PERMANENTLY_EMPLOYED = "permanentlyEmployed";

	/*
	 * Messages and Events
	 */
	public static final String MESSAGE_EMPLOYEE_RECRUITED = "Message_EmployeeRecruited";
	public static final String MESSAGE_EMPLOYMENT_DECISION = "Message_EmploymentDecision";


}
