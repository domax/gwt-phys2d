/*
 * Copyright 2011-2012 Maxim Dominichenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dominichenko.pet.gwt.phys2d.client;

import com.dominichenko.pet.gwt.phys2d.client.collisions.*;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.*;
import com.dominichenko.pet.gwt.phys2d.client.math.*;
import com.dominichenko.pet.gwt.phys2d.client.utils.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UtilsTest.class,
	VectorToolsTest.class,
	ImmutableArrayListTest.class,
	EventedArrayListTest.class,
	PrintableVector2Test.class,
	PrintableCircleTest.class,
	LineTest.class,
	EventedVector2Test.class,
	PolygonTest.class,
	CircleCollisionDetectorTest.class,
})
public class AllTests {
}
