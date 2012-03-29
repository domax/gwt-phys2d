/*
 * Copyright 2011 Maxim Dominichenko
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
package com.dominichenko.pet.gwt.phys2d.client.math;

import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;

import gwt.g2d.client.math.Vector2;
import org.junit.Test;

import static gwt.g2d.client.math.Vector2.*;
import static org.junit.Assert.*;

/**
 * JUnit for testing class {@link PrintableVector2}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PrintableVector2Test {

	@Test
	public void testValueOf() {
		PrintableVector2 vector1 = new PrintableVector2(ONE);
		Vector2 vector2 = new Vector2(ONE);
		assertTrue(vector1 == PrintableVector2.valueOf(vector1));
		assertFalse(vector1 == PrintableVector2.valueOf(vector2));
		assertEquals(vector1, vector2);
	}
	
	@Test
	public void testToString() {
		assertEquals("PrintableVector2 {X=1.0, Y=1.0}", 
				new PrintableVector2(Vector2.ONE.getX(), Vector2.ONE.getY()).toString());
	}
}
