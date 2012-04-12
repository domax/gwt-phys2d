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
package com.dominichenko.pet.gwt.phys2d.demo.client.circleCollision;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CircleCollisionPane extends Composite {

	@UiField VerticalPanel verticalPanel;
	@UiField SimplePanel collisionContainer;
	@UiField Button pauseBtn;
	@UiField Button resumeBtn;
	@UiField Button resetBtn;
	@UiField Button startBtn;

	private CIrcleCollisionGameScene gameScene;

	private static CircleCollisionPaneUiBinder uiBinder = GWT.create(CircleCollisionPaneUiBinder.class);

	interface CircleCollisionPaneUiBinder extends UiBinder<VerticalPanel, CircleCollisionPane> {
	}

	public CircleCollisionPane() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CIrcleCollisionGameScene getGameScene() {
		return gameScene;
	}

	@UiHandler("pauseBtn")
	void onPauseBtnClick(ClickEvent event) {
		gameScene.cancel();
		pauseBtn.setVisible(false);
		resumeBtn.setVisible(true);
	}

	@UiHandler("resumeBtn")
	void onResumeBtnClick(ClickEvent event) {
		gameScene.start();
		pauseBtn.setVisible(true);
		resumeBtn.setVisible(false);
	}

	@UiHandler("resetBtn")
	void onResetBtnClick(ClickEvent event) {
		gameScene.reset();
		pauseBtn.setVisible(true);
		resumeBtn.setVisible(false);
	}

	@UiHandler("startBtn")
	void onStartBtnClick(ClickEvent event) {
		gameScene = new CIrcleCollisionGameScene(collisionContainer);
		gameScene.start();
		startBtn.setVisible(false);
		pauseBtn.setVisible(true);
		resetBtn.setVisible(true);
	}
}
