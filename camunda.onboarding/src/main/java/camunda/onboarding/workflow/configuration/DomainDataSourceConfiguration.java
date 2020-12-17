package camunda.onboarding.workflow.configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration()
@EnableTransactionManagement
// @formatter:off
@EnableJpaRepositories(basePackages = "camunda.onboarding.workflow.repository", 
					   entityManagerFactoryRef = "domainEntityManagerFactory", 
					   transactionManagerRef = "domainTransactionManager")
// @formatter:on
public class DomainDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "domain.datasource")
	public DataSourceProperties domainDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "domain.datasource.configuration")
	public HikariDataSource domainDataSource() {
		return domainDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean(name = "domainEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean domainEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		// @formatter:off
		return builder
				.dataSource(domainDataSource())
				.packages("org.camunda.demo.multipleDatasources.entity")
				.persistenceUnit("domainEntityManager")
				.build();
		// @formatter:on
	}

	@Bean
	public PlatformTransactionManager domainTransactionManager(
			final @Qualifier("domainEntityManagerFactory") LocalContainerEntityManagerFactoryBean domainEntityManagerFactory) {
		return new JpaTransactionManager(domainEntityManagerFactory.getObject());
	}
}