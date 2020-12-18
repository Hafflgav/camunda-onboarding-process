package camunda.onboarding.workflow.configuration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class MyCamundaDatasourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "camunda.datasource")
	public DataSourceProperties camundaDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "camunda.datasource.configuration")
	public HikariDataSource camundaDataSource() {
		return camundaDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean("camundaTransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(camundaDataSource());
	}

}