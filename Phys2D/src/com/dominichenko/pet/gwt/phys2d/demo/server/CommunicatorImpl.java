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
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	private static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static ValidatorFactory validatorFactory =
		Validation.byDefaultProvider().configure().buildValidatorFactory();

	@Override
	public ScoreItem[] getTopScore(Integer start, Integer count) throws IllegalArgumentException {
		if (start == null) start = 0;
		if (count == null) count = 0;
		int size = start + count < scoreItemList.size() ? count : scoreItemList.size() - start;
		log.info("start=" + start + "; count=" + count + "; size=" + size + "; ");
		if (size < 0) size = 0;
		ScoreItem[] result = new ScoreItem[size];
		for (int i = 0; i < size; i++) {
			result[i] = scoreItemList.get(i + start);
			result[i].setPlace(i + start + 1);
		}
		return result;
	}

	@Override
	public ScoreItem sendScoreItem(ScoreItem scoreItem) throws IllegalArgumentException {
		if (scoreItem != null) {
			Validator validator = validatorFactory.getValidator();
			Set<ConstraintViolation<ScoreItem>> violations = validator.validate(scoreItem);
			if (violations.isEmpty()) {
				scoreItem.setPlace(null);
				scoreItemList.add(scoreItem);
				Collections.sort(scoreItemList);
				int i = scoreItemList.indexOf(scoreItem);
				if (i >= 0)
					scoreItem.setPlace(i + 1);
			} else {
				StringBuilder errors = new StringBuilder("Validation error(s) of ");
				errors.append(scoreItem);
				errors.append(":\n");
				for (ConstraintViolation<ScoreItem> violation : violations) {
					errors.append("- ");
					errors.append(violation.getMessage());
					errors.append("\n");
				}
				throw new IllegalArgumentException(errors.toString());
			}
		}
		log.info(String.valueOf(scoreItem));
		return scoreItem;
	}
}
