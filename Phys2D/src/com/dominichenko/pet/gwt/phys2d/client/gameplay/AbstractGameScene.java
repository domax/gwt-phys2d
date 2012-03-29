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
package com.dominichenko.pet.gwt.phys2d.client.gameplay;

import gwt.g2d.client.graphics.DirectShapeRenderer;
import gwt.g2d.client.graphics.KnownColor;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.util.CountDown;
import gwt.g2d.client.util.FpsTimer;

import java.util.ArrayList;
import java.util.List;

import com.dominichenko.pet.gwt.phys2d.client.collisions.CollisionDetector;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Polygon;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Sprite;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;
import com.dominichenko.pet.gwt.phys2d.client.utils.Messenger;
import com.google.gwt.user.client.ui.Panel;

public abstract class AbstractGameScene extends FpsTimer implements GameScene {
	
	private Panel panel;
	private Surface surface;
	private DirectShapeRenderer shapeRenderer;
	private ArrayList<Sprite> sprites;
	private CollisionDetector collisionDetector;
	private Polygon bounds;

	public AbstractGameScene(Panel panel) {
		this.panel = panel;
		surface = new Surface(getWidth(), getHeight());
		shapeRenderer = new DirectShapeRenderer(surface);
		sprites = new ArrayList<Sprite>();
		bounds = new Polygon();
//		this.setDesiredFps(20);
	}

	@Override
	public int getWidth() {
		return panel.getOffsetWidth();
	}

	@Override
	public int getHeight() {
		return panel.getOffsetHeight();
	}
	
	public Surface getSurface() {
		return surface;
	}
	
	public DirectShapeRenderer getRenderer() {
		return shapeRenderer;
	}
	
	@Override
	public void start() {
		panel.clear();
		panel.add(surface);
		if (!sprites.isEmpty()) {
			final CountDown countdown = new CountDown(sprites.size());
			for (Sprite sprite : sprites) {
				sprite.init(new SpriteInitCallback() {

					@Override
					public void onComplete(Sprite sprite) {
						if (countdown.tick())
							AbstractGameScene.super.start();
					}

					@Override
					public void onError(Sprite sprite, Throwable error) {
						Messenger.say("Error", error.getMessage());
					}
				});
			}
		}
	}

	@Override
	public List<Sprite> getSprites() {
		return sprites;
	}

	@Override
	public List<Sprite> getColliders() {
		final ArrayList<Sprite> result = new ArrayList<Sprite>(); 
		for (Sprite sprite : sprites)
			if (sprite.isCollidable())
				result.add(sprite);
		return result;
	}
		
	@Override
	public Polygon getBounds() {
		return bounds;
	}

	@Override
	public CollisionDetector getCollisionDetector() {
		return collisionDetector;
	}

	public void setCollisionDetector(CollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
	}

	public void drawBackground() {
		surface.clear().fillBackground(KnownColor.BLACK);
	}
	
	@Override
	public void update() {
		if (collisionDetector != null)
			collisionDetector.testForCollisions(getColliders(), this);
		
		for (Sprite sprite : sprites)
			sprite.update();
		
		drawBackground();
		for (Sprite sprite : sprites)
			if (sprite.isVisible())
				sprite.draw();
	}
}
