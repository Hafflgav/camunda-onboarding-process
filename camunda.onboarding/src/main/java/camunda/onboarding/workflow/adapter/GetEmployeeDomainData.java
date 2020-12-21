package camunda.onboarding.workflow.adapter;

import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import camunda.onboarding.workflow.entity.Employee;
import camunda.onboarding.workflow.repository.EmployeeRepository;

@Component("getEmployeeData")
public class GetEmployeeDomainData implements TaskListener {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void notify(DelegateTask task) {
		Long employeeId = (Long) task.getExecution().getVariable(ProcessConstants.EMPLOYEE_ID);
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		if(emp.isEmpty()) {
			throw new RuntimeException("Employee with id %s could not be found!");
		}
		
		Employee employee = emp.get();
		task.setVariableLocal(ProcessConstants.EMPLOYEE_NAME, employee.getFirstName() + " " + employee.getLastName());
		task.setVariableLocal(ProcessConstants.EMPLOYEE_SHOESIZE, employee.getShoeSize());
		task.setVariableLocal(ProcessConstants.EMPLOYEE_T_SHIRT_SIZE, employee.gettShirtSize());
		
	}

}
