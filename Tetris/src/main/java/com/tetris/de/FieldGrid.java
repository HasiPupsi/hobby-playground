package com.tetris.de;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Translate;

/**
 * Enthält alle Polylines des Gitters und ein Array aller Blöcke welche bereits
 * auf dem Spielfeld sind. So kann leicht ermittelt werden, ob eine Reihe voll
 * ist und welche Blöcke nachrücken müssen....
 * 
 * @author MWiechmann
 *
 */
public class FieldGrid {
	
	private final Logger logger = Logger.getLogger(FieldGrid.class);

	private int blocksize;
	private int numberOfBlocksHorizontal;
	private int numberOfBlocksVertical;
	private int width;
	private int height;
	private List<Polyline> verticalLines = new ArrayList<>();
	private List<Polyline> horizontalLines = new ArrayList<>();
	private Block_I[][] blockArray;

	public FieldGrid(int blocksize, int numberOfBlocksHorizontal, int numberOfBlocksVertical) {
		this.blocksize = blocksize;
		this.numberOfBlocksHorizontal = numberOfBlocksHorizontal;
		this.numberOfBlocksVertical = numberOfBlocksVertical;
		this.width = blocksize * numberOfBlocksHorizontal;
		this.height = blocksize * numberOfBlocksVertical;
	}

	public void init() {
		this.logger.info("Initialisiere Fieldgrid.");
		// Vertical lines
		this.logger.info("Erstelle vertikale Linien");
		for (int i = 1; i * this.blocksize < this.width; i++) {
			Polyline polyLine = new Polyline(i * this.blocksize, 0, i * this.blocksize, this.height);
			polyLine.setStroke(Color.LIGHTGRAY);
			verticalLines.add(polyLine);
		}
		// Horizontal lines
		this.logger.info("Erstelle horizontale Linien");
		for (int j = 1; j * this.blocksize < this.height; j++) {
			Polyline polyLine = new Polyline(0, j * this.blocksize, this.width, j * this.blocksize);
			polyLine.setStroke(Color.LIGHTGRAY);
			horizontalLines.add(polyLine);
		}
		this.blockArray = new Block[horizontalLines.size() + 1][verticalLines.size() + 1];
		this.logger.info("Fieldgrid initialisiert.");
	}

	public List<Polyline> getVerticalLines() {
		return this.verticalLines;
	}

	public List<Polyline> getHorizontalLines() {
		return this.horizontalLines;
	}

	public Block_I[][] getBlockArray() {
		return blockArray;
	}

	public int getBlocksize() {
		return blocksize;
	}

	public int getNumberOfBlocksHorizontal() {
		return numberOfBlocksHorizontal;
	}

	public int getNumberOfBlocksVertical() {
		return numberOfBlocksVertical;
	}

	public void addTetrisStone(TetrisStone tetrisStone) {
		tetrisStone.getBlockList().forEach(block -> {
			int vertical = (int) (block.getPolygon().getBoundsInParent().getMaxX() / this.blocksize) - 1;
			int horizontal = (int) (block.getPolygon().getBoundsInParent().getMaxY() / this.blocksize) - 1;
			this.blockArray[horizontal][vertical] = block;
		});
	}

	/**
	 * Checks if there is a full line on the board
	 * 
	 * @return -1 if there are no full lines on the board, lineindex otherwise
	 */
	public int existFullLine() {
		int result = -1;
		for (int i = this.horizontalLines.size() - 1 + 1; i > 0; i--) {
			int count = 0;
			for (int j = 0; j <= this.verticalLines.size(); j++) {
				if (this.blockArray[i][j] != null) {
					count++;
				}
			}
			if (count == this.numberOfBlocksHorizontal) {
				result = i;
				break;
			}
		}
		return result;
	}

	public List<Block_I> removeLine(int lineIndex, Translate translate) {
		List<Block_I> removedBlocks = new ArrayList<>();
		Block_I[][] newBlockArray = new Block_I[this.horizontalLines.size() + 1][this.verticalLines.size() + 1];
		for (int i = this.horizontalLines.size(); i > 1; i--) {
			if (lineIndex < i) {
				newBlockArray[i] = this.blockArray[i];
			} else if (lineIndex > i) {
				newBlockArray[i + 1] = this.blockArray[i];
				// Translate down all components one line
				for (int j = 0; j < newBlockArray[i + 1].length; j++) {
					if (newBlockArray[i + 1][j] != null) {
						newBlockArray[i + 1][j].translateBlock(translate);
					}
				}
			} else {
				removedBlocks.addAll(Arrays.asList(this.blockArray[i]));
			}
		}
		this.blockArray = newBlockArray;
		return removedBlocks;
	}

}
