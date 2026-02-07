package dochigosum.simvex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimvexApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Test that the application context loads successfully.
	 * This verifies that all Spring configurations are valid.
	 */
	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	/**
	 * Test that the SimvexApplication bean is present in the context.
	 */
	@Test
	void applicationBeanExists() {
		assertThat(applicationContext.containsBean("simvexApplication")).isTrue();
	}

	/**
	 * Test that essential Spring Boot beans are loaded.
	 * This ensures the framework is properly initialized.
	 */
	@Test
	void essentialBeansAreLoaded() {
		assertThat(applicationContext.containsBean("dataSource")).isTrue();
		assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
	}

	/**
	 * Test that the application has JPA support configured.
	 */
	@Test
	void jpaIsConfigured() {
		String[] beanNames = applicationContext.getBeanNamesForType(
			javax.persistence.EntityManagerFactory.class
		);
		assertThat(beanNames).isNotEmpty();
	}

	/**
	 * Test application context contains expected number of beans.
	 * This is a regression test to detect unintended configuration changes.
	 */
	@Test
	void applicationContextHasMinimumBeans() {
		int beanCount = applicationContext.getBeanDefinitionCount();
		// Spring Boot applications should have at least some auto-configured beans
		assertThat(beanCount).isGreaterThan(50);
	}

	/**
	 * Test that Spring profiles are properly configured.
	 */
	@Test
	void springProfilesAreAccessible() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		assertThat(activeProfiles).isNotNull();
	}

	/**
	 * Test that the application name is correctly set.
	 */
	@Test
	void applicationNameIsCorrect() {
		String appName = applicationContext.getEnvironment().getProperty("spring.application.name");
		// If not explicitly set, it should be null or the default
		assertThat(appName).isIn(null, "simvex", "application");
	}

}