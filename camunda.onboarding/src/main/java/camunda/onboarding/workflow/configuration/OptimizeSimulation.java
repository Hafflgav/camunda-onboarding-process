package camunda.onboarding.workflow.configuration;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.camunda.consulting.simulator.SimulationExecutor;

@Component
@Profile("optimize-demo")
public class OptimizeSimulation {

	@Value("${optimize.demo.numOfMonthsToSimulate}")
	private Integer numOfMonthsToSimulate;
	
	@EventListener
	public void onPostDeploy(PostDeployEvent event) {
		System.out.println("Running the simulation now...>");
		SimulationExecutor.execute(DateTime.now().minusMonths(numOfMonthsToSimulate).toDate(), DateTime.now().toDate());
	}
}
