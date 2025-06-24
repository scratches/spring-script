
/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.script.GenericApplication;
import org.springframework.util.ClassUtils;

public class SpringScript {

	public static SpringApplicationBuilder from(Class<?>... classes) {
		return new SpringApplicationBuilder(extract(classes, 2));
	}

	public static ConfigurableApplicationContext run(String... args) {
		return new SpringApplicationBuilder(extract(new Class[0], 2)).run(args);
	}

	private static Class<?>[] extract(Class<?>[] classes, int stackLevel) {
		Set<Class<?>> classList = new LinkedHashSet<>(List.of(classes));
		classList.add(GenericApplication.class);
		Class<?> caller = ClassUtils.resolveClassName(new RuntimeException().getStackTrace()[stackLevel].getClassName(),
				null);
		if (caller != null) {
			for (Class<?> c : caller.getDeclaredClasses()) {
				classList.add(c);
			}
			classList.add(caller);
		}
		return classList.toArray(new Class[0]);
	}
}
