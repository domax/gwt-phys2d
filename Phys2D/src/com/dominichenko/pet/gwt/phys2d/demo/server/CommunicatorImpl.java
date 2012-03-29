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
package com.dominichenko.pet.gwt.phys2d.demo.server;

import java.util.ArrayList;
import java.util.Collections;

import com.dominichenko.pet.gwt.phys2d.client.services.Communicator;
import com.dominichenko.pet.gwt.phys2d.shared.ScoreItem;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Just in-memory score keeping implementation.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
@SuppressWarnings("serial")
public class CommunicatorImpl extends RemoteServiceServlet implements Communicator {
	
	/**
	 * Static list will keep scores over all servlet instances across JM.
	 */
	private static ArrayList<ScoreItem> scoreItemList = new ArrayList<ScoreItem>();

	@Override
	public ScoreItem[] getTopScore(Integer count) throws IllegalArgumentException {
		int size = scoreItemList.size() > count ? count : scoreItemList.size();
		ScoreItem[] result = new ScoreItem[size];
		for (int i = 0; i < result.length; i++)
			result[i] = scoreItemList.get(i);
		return result;
	}

	@Override
	public ScoreItem sendScoreItem(ScoreItem scoreItem) throws IllegalArgumentException {
		if (scoreItem != null) {
			scoreItem.setPlace(null);
			scoreItemList.add(scoreItem);
			Collections.sort(scoreItemList);
			int i = scoreItemList.indexOf(scoreItem);
			if (i >= 0)
				scoreItem.setPlace(i + 1);
		}
		return scoreItem;
	}
}
