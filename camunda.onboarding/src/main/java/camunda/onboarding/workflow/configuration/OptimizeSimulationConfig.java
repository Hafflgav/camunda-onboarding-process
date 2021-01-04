package camunda.onboarding.workflow.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.camunda.consulting.simulator.PayloadGenerator;
import com.camunda.consulting.simulator.SimulatorPlugin;

@Configuration
@Profile("optimize-demo")
public class OptimizeSimulationConfig {

	/**
	 * Makes the PayloadGenerator available in expressions and scripts.
	 */
	@Bean
	public PayloadGenerator g() {
		PayloadGenerator g = new PayloadGenerator();
		return g;
	}

	@Bean
	public SimulatorPlugin simulatorPlugin() {
		return new SimulatorPlugin();
	}
}
