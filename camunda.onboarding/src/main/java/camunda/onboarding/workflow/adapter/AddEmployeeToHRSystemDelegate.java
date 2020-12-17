package camunda.onboarding.workflow.adapter;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.entity.Employee;
import camunda.onboarding.workflow.repository.EmployeeRepository;

@Component("addEmployeeToHRSystem")
public class AddEmployeeToHRSystemDelegate implements JavaDelegate {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		employeeRepository.save(new Employee("Maxe", "Meier", "8.5", "L", "Boss", "HR", new Date(), "emp-1"));
		employeeRepository.flush();
	}

}
