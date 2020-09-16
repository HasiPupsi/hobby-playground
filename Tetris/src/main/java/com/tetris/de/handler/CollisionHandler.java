package com.tetris.de.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tetris.de.Blockline;
import com.tetris.de.TetrisStone;
import com.tetris.de.state.BottomState;
import com.tetris.de.state.TopState;
import com.tetris.de.util.result.ResultFactory;
import com.tetris.de.util.result.Result_I;

public class CollisionHandler {

	private static CollisionHandler instance;

	public static CollisionHandler getInstance() {
		if (instance == null) {
			instance = new CollisionHandler();
		}
		return instance;
	}

	private CollisionHandler() {
	}

	// parallelstream not possible -> issues with coordinate jumping of the
	// currentTetrisStone
	public Set<Blockline> isCollisionDetected(List<TetrisStone> tetrisStonesOnBoard, TetrisStone currentTetrisStone) {
		Set<Blockline> resultList = new HashSet<>();
		currentTetrisStone.getBlockList().stream().forEach(block -> {
			// Filter all lines which have not the state 'TopState'
			block.getBlockLineList().stream().filter(Blockline.isNotState(TopState.NAME)).forEach(filteredBlockline -> {
				tetrisStonesOnBoard.stream().forEach(tetris -> {
					tetris.getBlockList().stream().forEach(blockOnBoard -> 
						// Filter all lines which have not the state 'BottomState'
						blockOnBoard.getBlockLineList().stream().filter(Blockline.isNotState(BottomState.NAME)).forEach(filteredBlockLineOnBoard -> {
							if(Blockline.isBlockLineIntersection(filteredBlockLineOnBoard).test(filteredBlockline)) {
								resultList.add(filteredBlockline);
							}
						})
					);
				});
			});
		});
		return resultList;
	}

	public boolean isRotationValid(List<TetrisStone> tetrisStonesOnBoard, TetrisStone currentTetrisStone, int maxXBoard) {
		return !areBlocksOverlapping(tetrisStonesOnBoard, currentTetrisStone) && !isTetrisStoneOutsideOfBoard(currentTetrisStone, maxXBoard);
	}

	private boolean areBlocksOverlapping(List<TetrisStone> tetrisStonesOnBoard, TetrisStone currentTetrisStone) {
		Result_I<Boolean> result = ResultFactory.createBooleanResult();
		tetrisStonesOnBoard.forEach(tetrisStone -> {
			tetrisStone.getBlockList().forEach(block -> {
				currentTetrisStone.getBlockList().forEach(blockCurrent -> {
					if (block.getPolygon().getBoundsInParent().contains(blockCurrent.getPolygon().getBoundsInParent())) {
						result.setResult(true);
					}
				});
			});
		});
		return result.getResult();
	}

	private boolean isTetrisStoneOutsideOfBoard(TetrisStone currentTetrisStone, int maxXBoard) {
		return currentTetrisStone.getMinX() < -1 || currentTetrisStone.getMaxX() > maxXBoard + 1; 
	}

}
