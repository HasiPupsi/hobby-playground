package com.tetris.de.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tetris.de.Block_I;
import com.tetris.de.Blockline;
import com.tetris.de.FieldGrid;
import com.tetris.de.TetrisStone;
import com.tetris.de.Ticker;
import com.tetris.de.TickerListener;
import com.tetris.de.handler.CollisionHandler;
import com.tetris.de.model.GameDataModel;
import com.tetris.de.state.BottomState;
import com.tetris.de.state.LeftState;
import com.tetris.de.state.RightState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

public class GameLayoutController implements EventHandler<KeyEvent>, TickerListener {

	@FXML
	private Pane leftPane;

	@FXML
	private Pane bodyPane;

	@FXML
	private Label pointLbl;

	private GameDataModel dataModel;
	private Ticker ticker;
	private DoubleProperty x = new SimpleDoubleProperty(0);
	private DoubleProperty y = new SimpleDoubleProperty(0);
	private IntegerProperty points = new SimpleIntegerProperty(0);

	public void init(GameDataModel dataModel) {
		this.dataModel = dataModel;
		this.ticker = new Ticker();
		this.ticker.addTickerListener(this);
		this.bodyPane.setPrefWidth(this.dataModel.getConfig().getGameWidth());
		this.leftPane.setPrefWidth((((Pane) this.bodyPane.getParent()).getPrefWidth() - this.bodyPane.getPrefWidth()) / 2);

		// Create background grid
		FieldGrid fieldGrid = this.dataModel.getConfig().getFieldGrid();
		fieldGrid.getHorizontalLines().forEach(horizontalLine -> {
			this.bodyPane.getChildren().add(horizontalLine);
		});
		fieldGrid.getVerticalLines().forEach(verticalLine -> {
			this.bodyPane.getChildren().add(verticalLine);
		});

		initDatabinding();
	}

	private void initDatabinding() {
		pointLbl.textProperty().bind(points.asString());
	}

	public Pane getBodyPane() {
		return bodyPane;
	}

	public void update() {
		updateBoard();
		addNewStone();
	}

	private void updateBoard() {
		Optional<TetrisStone> currentStone = this.dataModel.getCurrentTetrisStone();
		if (currentStone.isPresent()) {
			this.dataModel.addTetrisStone(currentStone.get());
			int numberOfLinesRemoved = this.removeFullLines(this.dataModel.getConfig().getFieldGrid());
			handleLvl(numberOfLinesRemoved);
		}
	}

	private void handleLvl(int numberOfLinesRemoved) {
		this.dataModel.getConfig().getGameConfig().addNumberOfLinesCleared(numberOfLinesRemoved);
		if (this.dataModel.getConfig().getGameConfig().isReadyToLvlUp()) {
			this.dataModel.getConfig().getGameConfig().incrementLvl();
			this.ticker.startTimeLine(this.dataModel.getConfig().getGameConfig().calculatePeriodForCurrentLvl());
		}
	}

