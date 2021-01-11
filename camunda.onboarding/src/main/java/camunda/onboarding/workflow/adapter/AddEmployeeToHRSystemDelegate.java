package camunda.onboarding.workflow.adapter;

import java.time.LocalDate;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import camunda.onboarding.workflow.entity.Employee;
import camunda.onboarding.workflow.repository.EmployeeRepository;

@Component("addEmployeeToHRSystem")
public class AddEmployeeToHRSystemDelegate implements JavaDelegate {

	@Autowired
	private EmployeeRepository employeeRepository;


	@Override
	public void execute(DelegateExecution execution) throws Exception {

		SpinJsonNode employeeData = (SpinJsonNode) execution.getVariable(ProcessConstants.EMPLOYEE);
		Employee employee = new Employee(//
				stringValue(employeeData, "name"), //
				stringValue(employeeData, "lastName"), //
				stringValue(employeeData, "shoeSize"), //
				stringValue(employeeData, "tShirtSize"), //
				stringValue(employeeData, "role"), //
				stringValue(employeeData, "department"), //
				LocalDate.parse((stringValue(employeeData, "startDate"))), //
				stringValue(employeeData, "employeeNumber"), //
				stringValue(employeeData, "email"));

		employee = employeeRepository.save(employee);
		execution.setVariable(ProcessConstants.EMPLOYEE_NAME, employee.getFirstName() + " " + employee.getLastName());
		execution.setVariable(ProcessConstants.EMPLOYEE_ID, employee.getId());
		execution.setVariable(ProcessConstants.EMPLOYEE_ROLE, employee.getRoleTitle());
		execution.setVariable(ProcessConstants.EMPLOYEE_DEPARTMENT, employee.getDepartment());
		execution.setVariable(ProcessConstants.EMPLOYEE_NUMBER, employee.getEmployeeNumber());
		execution.setVariable(ProcessConstants.EMPLOYEE_EMAIL, employee.getEmail());
		
		//delete variable "employee" from Runtime
		execution.getProcessInstance().removeVariable("employee");

		//delete variable "employee" from History
		/*
		HistoricVariableInstance varInstance = historyService//
			.createHistoricVariableInstanceQuery()//
			.processInstanceId(execution.getProcessInstanceId())//
			.variableName(ProcessConstants.EMPLOYEE).singleResult();
		historyService.deleteHistoricVariableInstance(varInstance.getId());
		*/
		
		
		employeeRepository.flush();
	}

	private String stringValue(SpinJsonNode node, String path) {
		return node.jsonPath(path).stringValue();
	}
}
