package com.tetris.de.state;

import com.tetris.de.Blockline;

public class BottomState implements BlockLineState_I {

	public enum RELEVANT_VALUE {
		X, Y;
	}

	public static final String NAME = "Bottom";

	@Override
	public void prevState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new RightState());
	}

	@Override
	public void nextState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new LeftState());
	}
	
	@Override
	public RELEVANT_VALUE getRelevantValue() {
		return RELEVANT_VALUE.X;
	}

	@Override
	public String toString() {
		return NAME;
	}

}
