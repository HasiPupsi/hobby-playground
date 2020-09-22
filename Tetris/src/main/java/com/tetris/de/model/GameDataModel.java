package com.tetris.de.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tetris.de.Config;
import com.tetris.de.TetrisStone;

public class GameDataModel {

	private Point startPointBausteine;
	private Optional<TetrisStone> currentTetrisStone = Optional.empty();
	private List<TetrisStone> allTetrisStoneInGame = new ArrayList<>();
	private Config config;

	public GameDataModel(Config gameConfig, Point startPointBausteine) {
		this.config = gameConfig;
		this.startPointBausteine = startPointBausteine;
	}

	public Config getConfig() {
		return config;
	}

	public Point2D getStartPointBausteine() {
		return startPointBausteine;
	}

	public void setCurrentBaustein(TetrisStone currentTetrisStone) {
		this.currentTetrisStone = Optional.ofNullable(currentTetrisStone);
	}

	public Optional<TetrisStone> getCurrentTetrisStone() {
		return currentTetrisStone;
	}

	public List<TetrisStone> getAllTetrisStoneInGame() {
		return allTetrisStoneInGame;
	}

	public void addTetrisStone(TetrisStone tetrisStone) {
		this.allTetrisStoneInGame.add(tetrisStone);
		this.config.getFieldGrid().addTetrisStone(tetrisStone);
	}

}
