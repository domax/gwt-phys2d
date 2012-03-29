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

import java.util.List;

import com.dominichenko.pet.gwt.phys2d.client.gameplay.sprites.Sprite;
import com.dominichenko.pet.gwt.phys2d.client.math.Line;

/**
 * Interface that represents notification handler of collisions by 
 * registered {@link Sprite} object with other sprites and with world boundaries.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 * @param <T> Specifies type of registered sprite. Should extend {@link Sprite}.
 */
public interface CollisionNotification<T extends Sprite> {

	/**
	 * Should handle event when registered sprite collides with other one.
	 * 
	 * @param collisionDetector A collision detector that was used.
	 * @param sprite A registered sprite that receives this notification.
	 * @param spriteWith A sprite that registered sprite collided with.
	 */
	void onCollide(CollisionDetector collisionDetector, T sprite, Sprite spriteWith);
	
	/**
	 * Should handle event when registered sprite goes out to world boundaries.
	 * 
	 * @param collisionDetector A collision detector that was used.
	 * @param sprite A registered sprite that receives this notification.
	 * @param edges A list of edges that registered sprite intersected.
	 */
	void onBeyondWorld(CollisionDetector collisionDetector, T sprite, List<Line> edges);
}
