package camunda.onboarding.workflow.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import camunda.onboarding.workflow.repository.EmployeeRepository;

@Component("removeUserFromHRSystem")
public class RemoveUserFromHRSystemDelegate implements JavaDelegate {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Long employeeId = (Long) execution.getVariable(ProcessConstants.EMPLOYEE_ID);
		employeeRepository.deleteById(employeeId);
		employeeRepository.flush();
	}

}
