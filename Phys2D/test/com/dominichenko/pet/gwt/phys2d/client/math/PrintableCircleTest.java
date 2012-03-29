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

import com.dominichenko.pet.gwt.phys2d.client.math.PrintableCircle;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;

import gwt.g2d.client.math.Circle;

import org.junit.Test;

import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.*;
import static org.junit.Assert.*;

/**
 * JUnit for testing class {@link PrintableCircle}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PrintableCircleTest {

	@Test
	public void testPrintableCircleVector2Vector2() {
		assertEquals(new PrintableCircle(0.5, 0.5, 0.707),
				mutableRoundCircle(new PrintableCircle(P_ZERO, P_ONE), 1000));
		assertEquals(new PrintableCircle(0, 0, 1.414), 
				mutableRoundCircle(new PrintableCircle(P_ONE, P_ONE.negate()), 1000));
		assertEquals(new PrintableCircle(-0.5, 0.5, 2.121),
				mutableRoundCircle(new PrintableCircle(new PrintableVector2(1, -1), new PrintableVector2(-2, 2)), 1000));
	}

	@Test
	public void testPrintableCircleVector2Vector2Vector2() {
		assertEquals(new PrintableCircle(1, 1, 1.414),
				mutableRoundCircle(new PrintableCircle(
						new PrintableVector2(0, 0), 
						new PrintableVector2(0, 2),
						new PrintableVector2(2, 2)), 1000));
		assertEquals(new PrintableCircle(8.605, -1.763, 8.784),
				mutableRoundCircle(new PrintableCircle(
						new PrintableVector2(0, 0), 
						new PrintableVector2(3, 5),
						new PrintableVector2(8, 7)), 1000));
	}

	@Test
	public void testValueOf() {
		PrintableCircle circle1 = new PrintableCircle(P_ONE, 1);
		Circle circle2 = new Circle(P_ONE, 1);
		assertTrue(circle1 == PrintableCircle.valueOf(circle1));
		assertFalse(circle1 == PrintableCircle.valueOf(circle2));
		assertEquals(circle1, circle2);
	}

	@Test
	public void testToString() {
		assertEquals("PrintableCircle {X=1.0, Y=1.0, R=1.414}", new PrintableCircle(1, 1, 1.414).toString());
	}

	@Test
	public void testEqualsObject() {
		PrintableCircle circle1 = new PrintableCircle(P_ONE, 1);
		Object circle2 = new Circle(P_ONE, 1);
		assertTrue(circle1.equals(circle2));
		assertFalse(circle1.equals((Object) null));
		assertFalse(circle1.equals(P_ONE));
	}

	@Test
	public void testEqualsCircle() {
		PrintableCircle circle1 = new PrintableCircle(P_ONE, 1);
		Circle circle2 = new Circle(P_ONE, 1);
		assertTrue(circle1.equals(circle2));
		assertFalse(circle1.equals((Circle) null));
	}
}
