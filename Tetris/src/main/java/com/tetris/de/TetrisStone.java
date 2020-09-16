package com.tetris.de;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tetris.de.constant.Const;
import com.tetris.de.state.BlockLineState_I;
import com.tetris.de.state.BottomState;
import com.tetris.de.state.LeftState;
import com.tetris.de.state.RightState;
import com.tetris.de.state.TopState;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class TetrisStone implements BlockListener_I {

	private final Map<Integer, BlockLineState_I> stateMap = new HashMap<>();

	private List<Block_I> blockList = new ArrayList<>();
	private List<String> gridBlockList;
	private String color = "000000";
	private int blocksize;
	private double multiplicatorX;
	private double multiplicatorY;

	public TetrisStone(String blocksize, List<String> gridBlockList, String color, double multiplicatorX, double multiplicatorY) {
		this.blocksize = Integer.valueOf(blocksize);
		this.gridBlockList = gridBlockList;
		this.color = color;
		this.multiplicatorX = multiplicatorX;
		this.multiplicatorY = multiplicatorY;
	}

	public List<Block_I> getBlockList() {
		return blockList;
	}

	public void init() {
		initParameter();
		gridBlockList.stream().forEach(value -> {
			String[] gridValues = value.split(",");
			if (gridValues.length == 2) {
				int x = Integer.parseInt(gridValues[0]);
				int y = Integer.parseInt(gridValues[1]);
				List<Blockline> blockLineList = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					BlockLineState_I currentBlockLineState = stateMap.get(i);
					Optional<Blockline> blockline = Optional.empty();
					switch (i) {
						case 0:
							// Topstate
							blockline = Optional.of(new Blockline(currentBlockLineState, x * blocksize, y * blocksize, (x + 1) * blocksize, y * blocksize));
							break;
						case 1:
							// Rightstate
							blockline = Optional.of(new Blockline(currentBlockLineState, (x + 1) * blocksize, y * blocksize, (x + 1) * blocksize, (y + 1) * blocksize));
							break;
						case 2:
							// Bottomstate
							blockline = Optional.of(new Blockline(currentBlockLineState, (x + 1) * blocksize, (y + 1) * blocksize, x * blocksize, (y + 1) * blocksize));
							break;
						case 3:
							// Leftstate
							blockline = Optional.of(new Blockline(currentBlockLineState, x * blocksize, (y + 1) * blocksize, x * blocksize, y * blocksize));
							break;
						default:
							// Nothing
							break;
					}
					if (blockline.isPresent()) {
						blockline.get().setFill(Color.AQUAMARINE);
						blockLineList.add(blockline.get());
					}
				}
				blockList.add(new Block(this.color, blockLineList));
			} else {
				throw new IllegalArgumentException("Fehler. Array muss eine Größe von 2 haben!");
			}
		});
	}

	private void initParameter() {
		stateMap.put(0, new TopState());
		stateMap.put(1, new RightState());
		stateMap.put(2, new BottomState());
		stateMap.put(3, new LeftState());
	}

	public double getMinX() {
		Optional<Double> result = Optional.empty();
		for (int i = 0; i < blockList.size(); i++) {
			Optional<Blockline> minXBlockline = blockList.get(i).getBlockLineList().stream().min(Comparator.comparing(blockline -> {
				return blockline.getBoundsInParent().getMinX();
			}));
			if (minXBlockline.isPresent()) {
				result = !result.isPresent() || result.get() > minXBlockline.get().getBoundsInParent().getMinX() ? Optional.of(minXBlockline.get().getBoundsInParent().getMinX()) : result;
			}
		}
		return result.orElse(-1d);
	}

	public double getMaxX() {
		Optional<Double> result = Optional.empty();
		for (int i = 0; i < blockList.size(); i++) {
			Optional<Blockline> maxXBlockline = blockList.get(i).getBlockLineList().stream().max(Comparator.comparing(blockline -> {
				return blockline.getBoundsInParent().getMaxX();
			}));
			if (maxXBlockline.isPresent()) {
				result = !result.isPresent() || result.get() < maxXBlockline.get().getBoundsInParent().getMaxX() ? Optional.of(maxXBlockline.get().getBoundsInParent().getMaxX()) : result;
			}
		}
		return result.orElse(-1d);
	}

	// Eine Polyline hat trotz Strokewidth von 1 eine Breite von 3 Pixel....
	// -> minY + 1
	public double getMinY() {
		Optional<Double> result = Optional.empty();
		for (int i = 0; i < blockList.size(); i++) {
			Optional<Blockline> minYBlockline = blockList.get(i).getBlockLineList().stream().min(Comparator.comparing(blockline -> {
				return blockline.getBoundsInParent().getMinY();
			}));
			if (minYBlockline.isPresent()) {
				result = !result.isPresent() || result.get() < minYBlockline.get().getBoundsInParent().getMinY() ? Optional.of(minYBlockline.get().getBoundsInParent().getMinY()) : result;
			}
		}
		return result.orElse(-1d);
	}

	// Eine Polyline hat trotz Strokewidth von 1 eine Breite von 3 Pixel....
	// -> minY - 1
	public double getMaxY() {
		Optional<Double> result = Optional.empty();
		for (int i = 0; i < blockList.size(); i++) {
			Optional<Blockline> maxYBlockline = blockList.get(i).getBlockLineList().stream().max(Comparator.comparing(blockline -> {
				return blockline.getBoundsInParent().getMaxY();
			}));
			if (maxYBlockline.isPresent()) {
				result = !result.isPresent() || result.get() < maxYBlockline.get().getBoundsInParent().getMaxY() ? Optional.of(maxYBlockline.get().getBoundsInParent().getMaxY()) : result;
			}
		}
		return result.orElse(-1d);
	}

	public void handleRotation(boolean cw) {
		Rotate rotationTransform = new Rotate(cw ? Const.ROTATION_ANGLE : Math.negateExact(Const.ROTATION_ANGLE), getPivotPointX(), getPivotPointY());
		blockList.forEach(block -> {
			block.handleRotation(cw, rotationTransform);
		});
	}

	private double getPivotPointX() {
		return this.blocksize * this.multiplicatorX;
	}

	private double getPivotPointY() {
		return this.blocksize * this.multiplicatorY;
	}

	@Override
	public void destroyBlock(Block_I block) {
		this.blockList.remove(block);
	}

}
