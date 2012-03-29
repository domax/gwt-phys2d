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

import static gwt.g2d.client.graphics.KnownColor.*;
import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Vector2;

import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;

public class BallSprite extends AbstractDemoSprite {
	
	private Color color;
	private String name;

	public BallSprite(DemoPhys2DGameScene gameScene, double radius) {
		this(gameScene, radius, GREEN_YELLOW, null);
	}

	public BallSprite(DemoPhys2DGameScene gameScene, double radius, Color color) {
		this(gameScene, radius, color, null);
	}
	
	public BallSprite(DemoPhys2DGameScene gameScene, double radius, Color color, String name) {
		super(gameScene);
		this.getCollidePolygon().addVertices(new Vector2(radius, 0), new Vector2(-radius, 0));
		this.color = color;
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if (color == null)
			color = TRANSPARENT;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void init(SpriteInitCallback callback) {
		setCollidable(true);
		setWeight(1);
		setElasticity(.7);
		super.init(callback);
	}

	@Override
	public void draw() {
		Circle c = this.getCollidePolygon().getBestFitCircle();
		getSurface().setFillStyle(color).fillShape(
				new ShapeBuilder().drawCircle(c.getCenter(), c.getRadius()).build());
	}
}
