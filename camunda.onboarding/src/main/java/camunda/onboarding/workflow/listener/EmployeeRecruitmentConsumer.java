package camunda.onboarding.workflow.listener;

import static camunda.onboarding.workflow.ProcessConstants.*;
import static org.camunda.spin.Spin.*;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import camunda.onboarding.workflow.ProcessConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = MESSAGE_TOPIC_NAME)
public class EmployeeRecruitmentConsumer {

	@Autowired
	private RuntimeService runtimeService;

	@KafkaHandler
	public void process(String record) {
		log.info("*** Reading message {} ***", record);

		SpinJsonNode employeeData = JSON(record);

		runtimeService.createMessageCorrelation(ProcessConstants.MESSAGE_EMPLOYEE_RECRUITED)
				.setVariable("employee", employeeData).correlate();
		;

	}

}