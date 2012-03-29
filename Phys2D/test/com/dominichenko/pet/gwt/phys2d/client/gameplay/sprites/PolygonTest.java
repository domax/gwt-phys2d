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
package com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites;

import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Polygon;
import com.dominichenko.pet.gwt.phys2d.client.math.Line;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableCircle;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;
import com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools;

import gwt.g2d.client.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.*;
import static gwt.g2d.client.math.Vector2.*;
import static org.junit.Assert.*;

/**
 * JUnit for testing class {@link Polygon}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PolygonTest {
	
	private Polygon polygon;
	private static final Vector2 v10 = new Vector2(10);
	private Vector2 vertex1, vertex2, vertex3, vertex4;
	private Line edge1, edge2, edge3, edge4;
	private Vector2 normal1, normal2, normal3, normal4;
	
	@Before
	public void setUp() throws Exception {
		vertex1 = new PrintableVector2(ZERO).mutableMultiply(v10);
		vertex2 = new PrintableVector2(UNIT_Y).mutableMultiply(v10);
		vertex3 = new PrintableVector2(ONE).mutableMultiply(v10);
		vertex4 = new PrintableVector2(UNIT_X).mutableMultiply(v10);
		
		edge1 = new Line(new PrintableVector2(vertex1), new PrintableVector2(vertex2));
		edge2 = new Line(edge1.getVertex2(), new PrintableVector2(vertex3));
		edge3 = new Line(edge2.getVertex2(), new PrintableVector2(vertex4));
		edge4 = new Line(edge3.getVertex2(), edge1.getVertex1());

		normal1 = new PrintableVector2(UNIT_X).mutableNegate();
		normal2 = new PrintableVector2(UNIT_Y);
		normal3 = new PrintableVector2(UNIT_X);
		normal4 = new PrintableVector2(UNIT_Y).mutableNegate();
		
		polygon = new Polygon(vertex1, vertex2, vertex3, vertex4);
	}

	@Test
	public void testGetVertexes() {
		assertEquals(vertex1, polygon.getVertices().get(0));
		assertEquals(vertex2, polygon.getVertices().get(1));
		assertEquals(vertex3, polygon.getVertices().get(2));
		assertEquals(vertex4, polygon.getVertices().get(3));
	}

	@Test
	public void testGetEdges() {
		assertEquals(edge1, polygon.getEdges().get(0));
		assertEquals(edge2, polygon.getEdges().get(1));
		assertEquals(edge3, polygon.getEdges().get(2));
		assertEquals(edge4, polygon.getEdges().get(3));
	}

	@Test
	public void testGetNormals() {
		assertEquals(normal1, VectorTools.mutableRoundVector(polygon.getEdges().get(0).getNormal(), 1000));
		assertEquals(normal2, VectorTools.mutableRoundVector(polygon.getEdges().get(1).getNormal(), 1000));
		assertEquals(normal3, VectorTools.mutableRoundVector(polygon.getEdges().get(2).getNormal(), 1000));
		assertEquals(normal4, VectorTools.mutableRoundVector(polygon.getEdges().get(3).getNormal(), 1000));
	}

	@Test
	public void testIsCollidable() {
		assertFalse(new Polygon().isCollidable());
		assertFalse(new Polygon(P_ZERO).isCollidable());
		assertTrue(new Polygon(P_ZERO, P_ONE).isCollidable());
		assertTrue(polygon.isCollidable());
	}

	@Test
	public void testRefresh() {
		// this method calls refresh() implicitly;
		polygon.getVertices().remove(3);
		
		assertEquals(edge1, polygon.getEdges().get(0));
		assertEquals(edge2, polygon.getEdges().get(1));
		assertEquals(new Line(vertex3, vertex1), polygon.getEdges().get(2));
		assertEquals(normal1, VectorTools.mutableRoundVector(polygon.getEdges().get(0).getNormal(), 1000));
		assertEquals(normal2, VectorTools.mutableRoundVector(polygon.getEdges().get(1).getNormal(), 1000));
		assertEquals(new PrintableVector2(0.707, -0.707), 
				VectorTools.mutableRoundVector(polygon.getEdges().get(2).getNormal(), 1000));
	}
	
	@Test
	public void testGetBestFitCircle() {
		polygon = new Polygon(
				new PrintableVector2(0, 0),
				new PrintableVector2(0, 5),
				new PrintableVector2(5, 5),
				new PrintableVector2(5, 0));
		assertEquals(new PrintableCircle(2.5, 2.5, 3.536), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
		polygon = new Polygon(
				new PrintableVector2(0, 3),
				new PrintableVector2(3, 7),
				new PrintableVector2(7, 4),
				new PrintableVector2(4, 0));
		assertEquals(new PrintableCircle(3.5, 3.5, 3.536), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
		polygon = new Polygon(
				new PrintableVector2(0, 4),
				new PrintableVector2(1, 6),
				new PrintableVector2(3, 7),
				new PrintableVector2(4, 7),
				new PrintableVector2(6, 6),
				new PrintableVector2(7, 4));
		assertEquals(new PrintableCircle(3.5, 3.5, 3.536), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
		polygon = new Polygon(
				new PrintableVector2(0, 5),
				new PrintableVector2(3, 10),
				new PrintableVector2(8, 10),
				new PrintableVector2(11, 5),
				new PrintableVector2(8, 0),
				new PrintableVector2(3, 0));
		assertEquals(new PrintableCircle(5.5, 5, 5.59), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
		polygon = new Polygon(
				new PrintableVector2(0, 2),
				new PrintableVector2(0, 4),
				new PrintableVector2(2, 6),
				new PrintableVector2(4, 6),
				new PrintableVector2(6, 4),
				new PrintableVector2(6, 2),
				new PrintableVector2(4, 0),
				new PrintableVector2(2, 0));
		assertEquals(new PrintableCircle(3, 3, 3.162), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
		polygon = new Polygon(
				new PrintableVector2(1, 2),
				new PrintableVector2(2, 5),
				new PrintableVector2(4, 8),
				new PrintableVector2(9, 9),
				new PrintableVector2(11, 7),
				new PrintableVector2(12, 4),
				new PrintableVector2(9, 3),
				new PrintableVector2(5, 2));
		assertEquals(new PrintableCircle(6.357, 3.786, 5.846), mutableRoundCircle(polygon.getBestFitCircle(), 1000));
	}
	
	@Test
	public void testMoveBy() {
		PrintableVector2 offset = new PrintableVector2(10, 10); 
		polygon.moveBy(offset);
		edge1.getVertex1().mutableAdd(offset);
		edge1.getVertex2().mutableAdd(offset);
		edge2.getVertex2().mutableAdd(offset);
		edge3.getVertex2().mutableAdd(offset);
		testGetVertexes();
		testGetEdges();
		testGetNormals();
	}
}
