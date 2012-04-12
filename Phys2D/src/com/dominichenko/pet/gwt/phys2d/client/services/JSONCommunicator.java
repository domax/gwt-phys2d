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
package com.dominichenko.pet.gwt.phys2d.client.services;

import com.dominichenko.pet.gwt.phys2d.shared.ScoreItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Singleton class that encapsulates logic of GWT-styled asynchronous communication between client and server.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class JSONCommunicator implements CommunicatorAsync {
	
	private final CommunicatorAsync communicator;
	private static JSONCommunicator instance;

	/**
	 * Private constructor disables possibility of instantiation this class except inside.
	 */
	private JSONCommunicator() {
		communicator = GWT.create(Communicator.class);
	}
	
	/**
	 * Gets an existing instance of this class. If it wasn't instantiated yet, it is created.
	 * 
	 * @return An instance of this class.
	 */
	public static JSONCommunicator getInstance() {
		if (instance == null)
			instance = new JSONCommunicator();
		return instance;
	}

	@Override
	public void getTopScore(Integer start, Integer count, AsyncCallback<ScoreItem[]> callback)
			throws IllegalArgumentException {
		communicator.getTopScore(start, count, callback);
	}

	@Override
	public void sendScoreItem(ScoreItem scoreItem, AsyncCallback<ScoreItem> callback) throws IllegalArgumentException {
		communicator.sendScoreItem(scoreItem, callback);
	}
}
