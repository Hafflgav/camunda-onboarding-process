package camunda.onboarding.workflow.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.listener.EmployeeRecruitmentConsumer;
import lombok.extern.slf4j.Slf4j;

@Component("prepareDesk")
@Slf4j
public class PrepareDeskDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Preparing a desk for a new employee!");
	}

}
