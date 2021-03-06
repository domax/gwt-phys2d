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

import com.dominichenko.pet.gwt.phys2d.client.services.CommunicatorAsync;
import com.dominichenko.pet.gwt.phys2d.client.services.JSONCommunicator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class DemoPhys2DApp implements EntryPoint {

	private static DemoPhys2DApp instance;
	private final CommunicatorAsync communicator = JSONCommunicator.getInstance();
	private DemoPane demoPane;

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
		demoPane = new DemoPane();
		RootLayoutPanel.get().add(demoPane);
	}

	public CommunicatorAsync getCommunicator() {
		return communicator;
	}
	
	public DemoPane getDemoPane() {
		return demoPane;
	}
}
