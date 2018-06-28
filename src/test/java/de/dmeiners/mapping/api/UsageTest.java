package de.dmeiners.mapping.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UsageTest {

	@Test
	public void simpleUsage() {

		// tag::simpleUsage[]
		PostProcessor processor = PostProcessorFactory.builder().build(); // <1>

		String someObject = "Hello"; // <2>

		Script script = processor.compileInline("target += ' World!'"); // <3>

		String result = script.execute(someObject, Collections.emptyMap()); // <4>

		assertThat(result).isEqualTo("Hello World!");
		// end::simpleUsage[]
	}

	@Test
	public void contextUsage() {
		// tag::contextUsage[]
		PostProcessor processor = PostProcessorFactory.builder().build();

		Map<String, Object> user = new HashMap<>(); // <1>
		user.put("firstName", "John");
		user.put("lastName", "Doe");

		Map<String, Object> context = new HashMap<>(); // <2>
		context.put("user", user);

		String someObject = "Hello";

		String scriptText = "target += ` ${user.firstName} ${user.lastName}`"; // <3>

		Script script = processor.compileInline(scriptText);

		String result = script.execute(someObject, context);

		assertThat(result).isEqualTo("Hello John Doe");
		// end::contextUsage[]
	}

	@Test
	public void exposeUtilityClass() {

		// tag::exposeUtilityClass[]
		PostProcessor processor = PostProcessorFactory.builder()
			.extension("StringUtils", StringUtils.class) // <1>
			.extension("tools", new Tools()) // <2>
			.build();

		Map<String, Object> user = new HashMap<>();
		user.put("firstName", "John");
		user.put("lastName", "Doe");

		Map<String, Object> context = new HashMap<>();
		context.put("user", user);

		String someObject = "Hello";

		String scriptText = "if (StringUtils.isNotBlank(user.firstName)) { target = 'First name is not blank and reversed ' + tools.reverse(user.firstName); }"; // <2>

		Script script = processor.compileInline(scriptText);

		String result = script.execute(someObject, context);

		assertThat(result).isEqualTo("First name is not blank and reversed nhoJ");
		// end::exposeUtilityClass[]
	}

	public static class Tools {

		public String reverse(String input) {

			return StringUtils.reverse(input);
		}
	}
}
