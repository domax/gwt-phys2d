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

import java.util.Arrays;

import com.dominichenko.pet.gwt.phys2d.client.services.CommunicatorAsync;
import com.dominichenko.pet.gwt.phys2d.client.services.JSONCommunicator;
import com.dominichenko.pet.gwt.phys2d.client.utils.Messenger;
import com.dominichenko.pet.gwt.phys2d.shared.ScoreItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class DemoPhys2DApp implements EntryPoint {

	private final CommunicatorAsync communicator = JSONCommunicator.getInstance();
	private DemoPhys2DGameScene gameScene;
	private static DemoPhys2DApp instance;

	public DemoPhys2DApp() {
		instance = this;
	}

	/**
	 * Gets an instance of application.<br/>
	 * Well, it's not classical singleton, but it's simple and enough for
	 * regular needs.
	 * 
	 * @return An instance of current {@link DemoPhys2DApp} application. 
	 */
	public static DemoPhys2DApp getApp() {
		return instance;
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

//		final Button sendButton = new Button("Score");
//		sendButton.addStyleName("sendButton");
//		sendButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				communicator.getTopScore(10, new AsyncCallback<ScoreItem[]>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						GWT.log(DemoPhys2DApp.class.getName() + "#addClickHandler", caught);
//						Messenger.say("Error", "<span style='color: red;'>" + caught.getMessage() + "</span>");
//					}
//
//					@Override
//					public void onSuccess(ScoreItem[] result) {
//						GWT.log(DemoPhys2DApp.class.getName() + "#addClickHandler: " + Arrays.toString(result));
//						Messenger.say("Score Board", Arrays.toString(result));
//					}
//				});
//			}
//		});
//		RootPanel.get("buttonContainer").add(sendButton);
//		
		DemoPane demoPane = new DemoPane();
		RootPanel.get().add(demoPane);

		gameScene = new DemoPhys2DGameScene(demoPane.collisionContainer);
		gameScene.start();
	}

	public CommunicatorAsync getCommunicator() {
		return communicator;
	}

	public DemoPhys2DGameScene getGameScene() {
		return gameScene;
	}
}
