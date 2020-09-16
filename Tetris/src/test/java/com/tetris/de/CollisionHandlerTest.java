package com.tetris.de;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tetris.de.handler.CollisionHandler;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("TetrisConfigTest.xml")
public class CollisionHandlerTest {

	private final int boardWidth = 200;
	private final int boardHeight = 400;

	@Autowired
	private List<TetrisStone> tetrisSteine;
	private Pane bodyPane;

	private Consumer<TetrisStone> addTetrisStoneToBody = tetrisStone -> {
		tetrisStone.getBlockList().forEach(block -> {
			this.bodyPane.getChildren().add(block.getPolygon());
		});
	};

	@Before
	public void beforeTest() {
		bodyPane = new Pane();
		// 10 x 20 blocks per Line with blocksize of 20
		bodyPane.setPrefWidth(this.boardWidth);
		bodyPane.setPrefHeight(this.boardHeight);
	}

	@Test
	public void testIsRotationValidOverlapping() {
		TetrisStone iTetrisStone = tetrisSteine.get(0);
		addTetrisStoneToBody.accept(iTetrisStone);
		TetrisStone lTetrisStone = tetrisSteine.get(1);
		addTetrisStoneToBody.accept(lTetrisStone);
		List<TetrisStone> tetrisStonesOnBoard = new ArrayList<TetrisStone>();
		tetrisStonesOnBoard.add(iTetrisStone);
		assertFalse(CollisionHandler.getInstance().isRotationValid(tetrisStonesOnBoard, lTetrisStone, this.boardWidth));
	}

	@Test
	public void testIsRotationValidOutOfBoard() {
		TetrisStone lTetrisStone = tetrisSteine.get(1);
		addTetrisStoneToBody.accept(lTetrisStone);
		lTetrisStone.getBlockList().forEach(block -> {
			block.translateBlock(new Translate(400, 0));
		});
		assertFalse(CollisionHandler.getInstance().isRotationValid(new ArrayList<TetrisStone>(), lTetrisStone, this.boardWidth));
	}

}
