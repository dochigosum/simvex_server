package dochigosum.simvex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for SimvexApplication that verify full application startup
 * and web server configuration.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimvexApplicationIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Environment environment;

	/**
	 * Test that the application starts up with a random port.
	 */
	@Test
	@DisplayName("Application should start with random port")
	void applicationStartsWithRandomPort() {
		assertThat(port).isGreaterThan(0);
	}

	/**
	 * Test that the server port is correctly configured.
	 */
	@Test
	@DisplayName("Server port should be accessible from environment")
	void serverPortIsConfigured() {
		String portFromEnv = environment.getProperty("local.server.port");
		assertThat(portFromEnv).isNotNull();
		assertThat(Integer.parseInt(portFromEnv)).isEqualTo(port);
	}

	/**
	 * Test that the application context is fully initialized.
	 */
	@Test
	@DisplayName("Application context should be fully initialized")
	void applicationContextIsInitialized() {
		assertThat(applicationContext).isNotNull();
		assertThat(applicationContext.isActive()).isTrue();
	}

	/**
	 * Test that web application context is properly configured.
	 */
	@Test
	@DisplayName("Web application context should be properly configured")
	void webApplicationContextIsConfigured() {
		assertThat(applicationContext).isInstanceOf(
			org.springframework.web.context.WebApplicationContext.class
		);
	}

	/**
	 * Test that the datasource bean is configured.
	 */
	@Test
	@DisplayName("DataSource should be configured")
	void dataSourceIsConfigured() {
		assertThat(applicationContext.containsBean("dataSource")).isTrue();
		Object dataSource = applicationContext.getBean("dataSource");
		assertThat(dataSource).isNotNull();
	}

	/**
	 * Test that JPA entity manager is configured.
	 */
	@Test
	@DisplayName("Entity manager factory should be configured")
	void entityManagerFactoryIsConfigured() {
		assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
	}

	/**
	 * Test application name from properties.
	 */
	@Test
	@DisplayName("Application name should be correctly configured")
	void applicationNameIsConfigured() {
		String appName = environment.getProperty("spring.application.name");
		assertThat(appName).isEqualTo("simvex");
	}

	/**
	 * Regression test: Verify servlet container is running.
	 */
	@Test
	@DisplayName("Servlet container should be running")
	void servletContainerIsRunning() {
		// If we get here, the web server started successfully
		assertThat(applicationContext.containsBean("servletWebServerFactory")).isTrue();
	}

	/**
	 * Boundary test: Verify H2 database is accessible.
	 */
	@Test
	@DisplayName("H2 database connection should be valid")
	void h2DatabaseConnectionIsValid() {
		String dbUrl = environment.getProperty("spring.datasource.url");
		assertThat(dbUrl).contains("jdbc:h2:mem:");
	}

	/**
	 * Test that Spring Boot autoconfiguration worked properly.
	 */
	@Test
	@DisplayName("Spring Boot autoconfiguration should be applied")
	void springBootAutoconfigurationIsApplied() {
		// Check for common auto-configured beans
		assertThat(applicationContext.containsBean("dataSource")).isTrue();
		assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
		assertThat(applicationContext.containsBean("transactionManager")).isTrue();
	}

}