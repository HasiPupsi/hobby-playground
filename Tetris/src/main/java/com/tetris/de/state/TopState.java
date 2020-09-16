package com.tetris.de.state;

import com.tetris.de.Blockline;
import com.tetris.de.state.BottomState.RELEVANT_VALUE;

public class TopState implements BlockLineState_I {

	public static final String NAME = "Top";

	@Override
	public void prevState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new LeftState());
	}

	@Override
	public void nextState(Blockline blockLine) {
		blockLine.setCurrentBlockLineState(new RightState());
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