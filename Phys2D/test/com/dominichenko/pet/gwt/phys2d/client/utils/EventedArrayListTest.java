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
package com.dominichenko.pet.gwt.phys2d.client.utils;

import static com.dominichenko.pet.gwt.phys2d.client.utils.ListHandler.Type.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import com.dominichenko.pet.gwt.phys2d.client.utils.EventedArrayList;
import com.dominichenko.pet.gwt.phys2d.client.utils.ListHandler;
import com.dominichenko.pet.gwt.phys2d.client.utils.ListHandlerRegistration;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit for testing classes and interfaces:
 * <ul>
 * <li>{@link EventedArrayList};</li>
 * <li>{@link ListHandler};</li>
 * <li>{@link ListHandlerRegistration};</li>
 * </ul>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class EventedArrayListTest {
	
	private EventedArrayList<String> eventedList;
	private ArrayList<String> defaultList;
	private ListHandler<String> listHandlerMock;
	private HandlerRegistration handlerRegistration;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		defaultList = new ArrayList<String>(Arrays.asList(new String[]{"Item1", "Item2", "Item3"}));
		eventedList = new EventedArrayList<String>(defaultList);
		listHandlerMock = createMock(ListHandler.class);
		handlerRegistration = eventedList.addEventHandler(listHandlerMock);
	}

	@Test
	public void testClearPositive() {
		expect(listHandlerMock.onModifying(eventedList, REMOVING, eventedList)).andReturn(true);
		replay(listHandlerMock);
		eventedList.clear();
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		assertTrue(eventedList.isEmpty());
	}
	
	@Test
	public void testClearNegative() {
		HandlerRegistration hr = eventedList.addEventHandler(listHandlerMock);
		expect(listHandlerMock.onModifying(eventedList, REMOVING, eventedList)).andReturn(false);
		replay(listHandlerMock);
		eventedList.clear();
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		hr.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		assertEquals(defaultList.size(), eventedList.size());
	}

	@Test
	public void testRemoveRange() {
		expect(listHandlerMock.onModifying(eventedList, REMOVING, eventedList.subList(0, 2))).andReturn(true);
		replay(listHandlerMock);
		eventedList.removeRange(0, 2);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		assertEquals(defaultList.subList(2, 3), eventedList);
	}

	@Test
	public void testAddE() {
		expect(listHandlerMock.onModifying(eventedList, ADDING, Arrays.asList(new String[]{"Item4"}))).andReturn(true);
		replay(listHandlerMock);
		eventedList.add("Item4");
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.add("Item4");
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testAddIntE() {
		String e = "Item4";
		expect(listHandlerMock.onModifying(eventedList, ADDING, Arrays.asList(new String[]{e}))).andReturn(true);
		replay(listHandlerMock);
		eventedList.add(0, e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.add(0, e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testAddAllCollection() {
		List<String> e = Arrays.asList(new String[]{"Item4", "Item5"});
		expect(listHandlerMock.onModifying(eventedList, ADDING, e)).andReturn(true);
		replay(listHandlerMock);
		eventedList.addAll(e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.addAll(e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testAddAllIntCollection() {
		List<String> e = Arrays.asList(new String[]{"Item4", "Item5"});
		expect(listHandlerMock.onModifying(eventedList, ADDING, e)).andReturn(true);
		replay(listHandlerMock);
		eventedList.addAll(0, e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.addAll(0, e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testRemoveInt() {
		expect(listHandlerMock.onModifying(eventedList, REMOVING, eventedList.subList(0, 1))).andReturn(true);
		replay(listHandlerMock);
		eventedList.remove(0);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.remove(0);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testRemoveObject() {
		String e = "Item1";
		expect(listHandlerMock.onModifying(eventedList, REMOVING, Arrays.asList(new String[]{e}))).andReturn(true);
		replay(listHandlerMock);
		eventedList.remove(e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.remove(e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testSetIntE() {
		String e = "Item4";
		expect(listHandlerMock.onModifying(eventedList, UPDATING, Arrays.asList(new String[]{e}))).andReturn(true);
		replay(listHandlerMock);
		eventedList.set(1, e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.set(1, e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testRemoveAllCollection() {
		List<String> e = Arrays.asList(new String[]{"Item1", "Item3"});
		expect(listHandlerMock.onModifying(eventedList, REMOVING, e)).andReturn(true);
		replay(listHandlerMock);
		eventedList.removeAll(e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.removeAll(e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testRetainAllCollection() {
		List<String> e = Arrays.asList(new String[]{"Item1", "Item3"});
		expect(listHandlerMock.onModifying(eventedList, RETAINING, e)).andReturn(true);
		replay(listHandlerMock);
		eventedList.retainAll(e);
		verify(listHandlerMock);
		handlerRegistration.removeHandler();
		assertTrue(eventedList.listHandlers.isEmpty());
		defaultList.retainAll(e);
		assertEquals(defaultList, eventedList);
	}

	@Test
	public void testAddEventHandler() {
		HandlerRegistration hr, hr1;
		hr = eventedList.addEventHandler(null);
		assertEquals(1, eventedList.listHandlers.size());
		assertEquals(null, hr);
		ListHandler<String> lh = new ListHandler<String>() {
			@Override
			public boolean onModifying(List<String> source, Type type, Collection<? extends String> items) {
				return true;
			}
		};
		hr = eventedList.addEventHandler(lh);
		hr1 = eventedList.addEventHandler(lh);
		Assert.assertFalse(hr.equals(hr1));
		assertEquals(2, eventedList.listHandlers.size());
	}
}
