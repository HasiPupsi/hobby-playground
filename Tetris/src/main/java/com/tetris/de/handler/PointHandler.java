package com.tetris.de.handler;

import java.util.Map;

import org.apache.log4j.Logger;

public class PointHandler {

	private final Logger logger = Logger.getLogger(PointHandler.class);

	private Map<Integer, Integer> pointsForNumberOfLines;
	private double points;

	public PointHandler(Map<Integer, Integer> pointsForNumberOfLines) {
		this.pointsForNumberOfLines = pointsForNumberOfLines;
	}

	public Map<Integer, Integer> getPointsForNumberOfLines() {
		return pointsForNumberOfLines;
	}

	public double getPoints() {
		return points;
	}

	public void addPoints(int numberOfLinesRemoved, int currentLvl) {
		if (numberOfLinesRemoved > 0 && numberOfLinesRemoved <= 4) {
			points += pointsForNumberOfLines.get(numberOfLinesRemoved) * (currentLvl + 1);
		} else {
			this.logger.warn("Es können nur maximal 4 Lines gleichzeitig entfernt werden (" + numberOfLinesRemoved + ")");
		}
	}

}
