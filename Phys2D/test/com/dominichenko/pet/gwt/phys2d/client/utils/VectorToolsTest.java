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
package com.dominichenko.pet.gwt.phys2d.client.utils;

import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.*;
import static org.junit.Assert.*;

import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Matrix;
import gwt.g2d.client.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import com.dominichenko.pet.gwt.phys2d.client.math.Line;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableCircle;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;
import com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools;

/**
 * JUnit for testing class {@link VectorTools}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class VectorToolsTest {
	
	private Vector2 vector;
	private Matrix matrix;
	private static final double angle = 30.0 * Math.PI / 180.0;
	private static final Vector2 rotated = new Vector2(0.366, 1.366);

	@Before
	public void setUp() throws Exception {
		vector = new Vector2(P_ONE);
		matrix = new Matrix();
		matrix.mutableRotateCcw(angle);
	}

	@Test
	public void testPrintVector() {
		assertEquals("Vector2 {X=1.0, Y=1.0}", printVector(vector));
	}

	@Test
	public void testRoundVector() {
		assertEquals(rotated, roundVector(matrix.mutableTransform(vector), 1000));
	}

	@Test
	public void testMutableRoundVector() {
		assertEquals(rotated, mutableRoundVector(matrix.mutableTransform(vector), 1000));
	}
	
	@Test
	public void testRoundVectorNull() {
		assertNull(roundVector(null, 10));
		assertNull(mutableRoundVector(null, 10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRoundVectorIllegalPrecisionMinus2() {
		roundVector(P_ONE, -2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRoundVectorIllegalPrecision0() {
		roundVector(P_ONE, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRoundVectorIllegalPrecision1() {
		roundVector(P_ONE, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRoundVectorIllegalPrecision2() {
		roundVector(P_ONE, 2);
	}
	
	@Test
	public void testPrintCircle() {
		assertEquals("Circle {X=1.0, Y=1.0, R=1.0}", printCircle(new Circle(vector, 1)));
	}
	
	@Test
	public void testRoundCircle() {
		PrintableCircle circle = new PrintableCircle(matrix.mutableTransform(vector), P_ZERO.distance(P_ONE));
		assertEquals(new PrintableCircle(rotated, 1.414), roundCircle(circle, 1000));
	}

	@Test
	public void testMutableRoundCircle() {
		PrintableCircle circle = new PrintableCircle(matrix.mutableTransform(vector), P_ZERO.distance(P_ONE));
		assertEquals(new PrintableCircle(rotated, 1.414), mutableRoundCircle(circle, 1000));
	}
		
	@Test
	public void testDistanceToLine() {
		PrintableVector2 v1 = new PrintableVector2(1, 2);
		PrintableVector2 v2 = new PrintableVector2(5, 5);
		assertEquals(-2.5, distanceToLine(new PrintableVector2(4.5, 1.5), v1, v2), EPSILON);
		assertEquals(2.5, distanceToLine(new PrintableVector2(1.5, 5.5), v1, v2), EPSILON);
		assertEquals(2.5, distanceToLine(new PrintableVector2(4.5, 1.5), v2, v1), EPSILON);
		assertEquals(-2.5, distanceToLine(new PrintableVector2(1.5, 5.5), v2, v1), EPSILON);
		assertEquals(0, distanceToLine(new PrintableVector2(3, 3.5), v1, v2), EPSILON);
	}
	
	@Test
	public void testMirrorVector() {
		assertEquals(new PrintableVector2(5, 3), mirrorVector(new PrintableVector2(5, -3), P_UNIT_Y));
		assertEquals(new PrintableVector2(-5, -3), mirrorVector(new PrintableVector2(5, -3), P_UNIT_X));
		assertEquals(new PrintableVector2(-3, -3), mutableRoundVector(mirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(P_ONE).mutableNormalize()), 1000));
		assertEquals(new PrintableVector2(-3, -3), mutableRoundVector(mirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(P_ONE).mutableNegate().mutableNormalize()), 1000));
		assertEquals(new PrintableVector2(3, 3), mutableRoundVector(mirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(1, -1).mutableNormalize()), 1000));
	}

	@Test
	public void testMutableMirrorVector() {
		assertEquals(new PrintableVector2(5, 3), mutableMirrorVector(new PrintableVector2(5, -3), P_UNIT_Y));
		assertEquals(new PrintableVector2(-5, -3), mutableMirrorVector(new PrintableVector2(5, -3), P_UNIT_X));
		assertEquals(new PrintableVector2(-3, -3), mutableRoundVector(mutableMirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(P_ONE).mutableNormalize()), 1000));
		assertEquals(new PrintableVector2(-3, -3), mutableRoundVector(mutableMirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(P_ONE).mutableNegate().mutableNormalize()), 1000));
		assertEquals(new PrintableVector2(3, 3), mutableRoundVector(mutableMirrorVector(
				new PrintableVector2(3, 3), 
				new PrintableVector2(1, -1).mutableNormalize()), 1000));
	}
	
	@Test
	public void testRoundLine() {
		matrix.mutableTransform(vector);
		assertEquals(new Line(rotated, rotated.negate()), roundLine(new Line(vector, vector.negate()), 1000));
	}

	@Test
	public void testMutableRoundLine() {
		matrix.mutableTransform(vector);
		assertEquals(new Line(rotated, rotated.negate()), mutableRoundLine(new Line(vector, vector.negate()), 1000));
	}

	@Test
	public void testAreVectorsEqual() {
		assertTrue(areVectorsEqual(vector, new Vector2(vector)));
		assertTrue(areVectorsEqual(vector, vector.add(new Vector2(EPSILON*0.9))));
		assertFalse(areVectorsEqual(vector, vector.negate()));
		assertTrue(areVectorsEqual(null, null));
		assertFalse(areVectorsEqual(vector, null));
	}
	
	@Test
	public void testAreVectorsCollinear() {
		assertTrue(areVectorsCollinear(vector, new Vector2(vector)));
		assertTrue(areVectorsCollinear(vector, vector.add(new Vector2(EPSILON/0.9))));
		assertTrue(areVectorsCollinear(vector, vector.scale(10).mutableNegate()));
		assertFalse(areVectorsCollinear(vector, rotated));
		assertTrue(areVectorsCollinear(null, null));
		assertFalse(areVectorsCollinear(vector, null));
		assertTrue(areVectorsCollinear(new Vector2(), new Vector2()));
	}
	
	@Test
	public void testAreRaysOncomingTrue() {
		assertTrue(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(2, 2)),
				new Line(new PrintableVector2(10, 0), new PrintableVector2(8, 2))));
		assertTrue(areRaysOncoming(
				new Line(new PrintableVector2(1, 1), new PrintableVector2(1, 2)),
				new Line(new PrintableVector2(3, 3), new PrintableVector2(2, 3))));
		assertTrue(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(1, 0)),
				new Line(new PrintableVector2(3, 0), new PrintableVector2(2, 0))));
		assertTrue(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(2, 2)),
				new Line(new PrintableVector2(3, 3), new PrintableVector2(4, 4))));
		assertTrue(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(2, 2)),
				new Line(new PrintableVector2(10, 0), new PrintableVector2(8, -2))));
	}
	
	@Test
	public void testAreRaysOncomingFalse() {
		assertFalse(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(-2, 2)),
				new Line(new PrintableVector2(10, 0), new PrintableVector2(12, 2))));
		assertFalse(areRaysOncoming(
				new Line(new PrintableVector2(1, 1), new PrintableVector2(1, 2)),
				new Line(new PrintableVector2(3, 3), new PrintableVector2(4, 3))));
		assertFalse(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(2, 2)),
				new Line(new PrintableVector2(10, 0), new PrintableVector2(12, 2))));
		assertFalse(areRaysOncoming(
				new Line(new PrintableVector2(0, 0), new PrintableVector2(1, 1)),
				new Line(new PrintableVector2(2, 2), new PrintableVector2(4, 4))));
	}
}
