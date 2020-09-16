package com.tetris.de;

import java.util.function.Predicate;

import com.tetris.de.state.BlockLineState_I;

import javafx.scene.shape.Polyline;
import javafx.scene.transform.Transform;

/**
 * Context of State-Pattern
 * 
 * @author MWiechmann
 *
 */
public class Blockline extends Polyline {

	public static Predicate<Blockline> isState(String state) {
		return blockline -> blockline.getCurrentBlockLineState().toString().compareTo(state) == 0;
	}

	public static Predicate<Blockline> isNotState(String notState) {
		return blockline -> blockline.blockLineState.toString().compareTo(notState) != 0;
	}

	private static Predicate<Blockline> isBlockLineIntersectionAndRelevant(Blockline blocklineCompare) {
		return blockline -> blockline.getCurrentBlockLineState().getRelevantValue().compareTo(blocklineCompare.getCurrentBlockLineState().getRelevantValue()) == 0 && blockline.getBoundsInParent().intersects(blocklineCompare.getBoundsInParent());
	}

	private static Predicate<Blockline> isBlockLineIntersectionEqualBockSize(Blockline blocklineCompare) {
		return blockline -> {
			boolean result = false;
			switch (blockline.getCurrentBlockLineState().getRelevantValue()) {
				case X:
					result = blockline.getBoundsInParent().getMinX() == blocklineCompare.getBoundsInParent().getMinX() && blockline.getBoundsInParent().getMaxX() == blocklineCompare.getBoundsInParent().getMaxX();
					break;
				case Y:
					result = blockline.getBoundsInParent().getMinY() == blocklineCompare.getBoundsInParent().getMinY() && blockline.getBoundsInParent().getMaxY() == blocklineCompare.getBoundsInParent().getMaxY();
					break;
				default:
					break;
			}
			return result;
		};
	}

	public static Predicate<Blockline> isBlockLineIntersection(Blockline blocklineCompare) {
		return isBlockLineIntersectionAndRelevant(blocklineCompare).and(isBlockLineIntersectionEqualBockSize(blocklineCompare));
	}

	private BlockLineState_I blockLineState;

	public Blockline(BlockLineState_I blockLineState, double... points) {
		super(points);
		this.setStrokeWidth(1);
		this.blockLineState = blockLineState;
	}

	public BlockLineState_I getCurrentBlockLineState() {
		return this.blockLineState;
	}

	public void setCurrentBlockLineState(BlockLineState_I currentBlockLineState) {
		this.blockLineState = currentBlockLineState;
	}

	/**
	 * 
	 * @param cw -> state clockwise or counterclockwise
	 */
	public void handleRotation(boolean cw, Transform rotationTransform) {
		if (cw) {
			this.blockLineState.nextState(this);
		} else {
			this.blockLineState.prevState(this);
		}
		this.getTransforms().add(rotationTransform);
	}

}
