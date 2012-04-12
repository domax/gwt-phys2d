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
package com.dominichenko.pet.gwt.phys2d.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class DemoPane extends Composite implements HasSelectionHandlers<Composite> {

	@UiField TabLayoutPanel tabLayoutPanel;

	private static DemoPaneUiBinder uiBinder = GWT.create(DemoPaneUiBinder.class);

	interface DemoPaneUiBinder extends UiBinder<LayoutPanel, DemoPane> {
	}

	public DemoPane() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addSelectionHandler(SelectionHandler<Composite> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}
	
	@UiHandler("tabLayoutPanel")
	void onTabLayoutPanelSelection(SelectionEvent<Integer> event) {
		Composite widget = (Composite) tabLayoutPanel.getWidget(tabLayoutPanel.getSelectedIndex());
		GWT.log("DemoPane.onTabLayoutPanelSelection(SelectionEvent): "
				+ "selectedItem=" + event.getSelectedItem() + "; "
				+ "widget=" + widget.getClass().getName());
		SelectionEvent.fire(this, widget);
	}
}
