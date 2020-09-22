package com.tetris.de;

import java.util.Calendar;

import com.tetris.de.util.SpeedCalculation_I;

public class GameConfig {

	private int startSpeed;
	private long startPeriod;
	private int numberOfLinesClearedToLvlUp;
	private int maxLvl;

	private long numberOfStonesCreated;
	private Calendar startCalendar;
	private Calendar endCalendar;
	private int currentLvl = 1;
	private int numberOfLinesCleared;

	private SpeedCalculation_I speedCalculation;

	public GameConfig(int startSpeed, long startPeriod, int numberOfLinesClearedToLvlUp, int maxLvl) {
		this.startSpeed = startSpeed;
		this.startPeriod = startPeriod;
		this.numberOfLinesClearedToLvlUp = numberOfLinesClearedToLvlUp;
		this.maxLvl = maxLvl;
	}

	public void init() {
		// Default calculation
		if (this.speedCalculation == null) {
			this.speedCalculation = ((lvl, periodAtStart, maxLvlOfGame) -> {
				return periodAtStart - (lvl * (periodAtStart / maxLvlOfGame));
			});
		}
	}

	public void setCurrentLvl(int currentLvl) {
		this.currentLvl = currentLvl;
	}

	public int getCurrentLvl() {
		return this.currentLvl;
	}

	public void setNumberOfLinesCleared(int numberOfLinesCleared) {
		this.numberOfLinesCleared = numberOfLinesCleared;
	}

	public int getNumberOfLinesCleared() {
		return this.numberOfLinesCleared;
	}

	public Calendar getStartCalendar() {
		return this.startCalendar;
	}

	public Calendar getEndCalendar() {
		return endCalendar;
	}

	public int getStartSpeed() {
		return this.startSpeed;
	}

	public long getStartPeriod() {
		return this.startPeriod;
	}

	public int getNumberOfLinesClearedToLvlUp() {
		return this.numberOfLinesClearedToLvlUp;
	}

	public int getMaxLvl() {
		return maxLvl;
	}

	public long getNumberOfStonesCreated() {
		return numberOfStonesCreated;
	}

	public void setSpeedCalculation(SpeedCalculation_I speedCalculation) {
		this.speedCalculation = speedCalculation;
	}

	public SpeedCalculation_I getSpeedCalculation() {
		return speedCalculation;
	}

	public void incrementNumberOfStones() {
		numberOfStonesCreated++;
	}

	public void incrementLvl() {
		currentLvl++;
	}

	public void addNumberOfLinesCleared(int numberOfLinesCleared) {
		this.numberOfLinesCleared += numberOfLinesCleared;
	}

	public boolean isReadyToLvlUp() {
		return this.numberOfLinesCleared/ this.numberOfLinesClearedToLvlUp >= this.currentLvl;
	}

	public long calculatePeriodForCurrentLvl() {
		return this.speedCalculation.calculateSpeed(this.currentLvl, this.startPeriod, this.maxLvl);
	}

	public void startGame() {
		this.startCalendar = Calendar.getInstance();
	}

	public void endGame() {
		this.endCalendar = Calendar.getInstance();
	}

}
