package camunda.onboarding.workflow;

public class ProcessConstants {

	public static final String PROCESS_DEFINITION_KEY = "OnboardingProcess"; // BPMN Process ID

	public static final String MESSAGE_TOPIC_NAME = "employeeRecruitment";
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
	public static final String EMPLOYEE_SHOESIZE = "shoeSize";
	public static final String EMPLOYEE_T_SHIRT_SIZE = "tShirtSize";
	public static final String EMPLOYEE_NUMBER = "employeeNumber";

	/*
	 * Messages and Events
	 */
	public static final String MESSAGE_EMPLOYEE_RECRUITED = "Message_EmployeeRecruited";

}
