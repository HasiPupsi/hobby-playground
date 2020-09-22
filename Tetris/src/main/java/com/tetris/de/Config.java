package com.tetris.de;

import java.util.List;

import com.tetris.de.handler.PointHandler;

public class Config {

	private List<String> stonesAvailableList;
	private GameConfig gameConfig;
	private FieldGrid fieldGrid;
	private PointHandler pointHandler;

	public Config(GameConfig gameConfig, List<String> stonesAvailableList, FieldGrid fieldGrid, PointHandler pointhandler) {
		this.gameConfig = gameConfig;
		this.stonesAvailableList = stonesAvailableList;
		this.fieldGrid = fieldGrid;
		this.pointHandler = pointhandler;
	}

	public List<String> getStonesAvailableList() {
		return stonesAvailableList;
	}

	public GameConfig getGameConfig() {
		return gameConfig;
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
