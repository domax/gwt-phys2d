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

import static com.dominichenko.pet.gwt.phys2d.client.utils.Utils.getSimpleName;
import static com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools.*;
import gwt.g2d.client.math.Vector2;

import com.dominichenko.pet.gwt.phys2d.client.gameplay.GameScene;
import com.dominichenko.pet.gwt.phys2d.client.math.EventedVector2;
import com.dominichenko.pet.gwt.phys2d.client.math.Vector2Handler;
import com.dominichenko.pet.gwt.phys2d.client.utils.VectorTools;

public abstract class AbstractSprite implements Sprite {

	private GameScene gameScene;
	private EventedVector2 position;
	private Vector2 velocity;
	private Polygon collidePolygon;
	private double weight = 0d;
	private double elasticity = 1d;
	private boolean visible = true;
	private boolean collidable = false;
	
	private class SpritePositionVector2Handler implements Vector2Handler {

		@Override
		public void onXChanged(Vector2 source, double oldX, double newX) {
			collidePolygon.moveBy(newX - oldX, 0);
		}

		@Override
		public void onYChanged(Vector2 source, double oldY, double newY) {
			collidePolygon.moveBy(0, newY - oldY);
		}
	}
	
	public AbstractSprite() {
		position = new EventedVector2();
		position.addEventHandler(new SpritePositionVector2Handler());
		velocity = new Vector2();
		collidePolygon = new Polygon();
	}

	public AbstractSprite(GameScene gameScene) {
		this();
		this.gameScene = gameScene;
		if (this.gameScene != null)
			this.gameScene.getSprites().add(this);
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getVelocity() {
		return velocity;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getElasticity() {
		return elasticity;
	}

	public void setElasticity(double elasticity) {
		if (elasticity < 0.0) elasticity = 0.0;
		else if (elasticity > 1.0) elasticity = 1.0;
		this.elasticity = elasticity;
	}

	public Polygon getCollidePolygon() {
		return collidePolygon;
	}

	@Override
	public boolean isCollidable() {
		return collidable && collidePolygon.isCollidable() && Math.abs(weight) > VectorTools.EPSILON;
	}
	
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getSimpleName(getClass()));	
		builder.append(" {gameScene=");
		builder.append(gameScene);
		builder.append(", position=");
		builder.append(printVector(position));
		builder.append(", velocity=");
		builder.append(printVector(velocity));
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", elasticity=");
		builder.append(elasticity);
		builder.append(", visible=");
		builder.append(visible);
		builder.append(", collidable=");
		builder.append(collidable);
		builder.append(", collidePolygon=");
		builder.append(collidePolygon);
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (collidable ? 1231 : 1237);
		result = prime * result
				+ ((collidePolygon == null) ? 0 : collidePolygon.hashCode());
		long temp;
		temp = new Double(elasticity * 1000).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		temp = new Double(weight * 1000).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSprite other = (AbstractSprite) obj;
		if (collidable != other.collidable)
			return false;
		if (collidePolygon == null) {
			if (other.collidePolygon != null)
				return false;
		} else if (!collidePolygon.equals(other.collidePolygon))
			return false;
		if (Math.abs(elasticity - other.elasticity) > EPSILON)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (velocity == null) {
			if (other.velocity != null)
				return false;
		} else if (!velocity.equals(other.velocity))
			return false;
		if (visible != other.visible)
			return false;
		if (Math.abs(weight - other.weight) > EPSILON)
			return false;
		return true;
	}
}
