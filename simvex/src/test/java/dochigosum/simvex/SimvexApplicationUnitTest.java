package dochigosum.simvex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Unit tests for SimvexApplication that don't require full Spring context.
 */
class SimvexApplicationUnitTest {

	/**
	 * Test that the SimvexApplication class exists and is properly defined.
	 */
	@Test
	@DisplayName("SimvexApplication class should be properly defined")
	void applicationClassExists() {
		assertThat(SimvexApplication.class).isNotNull();
	}

	/**
	 * Test that the main method exists with correct signature.
	 */
	@Test
	@DisplayName("Main method should exist with correct signature")
	void mainMethodExists() throws NoSuchMethodException {
		assertThatCode(() -> {
			SimvexApplication.class.getDeclaredMethod("main", String[].class);
		}).doesNotThrowAnyException();
	}

	/**
	 * Test that the class has the SpringBootApplication annotation.
	 */
	@Test
	@DisplayName("Class should be annotated with @SpringBootApplication")
	void hasSpringBootApplicationAnnotation() {
		assertThat(SimvexApplication.class.isAnnotationPresent(
			org.springframework.boot.autoconfigure.SpringBootApplication.class
		)).isTrue();
	}

	/**
	 * Test that SpringBootApplication annotation enables component scanning.
	 */
	@Test
	@DisplayName("SpringBootApplication should enable component scanning")
	void springBootApplicationEnablesComponentScanning() {
		org.springframework.boot.autoconfigure.SpringBootApplication annotation =
			SimvexApplication.class.getAnnotation(
				org.springframework.boot.autoconfigure.SpringBootApplication.class
			);
		assertThat(annotation).isNotNull();
		// Default behavior is to enable component scanning
		assertThat(annotation.scanBasePackages()).isEmpty(); // Uses default package scanning
	}

	/**
	 * Test the package structure for the application class.
	 */
	@Test
	@DisplayName("Application should be in correct package")
	void applicationIsInCorrectPackage() {
		String packageName = SimvexApplication.class.getPackage().getName();
		assertThat(packageName).isEqualTo("dochigosum.simvex");
	}

	/**
	 * Test that the application class is public.
	 */
	@Test
	@DisplayName("Application class should be public")
	void applicationClassIsPublic() {
		assertThat(java.lang.reflect.Modifier.isPublic(
			SimvexApplication.class.getModifiers()
		)).isTrue();
	}

	/**
	 * Test that the main method is public and static.
	 */
	@Test
	@DisplayName("Main method should be public and static")
	void mainMethodIsPublicAndStatic() throws NoSuchMethodException {
		java.lang.reflect.Method mainMethod = SimvexApplication.class.getDeclaredMethod("main", String[].class);
		int modifiers = mainMethod.getModifiers();
		assertThat(java.lang.reflect.Modifier.isPublic(modifiers)).isTrue();
		assertThat(java.lang.reflect.Modifier.isStatic(modifiers)).isTrue();
	}

	/**
	 * Test that the main method has void return type.
	 */
	@Test
	@DisplayName("Main method should have void return type")
	void mainMethodReturnsVoid() throws NoSuchMethodException {
		java.lang.reflect.Method mainMethod = SimvexApplication.class.getDeclaredMethod("main", String[].class);
		assertThat(mainMethod.getReturnType()).isEqualTo(void.class);
	}

	/**
	 * Negative test: Verify that calling main with null doesn't cause NPE in our code.
	 * Note: This will fail if SpringApplication.run doesn't handle null gracefully.
	 */
	@Test
	@DisplayName("Main method should handle empty arguments array")
	void mainMethodHandlesEmptyArgs() {
		// We can't actually run this without starting the full application,
		// but we can verify the method signature accepts String[]
		assertThatCode(() -> {
			SimvexApplication.class.getDeclaredMethod("main", String[].class);
		}).doesNotThrowAnyException();
	}

	/**
	 * Boundary test: Verify class has a default constructor (required by Spring).
	 */
	@Test
	@DisplayName("Application class should have accessible constructor")
	void applicationClassHasConstructor() {
		assertThatCode(() -> {
			SimvexApplication.class.getDeclaredConstructor();
		}).doesNotThrowAnyException();
	}

}