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
import com.google.gwt.event.shared.HandlerRegistration;

public class CollisionNotificationRegistration implements HandlerRegistration {

	private AbstractCollisionDetector source;
	private CollisionNotification<Sprite> handler;
	private Sprite registrant;
	
	public CollisionNotificationRegistration(
			AbstractCollisionDetector source, CollisionNotification<Sprite> handler, Sprite registrant) {
		this.source = source;
		this.handler = handler;
		this.registrant = registrant;
	}
	
	@Override
	public void removeHandler() {
		if (source != null && handler != null && registrant != null) {
			List<CollisionNotification<Sprite>> handlers = source.collisionNotificationMap.get(registrant);
			if (handlers != null) {
				handlers.remove(handler);
				if (handlers.isEmpty())
					source.collisionNotificationMap.remove(registrant);
			}
			handler = null;
		}
	}
}
