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
package com.dominichenko.pet.gwt.phys2d.client.math;

import org.junit.Before;
import org.junit.Test;

import com.dominichenko.pet.gwt.phys2d.client.math.Line;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;

import static org.junit.Assert.*;
import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.*;

/**
 * JUnit for testing class {@link Line}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class LineTest {
	
	private Line line;
	private PrintableVector2 v1, v2, v, n;

	@Before
	public void setUp() throws Exception {
		v1 = new PrintableVector2(1, 2);
		v2 = new PrintableVector2(5, 5);
		v = new PrintableVector2(4, 3);
		n = new PrintableVector2(-0.6, 0.8);
		line = new Line(new PrintableVector2(v1), new PrintableVector2(v2));
	}

	@Test
	public void testLine() {
		Line l = new Line();
		assertEquals(P_ZERO, l.getVertex1());
		assertEquals(P_ZERO, l.getVertex2());
		assertEquals(P_ZERO, l.getVector());
		assertEquals(P_ZERO, l.getNormal());
		assertEquals(0, l.getLength(), EPSILON);
	}

	@Test
	public void testLineline() {
		Line l = new Line(line);
		assertEquals(v1, l.getVertex1());
		assertEquals(v2, l.getVertex2());
		assertEquals(v, l.getVector());
		assertEquals(n, mutableRoundVector(l.getNormal(), 1000));
		assertEquals(5, l.getLength(), EPSILON);
		assertFalse(l.getVertex1() == line.getVertex1());
		assertFalse(l.getVertex2() == line.getVertex2());
	}

	@Test
	public void testLineVector2Vector2() {
		assertEquals(v1, line.getVertex1());
		assertEquals(v2, line.getVertex2());
		assertEquals(v, line.getVector());
		assertEquals(n, mutableRoundVector(line.getNormal(), 1000));
		assertEquals(5, line.getLength(), EPSILON);
	}

	@Test
	public void testUpdate() {
		line.getVertex1().set(v2.getX(), v2.getY());
		line.getVertex2().set(v1.getX(), v1.getY());
		line.update();
		assertEquals(v.mutableNegate(), line.getVector());
		assertEquals(n.mutableNegate(), mutableRoundVector(line.getNormal(), 1000));
		assertEquals(5, line.getLength(), EPSILON);
		line = new Line(new PrintableVector2(v2), new PrintableVector2(v1));
		line.getVertex1().set(v1.getX(), v1.getY());
		line.update();
		assertEquals(P_ZERO, line.getVector());
		assertEquals(P_ZERO, line.getNormal());
		assertEquals(0, line.getLength(), EPSILON);
	}

	@Test
	public void testToString() {
		assertEquals("Line {" +
				"vertex1=PrintableVector2 {X=1.0, Y=2.0}, " +
				"vertex2=PrintableVector2 {X=5.0, Y=5.0}, " +
				"vector=PrintableVector2 {X=4.0, Y=3.0}, " +
				"normal=PrintableVector2 {X=-0.6, Y=0.8}, " +
				"length=5.0}", mutableRoundLine(line, 1000).toString());
	}

	@Test
	public void testEqualsObject() {
		assertTrue(line.equals(line));
		assertFalse(line.equals(null));
		assertFalse(line.equals("test"));
		assertFalse(line.equals(new Line()));
		assertTrue(line.equals(new Line(line)));
	}

	@Test
	public void testCompareTo() {
		assertTrue(line.compareTo(null) > 0);
		assertTrue(line.compareTo(new Line()) > 0);
		assertTrue(line.compareTo(new Line(line)) == 0);
	}
	
	@Test
	public void testGetDistance() {
		assertEquals(-2.5, line.getDistance(new PrintableVector2(4.5, 1.5)), EPSILON);
		assertEquals(0, line.getDistance(new PrintableVector2(3, 3.5)), EPSILON);
	}
}
