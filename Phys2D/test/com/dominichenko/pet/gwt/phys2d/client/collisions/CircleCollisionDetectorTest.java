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
package com.dominichenko.pet.gwt.phys2d.client.collisions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dominichenko.pet.gwt.phys2d.client.collisions.CircleCollisionDetector;
import com.dominichenko.pet.gwt.phys2d.client.collisions.CollisionDetector;
import com.dominichenko.pet.gwt.phys2d.client.collisions.CollisionHandler;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.GameScene;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.AbstractSprite;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Polygon;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Sprite;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;
import com.dominichenko.pet.gwt.phys2d.client.math.PrintableVector2;
import com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools;

/**
 * JUnit for testing class {@link CircleCollisionDetector}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class CircleCollisionDetectorTest {

	private CircleCollisionDetector circleCollisionDetector;
	private TestSprite sprite1, sprite2;
	private TestGameScene gameScene;
	private CollisionHandler collisionHandler = new CollisionHandler() {

		@Override
		public void onCollide(CollisionDetector collisionDetector, Sprite sprite1, Sprite sprite2) {
			circleCollisionDetector.computeCollision(sprite1, sprite2);
		}

		@Override
		public void onBeyondWorld(CollisionDetector collisionDetector, List<Sprite> sprites) {
			for (Sprite sprite : sprites)
				circleCollisionDetector.computeCollision(sprite, gameScene);
		}
	};

	public class TestSprite extends AbstractSprite {

		public TestSprite() {
			// Pivot point should be defined before vertices, because pivot position
			// setting forces vertices re-calculation
			getPosition().set(1, 1);
			getCollidePolygon().addVertices(
					new PrintableVector2(0, 1),
					new PrintableVector2(1, 2),
					new PrintableVector2(2, 1),
					new PrintableVector2(1, 0));
			setWeight(1);
			setCollidable(true);
		}

		@Override
		public void init(SpriteInitCallback callback) {
		}

		@Override
		public void update() {
		}

		@Override
		public void draw() {
		}
	}

	public class TestGameScene implements GameScene {

		private Polygon bounds;
		private List<Sprite> sprites;

		public TestGameScene() {
			bounds = new Polygon(
					new PrintableVector2(0, 0),
					new PrintableVector2(10, 0),
					new PrintableVector2(10, 10),
					new PrintableVector2(0, 10));
			sprites = Arrays.asList(new Sprite[] {sprite1, sprite2});
		}

		@Override
		public int getWidth() {
			return 10;
		}

		@Override
		public int getHeight() {
			return 10;
		}

		@Override
		public Polygon getBounds() {
			return bounds;
		}

		@Override
		public List<Sprite> getSprites() {
			return sprites;
		}

		@Override
		public List<Sprite> getColliders() {
			return sprites;
		}

		@Override
		public CollisionDetector getCollisionDetector() {
			return circleCollisionDetector;
		}
	}

	@Before
	public void setUp() throws Exception {
		circleCollisionDetector = new CircleCollisionDetector();
		sprite1 = new TestSprite();
		sprite2 = new TestSprite();
		gameScene = new TestGameScene();
	}

	@Test
	public void testTestForCollisionsSpriteSprite() {
		sprite1.getVelocity().set(10, 0);
		sprite2.getPosition().setX(3);
		sprite2.getVelocity().set(-10, 0);
		assertTrue(circleCollisionDetector.testForCollisions(sprite1, sprite2));

		sprite2.getPosition().setX(4);
		assertFalse(circleCollisionDetector.testForCollisions(sprite1, sprite2));

		sprite2.getPosition().setX(2);
		assertTrue(circleCollisionDetector.testForCollisions(sprite1, sprite2));
	}

	@Test
	public void testTestForCollisionsSpriteGameScene() {
		sprite2.getPosition().set(3, 3);
		assertTrue(circleCollisionDetector.testForCollisions(sprite1, gameScene));
		assertFalse(circleCollisionDetector.testForCollisions(sprite2, gameScene));
		sprite2.getPosition().set(3, 9.5);
		assertTrue(circleCollisionDetector.testForCollisions(sprite2, gameScene));
		sprite2.getPosition().set(3, 15);
		assertTrue(circleCollisionDetector.testForCollisions(sprite2, gameScene));
	}

	@Test
	public void testComputeCollisionSpriteSprite1() {
		sprite1.getVelocity().set(5, 5);
		sprite2.getPosition().setX(3);
		sprite2.getVelocity().set(-5, 5);
		circleCollisionDetector.computeCollision(sprite1, sprite2);
		// System.out.println("sprite1: " + sprite1);
		// System.out.println("sprite2: " + sprite2);
		assertEquals(new PrintableVector2(-5, 5),
				new PrintableVector2(sprite1.getVelocity()));
		assertEquals(new PrintableVector2(5, 5),
				new PrintableVector2(sprite2.getVelocity()));
	}

	@Test
	public void testComputeCollisionSpriteSprite2() {
		sprite1.setElasticity(0);
		sprite2.setElasticity(0);
		sprite1.getVelocity().set(5, 5);
		sprite2.getPosition().setX(3);
		sprite2.getVelocity().set(-5, 5);
		circleCollisionDetector.computeCollision(sprite1, sprite2);
		// System.out.println("sprite1: " + sprite1);
		// System.out.println("sprite2: " + sprite2);
		assertEquals(new PrintableVector2(0, 5),
				new PrintableVector2(sprite1.getVelocity()));
		assertEquals(new PrintableVector2(0, 5),
				new PrintableVector2(sprite2.getVelocity()));
	}

	@Test
	public void testComputeCollisionSpriteSprite3() {
		sprite1.setElasticity(0.8);
		sprite2.setElasticity(0.8);
		sprite2.setWeight(2);
		sprite1.getVelocity().set(5, 5);
		sprite2.getPosition().setX(3);
		sprite2.getVelocity().set(-5, 5);
		circleCollisionDetector.computeCollision(sprite1, sprite2);
		// System.out.println("sprite1: " + sprite1);
		// System.out.println("sprite2: " + sprite2);
		assertEquals(
				new PrintableVector2(-7, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(1, 5),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}

	@Test
	public void testComputeCollisionSpriteSprite4() {
		sprite2.setWeight(2);
		sprite2.getPosition().setX(2);
		circleCollisionDetector.computeCollision(sprite1, sprite2);
		assertEquals(
				new PrintableVector2(-1, 0),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(0.5, 0),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}

	@Test
	public void testComputeCollisionSpriteGameScene() {
		sprite1.getPosition().setY(5);
		sprite1.getVelocity().set(-5, 5);
		assertTrue(circleCollisionDetector.testForCollisions(sprite1, gameScene));
		circleCollisionDetector.computeCollision(sprite1, gameScene);
		assertEquals(
				new PrintableVector2(5, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		
		sprite2.getVelocity().set(-5, -5);
		assertTrue(circleCollisionDetector.testForCollisions(sprite2, gameScene));
		circleCollisionDetector.computeCollision(sprite2, gameScene);
		assertEquals(
				new PrintableVector2(5, 5),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}
	
	@Test
	public void testTestForCollisionsList_SpriteGameScene1() {
		sprite1.getVelocity().set(5, -5);
		sprite2.getPosition().setX(3);
		circleCollisionDetector.addCollisionHandler(collisionHandler);
		circleCollisionDetector.testForCollisions(gameScene.getColliders(), gameScene);
		assertEquals(
				new PrintableVector2(0, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(5, 0),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}

	@Test
	public void testTestForCollisionsList_SpriteGameScene2() {
		sprite1.getVelocity().set(-5, -5);
		sprite2.getPosition().setX(4);
		sprite2.getVelocity().set(5, 5);
		circleCollisionDetector.addCollisionHandler(collisionHandler);
		circleCollisionDetector.testForCollisions(gameScene.getColliders(), gameScene);
		assertEquals(
				new PrintableVector2(5, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(5, 5),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}
	
	@Test
	public void testTestForCollisionsList_SpriteGameScene3() {
		sprite1.getVelocity().set(0, 5);
		sprite1.getPosition().set(2, 2);
		sprite2.getVelocity().set(0, -5);
		sprite2.getPosition().set(4, 2);
		circleCollisionDetector.addCollisionHandler(collisionHandler);
		circleCollisionDetector.testForCollisions(gameScene.getColliders(), gameScene);
		assertEquals(
				new PrintableVector2(0, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(0, -5),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
		
		sprite1.getVelocity().set(5, 5);
		sprite2.getVelocity().set(0, -5);
		circleCollisionDetector.testForCollisions(gameScene.getColliders(), gameScene);
		assertEquals(
				new PrintableVector2(0, 5),
				new PrintableVector2(VectorTools.roundVector(sprite1.getVelocity(), 100)));
		assertEquals(
				new PrintableVector2(5, -5),
				new PrintableVector2(VectorTools.roundVector(sprite2.getVelocity(), 100)));
	}
}
