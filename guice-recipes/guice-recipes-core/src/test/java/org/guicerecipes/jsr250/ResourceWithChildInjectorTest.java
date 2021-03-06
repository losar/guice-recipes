/**
 * Copyright (C) 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.guicerecipes.jsr250;

import javax.annotation.*;

import junit.framework.*;

import org.guicerecipes.support.*;

import com.google.inject.*;

/** @author james.strachan@gmail.com (James Strachan) */
public class ResourceWithChildInjectorTest extends TestCase {

	public void testResourceInjection() throws CreationException, CloseFailedException {
		Injector injector = Guice.createInjector(new Jsr250Module() {
		});

		Injector childInjector = injector.createChildInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(MyInterface.class).to(MyBean.class);
			}
		});

/* TODO
		MyBean bean = (MyBean) childInjector.getInstance(MyInterface.class);
		assertNotNull("Should have instantiated the bean", bean);
		assertNotNull("Should have injected a foo", bean.foo);
		assertNotNull("Should have injected a bar", bean.bar);

		assertEquals("Should have injected correct foo", "Foo", bean.foo.name);
		assertEquals("Should have injected correct bar", "XYZ", bean.bar.name);
*/	}

	public static interface MyInterface {

	}

	public static class MyBean implements MyInterface {
		@Resource
		public AnotherBean foo;

		public AnotherBean bar;

		@Resource(name = "xyz")
		public void bar(AnotherBean bar) {
			this.bar = bar;
		}
	}

	static class AnotherBean {
		public String name = "undefined";

		AnotherBean(String name) {
			this.name = name;
		}
	}
}
