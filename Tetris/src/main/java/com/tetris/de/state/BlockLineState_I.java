package com.tetris.de.state;

import com.tetris.de.Blockline;
import com.tetris.de.state.BottomState.RELEVANT_VALUE;

/**
 * Stateinterface
 * @author MWiechmann
 *
 */
public interface BlockLineState_I {

	public void prevState(Blockline blockLine);

	public void nextState(Blockline blockLine);
	
	public RELEVANT_VALUE getRelevantValue();

}
