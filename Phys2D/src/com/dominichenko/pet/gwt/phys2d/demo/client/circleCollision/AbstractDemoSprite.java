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
package com.dominichenko.pet.gwt.phys2d.demo.client.circleCollision;

import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.EPSILON;
import gwt.g2d.client.graphics.Surface;

import com.dominichenko.pet.gwt.phys2d.client.gameplay.AbstractGameScene;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.AbstractSprite;
import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;

public abstract class AbstractDemoSprite extends AbstractSprite {

	public AbstractDemoSprite(CIrcleCollisionGameScene gameScene) {
		super(gameScene);
	}

	protected Surface getSurface() {
		return ((AbstractGameScene) getGameScene()).getSurface();
	}

	protected CIrcleCollisionGameScene getScene() {
		return (CIrcleCollisionGameScene) getGameScene();
	}
	
	@Override
	public void init(SpriteInitCallback callback) {
		callback.onComplete(this);
	}

	@Override
	public void update() {
		float fps = getScene().getFps();
		if (fps < EPSILON)
			fps = getScene().getDesiredFps();
		getPosition().mutableAdd(getVelocity().scale(1d / fps));
	}
}
