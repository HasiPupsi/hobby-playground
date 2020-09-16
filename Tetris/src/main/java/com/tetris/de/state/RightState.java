package com.tetris.de.state;

import com.tetris.de.Blockline;
import com.tetris.de.state.BottomState.RELEVANT_VALUE;

public class RightState implements BlockLineState_I {

	public static final String NAME = "Right";

	@Override
	public void prevState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new TopState());
	}

	@Override
	public void nextState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new BottomState());
	}
	
	@Override
	public RELEVANT_VALUE getRelevantValue() {
		return RELEVANT_VALUE.Y;
	}

	@Override
	public String toString() {
		return NAME;
	}

}
