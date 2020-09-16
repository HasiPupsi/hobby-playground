package com.tetris.de;

import java.util.List;

import com.tetris.de.handler.PointHandler;

public class GameConfig {

	private List<String> stonesAvailableList;
	private int startSpeed;
	private FieldGrid fieldGrid;
	private PointHandler pointHandler;

	public GameConfig(List<String> stonesAvailableList, int startSpeed, FieldGrid fieldGrid, PointHandler pointhandler) {
		this.stonesAvailableList = stonesAvailableList;
		this.fieldGrid = fieldGrid;
		this.startSpeed = startSpeed;
		this.pointHandler = pointhandler;
	}

	public List<String> getStonesAvailableList() {
		return stonesAvailableList;
	}

	public void setStartSpeed(int startSpeed) {
		this.startSpeed = startSpeed;
	}

	public int getStartSpeed() {
		return startSpeed;
	}

	public int getBlockSize() {
		return this.fieldGrid.getBlocksize();
	}

	public int getGameWidth() {
		return this.fieldGrid.getNumberOfBlocksHorizontal() * this.fieldGrid.getBlocksize();
	}

	public int getMaxWidthForStone() {
		return getGameWidth() - getBlockSize() + 1;
	}

	public void setFieldGrid(FieldGrid fieldGrid) {
		this.setFieldGrid(fieldGrid);
	}

	public FieldGrid getFieldGrid() {
		return fieldGrid;
	}

	public PointHandler getPointHandler() {
		return pointHandler;
	}

}
