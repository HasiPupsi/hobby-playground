package com.tetris.de.util;

@FunctionalInterface
public interface SpeedCalculation_I {

	/**
	 * Has to Return a value in ms (Timerlinecycle)
	 * @param lvl
	 * @return
	 */
	public long calculateSpeed(int lvl, long startPeriod, int maxLvl);

}
