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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessengerPane extends Composite {

	private static MessengerPaneUiBinder uiBinder = GWT.create(MessengerPaneUiBinder.class);
	private DialogBox dialogBox;
	@UiField VerticalPanel dialogVPanel;
	@UiField HTML content;
	@UiField Button closeButton;

	interface MessengerPaneUiBinder extends UiBinder<VerticalPanel, MessengerPane> {
	}

	public MessengerPane(DialogBox dialogBox) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dialogBox = dialogBox;
	}

	@UiHandler("closeButton")
	void onCloseButtonClick(ClickEvent event) {
		dialogBox.hide();
	}
}
