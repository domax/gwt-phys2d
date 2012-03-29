/*
 * Copyright 2011 Maxim Dominichenko
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

import com.dominichenko.pet.gwt.phys2d.client.math.EventedVector2;
import com.dominichenko.pet.gwt.phys2d.client.math.Vector2Handler;
import com.dominichenko.pet.gwt.phys2d.client.math.Vector2HandlerRegistration;
import com.google.gwt.event.shared.HandlerRegistration;

import gwt.g2d.client.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * JUnit for testing classes and interfaces:
 * <ul>
 * <li>{@link EventedVector2};</li>
 * <li>{@link Vector2Handler};</li>
 * <li>{@link Vector2HandlerRegistration};</li>
 * </ul>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class EventedVector2Test {
	
	private EventedVector2 vectorTest;
	private Vector2Handler handlerMock;
	private HandlerRegistration handlerRegistration;

	@Before
	public void setUp() throws Exception {
		vectorTest = new EventedVector2(1, 10);
		handlerMock = createMock(Vector2Handler.class);
		handlerRegistration = vectorTest.addEventHandler(handlerMock);
	}

	@Test
	public void testSetXYChanged() {
		handlerMock.onXChanged(vectorTest, 1, -1);
		handlerMock.onYChanged(vectorTest, 10, -10);
		replay(handlerMock);
		vectorTest.mutableNegate();
		verify(handlerMock);
		handlerRegistration.removeHandler();
		assertTrue(vectorTest.vector2Handlers.isEmpty());
	}

	@Test
	public void testSetXYUnchanged() {
		replay(handlerMock);
		vectorTest.mutableAdd(Vector2.ZERO);
		verify(handlerMock);
		handlerRegistration.removeHandler();
		assertTrue(vectorTest.vector2Handlers.isEmpty());
	}
}
