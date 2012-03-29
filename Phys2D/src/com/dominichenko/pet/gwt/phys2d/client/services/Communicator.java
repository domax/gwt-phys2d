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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface that pre-implements GWT style of asynchronous communication between client and server. 
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
@RemoteServiceRelativePath("communicator")
public interface Communicator extends RemoteService {
	
	/**
	 * This operation should request from server table with topmost successful results.
	 * 
	 * @param count A maximal amount of results that should be returned by server.
	 * @return An array of {@link ScoreItem}s. Empty array if there are no results.
	 * @throws IllegalArgumentException
	 */
	ScoreItem[] getTopScore(Integer count) throws IllegalArgumentException;

	/**
	 * This operation should store on server specified score.
	 * 
	 * @param scoreItem A score (final game result) that should be kept at the server.
	 * @return The same scoreItem, but with updated {@code place} property. 
	 * @throws IllegalArgumentException
	 * @see ScoreItem
	 */
	ScoreItem sendScoreItem(ScoreItem scoreItem) throws IllegalArgumentException;
}
