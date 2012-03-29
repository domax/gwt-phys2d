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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.dominichenko.pet.gwt.phys2d.client.utils.ImmutableArrayList;
import com.dominichenko.pet.gwt.phys2d.client.utils.ImmutableArrayListException;

/**
 * JUnit for testing classes:
 * <ul>
 * <li>{@link ImmutableArrayList};</li>
 * <li>{@link ImmutableArrayListException};</li>
 * </ul>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class ImmutableArrayListTest {

	private ImmutableArrayList<String> immutableList;

	@Before
	public void setUp() throws Exception {
		immutableList = new ImmutableArrayList<String>("Item1", "Item2", "Item3");
	}

	@Test
	public void testAdd() {
		try {
			immutableList.add("Item4");
			fail("ImmutableArrayList.add(String)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot add element [Item4] into immutable list.", ex.getMessage());
		}
		try {
			immutableList.add(0, "Item4");
			fail("ImmutableArrayList.add(int, String)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot add element [Item4] into immutable list.", ex.getMessage());
		}
		try {
			immutableList.addAll(Arrays.asList(new String[]{"Item4", "Item5"}));
			fail("ImmutableArrayList.addAll(Collection)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot add elements [Item4, Item5] into immutable list.", ex.getMessage());
		}
		try {
			immutableList.addAll(0, Arrays.asList(new String[]{"Item4", "Item5"}));
			fail("ImmutableArrayList.addAll(int, Collection)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot add elements [Item4, Item5] into immutable list.", ex.getMessage());
		}
	}
	
	@Test
	public void testRemove() {
		try {
			immutableList.remove(0);
			fail("ImmutableArrayList.remove(int)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove element [Item1] from immutable list.", ex.getMessage());
		}
		try {
			immutableList.remove("Item1");
			fail("ImmutableArrayList.remove(Object)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove element [Item1] from immutable list.", ex.getMessage());
		}
		try {
			immutableList.removeAll(Arrays.asList(new String[]{"Item1", "Item2"}));
			fail("ImmutableArrayList.removeAll(Collection)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove elements [Item1, Item2] from immutable list.", ex.getMessage());
		}
		try {
			immutableList.removeRange(0, 2);
			fail("ImmutableArrayList.removeRange(int, int)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove elements [Item1, Item2] from immutable list.", ex.getMessage());
		}
		try {
			Iterator<String> i = immutableList.iterator();
			i.next();
			i.remove();
			fail("ImmutableArrayList.iterator().next().remove()");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove element [Item1] from immutable list.", ex.getMessage());
		}
	}
	
	@Test
	public void testClearSetRetain() {
		try {
			immutableList.clear();
			fail("ImmutableArrayList.clear()");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot remove elements [Item1, Item2, Item3] from immutable list.", ex.getMessage());
		}
		try {
			immutableList.set(0, "Item4");
			fail("ImmutableArrayList.set(int, String)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot set element [Item4] into immutable list.", ex.getMessage());
		}
		try {
			immutableList.retainAll(Arrays.asList(new String[]{"Item1", "Item2"}));
			fail("ImmutableArrayList.retain(Collection)");
		} catch (ImmutableArrayListException ex) {
			assertEquals("Cannot retain elements [Item1, Item2] in immutable list.", ex.getMessage());
		}
	}
	
}
