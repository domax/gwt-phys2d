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
package com.dominichenko.pet.gwt.phys2d.demo.client;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.KnownColor;
import gwt.g2d.client.math.Vector2;

import java.util.Collection;
import java.util.List;

import com.dominichenko.pet.gwt.phys2d.client.collisions.CircleCollisionDetector;
import com.dominichenko.pet.gwt.phys2d.client.collisions.CollisionDetector;
import com.dominichenko.pet.gwt.phys2d.client.collisions.CollisionHandler;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.AbstractGameScene;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Sprite;
import com.google.gwt.user.client.ui.RootPanel;

public class DemoPhys2DGameScene extends AbstractGameScene implements CollisionHandler {
	
	private static final Vector2 gravity = new Vector2(0, 9.8);
	private static final Color bgColor = new Color(190, 190, 190);

	public DemoPhys2DGameScene() {
		super(RootPanel.get("canvasContainer"));
		setCollisionDetector(new CircleCollisionDetector());
		getCollisionDetector().addCollisionHandler(this);
		this.getBounds().addVertices(
				new Vector2(0, 0),
				new Vector2(getWidth(), 0),
				new Vector2(getWidth(), getHeight()),
				new Vector2(0, getHeight()));
		createBalls(20);
	}

	@Override
	public void onCollide(CollisionDetector collisionDetector, Sprite sprite1, Sprite sprite2) {
		collisionDetector.computeCollision(sprite1, sprite2);
	}

	@Override
	public void onBeyondWorld(CollisionDetector collisionDetector, List<Sprite> sprites) {
		for (Sprite sprite : sprites)
			collisionDetector.computeCollision(sprite, this);
	}
	
	@Override
	public void update() {
		//FIXME: define a rule - which sprites are affected by gravity
		for (Sprite sprite : getSprites())
			sprite.getVelocity().mutableAdd(gravity);
		super.update();
	}
	
	@Override
	public void drawBackground() {
		getSurface().clear().fillBackground(bgColor);
	}
	
	private void createBalls(int number) {
		Collection<KnownColor> colorCollection = KnownColor.getKnownColors();
		KnownColor[] colors = colorCollection.toArray(new KnownColor[colorCollection.size()]);
		for (int i = 0; i < number; i++) {
			double radius = 10.0 + Math.random() * 15.0;
			BallSprite sprite = new BallSprite(this, radius);
			sprite.setWeight((radius * radius) * Math.PI);
			sprite.getPosition().set(
					radius + Math.random() * (getWidth() - radius*2),
					radius + Math.random() * (getHeight() / 2 - radius*2));
			sprite.getVelocity().set(
					Math.random() * 200 - 100,
					-Math.random() * 100);
			sprite.setColor(colors[(int) Math.round((Math.random() * (colors.length - 1)))]);
		}
		getCollisionDetector().setAccuracy(number / 4);		
	}
}