	private void addNewStone() {
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/tetris/de/TetrisStones.xml")) {
			TetrisStone stone = createNewTetrisStone(ctx);
			stone.getBlockList().forEach(block -> {
				bodyPane.getChildren().add(block.getPolygon());
			});
		}
	}

	private TetrisStone createNewTetrisStone(ClassPathXmlApplicationContext ctx) {
		// Create random stone
		List<String> stonesAvailableList = dataModel.getConfig().getStonesAvailableList();
		int randomNum = ThreadLocalRandom.current().nextInt(0, stonesAvailableList.size());
		final TetrisStone result = (TetrisStone) ctx.getBean(stonesAvailableList.get(randomNum));
		this.dataModel.setCurrentBaustein(result);

		// Translate stone to middle
		Translate translateTransform = new Translate();
		this.x = new SimpleDoubleProperty(this.dataModel.getConfig().getGameWidth() / 2d);
		this.y = new SimpleDoubleProperty(0);
		translateTransform.xProperty().bind(this.x);
		translateTransform.yProperty().bind(this.y);
		result.getBlockList().stream().forEach(block -> {
			block.addBlockListener(result);
			block.translateBlock(translateTransform);
		});
		return result;
	}

	private int removeFullLines(FieldGrid gitter) {
		int fullLineIndex = -1;
		Translate translateOneLineDown = new Translate();
		translateOneLineDown.setX(0);
		translateOneLineDown.setY(this.dataModel.getConfig().getBlockSize());
		int count = 0;
		while ((fullLineIndex = gitter.existFullLine()) != -1) {
			List<Block_I> removedBlocks = this.dataModel.getConfig().getFieldGrid().removeLine(fullLineIndex, translateOneLineDown);
			removedBlocks.forEach(block -> {
				if (block != null) {
					this.bodyPane.getChildren().remove(block.getPolygon());
					block.notifyBlockListener();
				}
			});
			count++;
		}
		this.dataModel.getConfig().getPointHandler().addPoints(count, 0);
		this.points.set((int) (this.dataModel.getConfig().getPointHandler().getPoints()));
		return count;
	}

	@Override
	public void handle(KeyEvent event) {
		int stoneSize = this.dataModel.getConfig().getBlockSize();
		Optional<TetrisStone> currentStoneOpt = this.dataModel.getCurrentTetrisStone();
		if (currentStoneOpt.isPresent()) {
			boolean doUpdate = false;
			// Find current Collisions to block movement left and right
			TetrisStone currentStone = currentStoneOpt.get();
			Set<Blockline> collisionLinesFromCurrentStone = CollisionHandler.getInstance().isCollisionDetected(this.dataModel.getAllTetrisStoneInGame(), currentStone);
			switch (event.getCode()) {
				case RIGHT:
					handleRightKey(collisionLinesFromCurrentStone, currentStone, stoneSize);
					break;
				case LEFT:
					handleLeftKey(collisionLinesFromCurrentStone, currentStone, stoneSize);
					break;
				case DOWN:
					doUpdate = handleDownKey(currentStone, stoneSize);
					break;
				case SPACE:
					handleSpaceKey(this.dataModel.getAllTetrisStoneInGame(), currentStone, this.dataModel.getConfig().getGameWidth());
					break;
				case ENTER:
					// Creates new stone
					addNewStone();
					break;
				default:
					// Do nothing
					break;
			}
			if (doUpdate) {
				update();
			} else {
				handleBottomCollision(CollisionHandler.getInstance().isCollisionDetected(this.dataModel.getAllTetrisStoneInGame(), currentStone));
			}
		} else {
			if (event.getCode().compareTo(KeyCode.ENTER) == 0) {
				// Creates new stone
				addNewStone();
				ticker.startTimeLine(1000);
			}
		}
	}

	private void handleBottomCollision(Set<Blockline> collisionLinesFromCurrentStone) {
		if (collisionLinesFromCurrentStone.stream().filter(Blockline.isState(BottomState.NAME)).count() > 0) {
			// Stop and create new Stone
			update();
		}
	}

	private void handleRightKey(Set<Blockline> collisionLinesFromCurrentStone, TetrisStone currentStone, int stoneSize) {
		if (collisionLinesFromCurrentStone.stream().filter(Blockline.isState(RightState.NAME)).count() == 0 && currentStone.getMaxX() <= this.dataModel.getConfig().getMaxWidthForStone()) {
			this.x.set(this.x.getValue() + stoneSize);
		}
	}

	private void handleLeftKey(Set<Blockline> collisionLinesFromCurrentStone, TetrisStone currentStone, int stoneSize) {
		if (collisionLinesFromCurrentStone.stream().filter(Blockline.isState(LeftState.NAME)).count() == 0 && currentStone.getMinX() >= (stoneSize - 1)) {
			this.x.set(this.x.getValue() - stoneSize);
		}
	}

	private boolean handleDownKey(TetrisStone currentStone, int stoneSize) {
		boolean result = false;
		if (currentStone.getMaxY() <= (this.bodyPane.getHeight() - stoneSize + 1)) {
			y.set(y.getValue() + stoneSize);
			if (currentStone.getMaxY() > (this.bodyPane.getHeight() - stoneSize + 1)) {
				result = true;
			}
		} else {
			result = true;
		}
		return result;
	}

	private void handleSpaceKey(List<TetrisStone> allStonesOnBoard, TetrisStone currentStone, int gameWidth) {
		// Rotation handling CW
		currentStone.handleRotation(true);
		// Check for intersections -> when intersection then undo rotation
		if (!CollisionHandler.getInstance().isRotationValid(allStonesOnBoard, currentStone, gameWidth)) {
			currentStone.handleRotation(false);
		}
	}

	@Override
	public void tick() {
		Optional<TetrisStone> currentStoneOpt = this.dataModel.getCurrentTetrisStone();
		if (currentStoneOpt.isPresent()) {
			if (handleDownKey(currentStoneOpt.get(), this.dataModel.getConfig().getBlockSize())) {
				update();
			} else {
				handleBottomCollision(CollisionHandler.getInstance().isCollisionDetected(this.dataModel.getAllTetrisStoneInGame(), currentStoneOpt.get()));
			}
		}
	}

}
