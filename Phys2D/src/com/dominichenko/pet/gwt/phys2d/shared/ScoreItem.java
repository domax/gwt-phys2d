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
package com.dominichenko.pet.gwt.phys2d.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Serializable Java bean that is used to encapsulate information about player's
 * score achievement.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class ScoreItem implements Serializable, Comparable<ScoreItem> {

	private static final long serialVersionUID = 1337096705293261019L;

	private String name;
	private Date date;
	private Integer score;
	private Integer place;

	/**
	 * Default empty constructor of this bean.
	 */
	public ScoreItem() {
	}

	/**
	 * Constructs score bean with specified default values.
	 * 
	 * @param name
	 *          Player's custom name.
	 * @param score
	 *          Player's score achievement.
	 * @param place
	 *          Player's place in the rating table.
	 */
	public ScoreItem(String name, Date date, Integer score, Integer place) {
		this.name = name;
		this.date = date;
		this.score = score;
		this.place = place;
	}

	/**
	 * Gets player's custom name property.
	 * 
	 * @return Name of player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets player's custom name property.
	 * 
	 * @param name
	 *          Name of player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets player's score achievement property.
	 * 
	 * @return Score result
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * Sets player's score achievement property.
	 * 
	 * @param score
	 *          Score result
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * Gets player's place in the rating table property. Starts from 1 (best).
	 * 
	 * @return Place of player
	 */
	public Integer getPlace() {
		return place;
	}

	/**
	 * Gets player's score date property.
	 * 
	 * @return score date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets player's score date property.
	 * 
	 * @param date
	 *          Score date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets player's place in the rating table property. Starts from 1 (best).
	 * 
	 * @param place
	 *          Place of player
	 */
	public void setPlace(Integer place) {
		this.place = place;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.getClass().getName());
		result.append(" {name=");
		result.append(name);
		result.append(", date=");
		result.append(date);
		result.append(", score=");
		result.append(score);
		result.append(", place=");
		result.append(place);
		result.append('}');
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreItem other = (ScoreItem) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	@Override
	public int compareTo(ScoreItem scoreItem) {
		if (scoreItem == null)
			return -1;
	
		int result = 0;
		
		if (score != null || scoreItem.score != null) {
			if (score == null)
				return 1;
			if (scoreItem.score == null)
				return -1;
			result = -score.compareTo(scoreItem.score);
			if (result != 0)
				return result;
		}
		if (date != null || scoreItem.date != null) {
			if (date == null)
				return 1;
			if (scoreItem.date == null)
				return -1;
			result = date.compareTo(scoreItem.date);
			if (result != 0)
				return result;
		}
		if (name != null || scoreItem.name != null) {
			if (name == null)
				return 1;
			if (scoreItem.name == null)
				return -1;
			result = name.compareTo(scoreItem.name);
			if (result != 0)
				return result;
		}
		if (place != null || scoreItem.place != null) {
			if (place == null)
				return 1;
			if (scoreItem.place == null)
				return -1;
			result = place.compareTo(scoreItem.place);
			if (result != 0)
				return result;
		}
		
		return result;
	}
}
