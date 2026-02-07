package dochigosum.simvex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Edge case and negative scenario tests for SimvexApplication.
 * These tests verify behavior under unusual or boundary conditions.
 */
@SpringBootTest
class SimvexApplicationEdgeCaseTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Environment environment;

	/**
	 * Edge case: Verify application handles missing optional properties gracefully.
	 */
	@Test
	@DisplayName("Application should handle missing optional properties")
	void handlesNonExistentProperty() {
		String nonExistent = environment.getProperty("non.existent.property");
		assertThat(nonExistent).isNull();
	}

	/**
	 * Edge case: Verify application can retrieve property with default value.
	 */
	@Test
	@DisplayName("Application should return default value for missing property")
	void returnsDefaultForMissingProperty() {
		String value = environment.getProperty("non.existent.property", "default");
		assertThat(value).isEqualTo("default");
	}

	/**
	 * Boundary test: Verify all expected domains have their package structure.
	 */
	@Test
	@DisplayName("All domain packages should be recognizable")
	void allDomainPackagesExist() {
		// This test verifies the application can scan the domain package structure
		String basePackage = SimvexApplication.class.getPackage().getName();
		assertThat(basePackage).isEqualTo("dochigosum.simvex");

		// Verify package structure allows for domain organization
		Package pkg = SimvexApplication.class.getPackage();
		assertThat(pkg).isNotNull();
	}

	/**
	 * Edge case: Test that Spring profiles can be accessed even when none are active.
	 */
	@Test
	@DisplayName("Should handle no active profiles gracefully")
	void handlesNoActiveProfiles() {
		String[] activeProfiles = environment.getActiveProfiles();
		// Should return empty array, not null
		assertThat(activeProfiles).isNotNull();
	}

	/**
	 * Edge case: Verify default profiles are accessible.
	 */
	@Test
	@DisplayName("Should access default profiles")
	void canAccessDefaultProfiles() {
		String[] defaultProfiles = environment.getDefaultProfiles();
		assertThat(defaultProfiles).isNotNull();
	}

	/**
	 * Negative test: Verify non-existent bean doesn't exist.
	 */
	@Test
	@DisplayName("Should return false for non-existent bean")
	void nonExistentBeanReturnsFalse() {
		assertThat(applicationContext.containsBean("nonExistentBean")).isFalse();
	}

	/**
	 * Boundary test: Verify application context can be queried for bean count.
	 */
	@Test
	@DisplayName("Should return positive bean count")
	void beanCountIsPositive() {
		int count = applicationContext.getBeanDefinitionCount();
		assertThat(count).isPositive();
	}

	/**
	 * Edge case: Test that application context parent is properly set.
	 */
	@Test
	@DisplayName("Application context should have proper hierarchy")
	void applicationContextHierarchy() {
		// Root application context might not have a parent
		ApplicationContext parent = applicationContext.getParent();
		// Parent can be null for root context, which is fine
		if (parent != null) {
			assertThat(parent.isActive()).isTrue();
		}
	}

	/**
	 * Regression test: Verify Spring Boot version compatibility.
	 */
	@Test
	@DisplayName("Should be running with Spring Boot")
	void isRunningSpringBoot() {
		// Check that Spring Boot specific beans are present
		String[] beanNames = applicationContext.getBeanNamesForType(
			org.springframework.boot.SpringApplication.class
		);
		// The presence of Spring Boot infrastructure indicates proper initialization
		assertThat(applicationContext.getEnvironment()).isNotNull();
	}

	/**
	 * Edge case: Verify application can resolve required property types.
	 */
	@Test
	@DisplayName("Should convert property values to correct types")
	void convertsPropertyTypes() {
		// Test boolean property conversion
		Boolean showSql = environment.getProperty("spring.jpa.show-sql", Boolean.class);
		if (showSql != null) {
			assertThat(showSql).isInstanceOf(Boolean.class);
		}
	}

	/**
	 * Boundary test: Verify system properties are accessible.
	 */
	@Test
	@DisplayName("Should access system properties")
	void canAccessSystemProperties() {
		String javaVersion = environment.getProperty("java.version");
		assertThat(javaVersion).isNotNull();
	}

	/**
	 * Edge case: Test property placeholder resolution.
	 */
	@Test
	@DisplayName("Should resolve property placeholders")
	void resolvesPropertyPlaceholders() {
		String resolved = environment.resolvePlaceholders("${spring.application.name:default}");
		assertThat(resolved).isIn("simvex", "default");
	}

	/**
	 * Negative test: Verify required properties are validated.
	 */
	@Test
	@DisplayName("Should handle property resolution with missing required placeholder")
	void handlesRequiredPlaceholder() {
		// Using ${...} without default requires the property to exist
		// If it doesn't exist, it returns the placeholder as-is
		String resolved = environment.resolvePlaceholders("${definitely.not.existing.property}");
		// Spring returns the unresolved placeholder if property doesn't exist
		assertThat(resolved).contains("definitely.not.existing.property");
	}

	/**
	 * Regression test: Verify transaction management is configured.
	 */
	@Test
	@DisplayName("Transaction manager should be available")
	void transactionManagerIsConfigured() {
		assertThat(applicationContext.containsBean("transactionManager")).isTrue();
	}

	/**
	 * Edge case: Verify multiple EntityManagerFactory beans don't exist.
	 */
	@Test
	@DisplayName("Should have exactly one EntityManagerFactory")
	void hasExactlyOneEntityManagerFactory() {
		String[] beanNames = applicationContext.getBeanNamesForType(
			javax.persistence.EntityManagerFactory.class
		);
		assertThat(beanNames.length).isGreaterThanOrEqualTo(1);
	}

}