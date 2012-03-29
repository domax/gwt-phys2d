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
package com.dominichenko.pet.gwt.phys2d.client.utils;

import java.util.List;

import com.dominichenko.pet.gwt.phys2d.client.collisions.CircleCollisionDetectorTest;
import com.dominichenko.pet.gwt.phys2d.client.utils.Utils;

import junit.framework.TestCase;

//import com.google.gwt.junit.client.GWTTestCase;

import static com.dominichenko.pet.gwt.phys2d.client.utils.Utils.*;

/**
 * JUnit for testing class {@link Utils}
 *  
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class UtilsTest extends TestCase {
//public class UtilsTest extends GWTTestCase {

//	@Override
//	public String getModuleName() {
//		return "com.dominichenko.pet.games.cosmoSheep.CosmoSheep";
//	}

	public void testIsEmpty() {
		assertTrue(isEmpty(null));
		assertTrue(isEmpty(""));
		assertFalse(isEmpty(" "));
		assertFalse(isEmpty(" blah "));
	}

	public void testIsHollow() {
		assertTrue(isHollow(null));
		assertTrue(isHollow(""));
		assertTrue(isHollow(" "));
		assertTrue(isHollow(" \t\t\n"));
		assertFalse(isHollow(" blah "));
	}

	public void testCapitalize() {
		assertEquals("", capitalize(null));
		assertEquals("", capitalize("  \t\t"));
		assertEquals("Word", capitalize("word"));
		assertEquals("Word", capitalize("WORD"));
		assertEquals("Word wide web", capitalize("word wide web"));
	}

	public void testRepeat() {
		assertEquals("", repeat(null, 0));
		assertEquals("", repeat(null, -1));
		assertEquals("", repeat(".", 0));
		assertEquals("", repeat(".", -1));
		assertEquals(".", repeat(".", 1));
		assertEquals("..........", repeat(".", 10));
	}

	public void testJoinStringArrayString() {
		String[] a = new String[]{"One", "Two", "Three"};
		String d = ", ";
		assertEquals("", join((String []) null, null));
		assertEquals("", join((String []) null, d));
		assertEquals("OneTwoThree", join(a, null));
		assertEquals("One, Two, Three", join(a, d));
	}

	public void testJoinListOfQString() {
		List<String> a = java.util.Arrays.asList(new String[]{"One", "Two", "Three"});
		String d = ", ";
		assertEquals("", join((List<String>) null, null));
		assertEquals("", join((List<String>) null, d));
		assertEquals("OneTwoThree", join(a, null));
		assertEquals("One, Two, Three", join(a, d));
	}

	public void testEscapeXML() {
		assertNull(escapeXML(null));
		assertEquals("", escapeXML(""));
		assertEquals("  \t\t", escapeXML("  \t\t"));
		assertEquals("&lt;a href='https://www.google.com/#sclient=psy-ab&amp;q=GWT&amp;'&gt;Search for GWT&lt;/a&gt;",
				escapeXML("<a href='https://www.google.com/#sclient=psy-ab&q=GWT&'>Search for GWT</a>"));
	}

	public void testMakeTeaser() {
		String text = "One two three";
		String teaser = "One two";
		assertEquals("", makeTeaser(null, 0, null));
		assertEquals("", makeTeaser(null, 10, null));
		assertEquals(teaser, makeTeaser(text, 10, null));
		assertEquals(teaser + "...", makeTeaser(text, 10, "..."));
	}

	public void testConvertToPlain() {
		assertEquals("sentense 1sentence 2\nsentence 3\n\nsentence 4\n", 
				convertToPlain("<b >sentense</B> 1<p/>sentence 2<Br>sentence 3<br/><br />sentence 4<bR>"));
		assertEquals("plain\nplain",
				convertToPlain("plain\nplain"));
	}

	public void testSimpleMessageFormat() {
		assertNull(simpleMessageFormat(null));
		assertEquals("no pattern", 
				simpleMessageFormat("no pattern"));
		assertEquals("no pattern", 
				simpleMessageFormat("no pattern", "One", "Two", "Three"));
		assertEquals("placeholder One",
				simpleMessageFormat("placeholder {0}", "One", "Two", "Three"));
		assertEquals("placeholder One, then Two, finally Three", 
				simpleMessageFormat("placeholder {0}, then {1}, finally {2}", "One", "Two", "Three"));
		assertEquals("placeholder Three, then Two, finally One", 
				simpleMessageFormat("placeholder {2}, then {1}, finally {0}", "One", "Two", "Three"));
		assertEquals("placeholder Two, then Two, finally Two", 
				simpleMessageFormat("placeholder {1}, then {1}, finally {1}", "One", "Two", "Three"));
		assertEquals("placeholder One, then {1}, finally {2}", 
				simpleMessageFormat("placeholder {0}, then {1}, finally {2}", "One"));
	}
	
	public void testGetSimpleName() {
		assertNull(getSimpleName(null));
		assertEquals("UtilsTest", getSimpleName(getClass()));
		assertEquals("TestSprite", getSimpleName(CircleCollisionDetectorTest.TestSprite.class));
	}
}
