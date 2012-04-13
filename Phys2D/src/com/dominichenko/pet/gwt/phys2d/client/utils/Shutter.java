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

import com.google.gwt.cell.client.ImageLoadingCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * This singleton class encapsulates a logic for locking whole screen
 * when some long operation is processed, and user's actions with UI
 * aren't desirable.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 */
public class Shutter {

	private static final String DEF_LABEL = "Loading...";
	private static Shutter instance = new Shutter();
	private PopupPanel panel;
	private int level = 0;
	private Label label;
	
	/**
	 * Private constructor prevents creating additional instances of shutter.
	 */
	private Shutter() {
		panel = new PopupPanel();
		panel.setAnimationEnabled(true);
		panel.setGlassEnabled(true);
		panel.setModal(true);
		panel.setStylePrimaryName("shutter");
		SafeHtml loading = new ImageLoadingCell.DefaultRenderers().getLoadingRenderer().render(null);
		HTMLPanel hp = new HTMLPanel("<div id='shutter_img'>" + loading.asString() + "</div>"
				+ "<div id='shutter_label'></div>");
		hp.setSize("100%", "100%");
		label = new Label(DEF_LABEL);
		hp.add(label, "shutter_label");
		panel.add(hp);
	}
	
	/**
	 * Shows shutter and locks screen.
	 */
	public static void lock() {
		instance.panel.center();
		instance.level++;
	}
	
	/**
	 * Shows shutter and locks screen with optional label. 
	 * @param label Short message on a popup box
	 */
	public static void lock(String label) {
		if (!Utils.isHollow(label))
			instance.label.setText(label);
		lock();
	}
	
	/**
	 * Hides shutter and unlocks screen. 
	 */
	public static void unlock() {
		instance.level--;
		if (instance.level <= 0) {
			instance.panel.hide();
			instance.label.setText(DEF_LABEL);
			instance.level = 0;
		}
	}
}
