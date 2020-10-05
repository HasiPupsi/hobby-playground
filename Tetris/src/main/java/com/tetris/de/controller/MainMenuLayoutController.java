package com.tetris.de.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tetris.de.constant.Const;
import com.tetris.de.model.GameDataModel;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuLayoutController {

	private Logger logger = Logger.getLogger(MainMenuLayoutController.class);

	private FadeTransition focusTransition;
	private InvalidationListener focusListener = value -> {
		if (value instanceof ReadOnlyBooleanProperty) {
			ReadOnlyBooleanProperty boolProperty = (ReadOnlyBooleanProperty) value;
			if (boolProperty.getValue().equals(true)) {
				focusTransition.setNode((Button) boolProperty.getBean());
				focusTransition.play();
			} else {
				focusTransition.stop();
				// Back to original state
				((Button) boolProperty.getBean()).setOpacity(1);
			}
		}
	};

	@FXML
	private Pane root;

	@FXML
	private Button startBtn;

	@FXML
	private Button optionBtn;

	@FXML
	private Button closeBtn;

	public void init() {
		// Focusanimationen
		focusTransition = new FadeTransition(Duration.millis(1000));
		focusTransition.setNode(startBtn);
		focusTransition.setFromValue(1);
		focusTransition.setToValue(0.6);
		focusTransition.setCycleCount(-1);
		focusTransition.setAutoReverse(true);
		focusTransition.play();
		startBtn.requestFocus();

		// Focuslistener hinzufügen
		startBtn.focusedProperty().addListener(this.focusListener);
		optionBtn.focusedProperty().addListener(this.focusListener);
		closeBtn.focusedProperty().addListener(this.focusListener);
	}

	@FXML
	public void startButtonPushed() {
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/tetris/de/TetrisConfig.xml")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../GameLayout.fxml"));
			Pane root = loader.load();
			this.logger.info("Lade Spiellayout...");
			GameLayoutController gameController = loader.getController();
			gameController.init((GameDataModel) ctx.getBean("dataModel"));
			this.logger.info("Initialisiere Gamecontroller...");

			Scene scene = new Scene(root);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, gameController);
			Stage gameStage = (Stage) closeBtn.getScene().getWindow();
			gameStage.setMinWidth(Const.MINWIDTH);
			gameStage.setMinHeight(Const.MINHEIGHT);
			gameStage.setTitle("Hello World");
			gameStage.setScene(scene);
			gameStage.show();
			this.logger.info("Spiel erfolgreich gestartet.");
			this.logger.info("#####################################");
		} catch (IOException ex) {
			this.logger.error("Fehler beim initialisieren des Spiels.", ex);
		}
	}

	@FXML
	public void mouseEnteredStart() {
		startBtn.requestFocus();
	}

	@FXML
	public void mouseEnteredOption() {
		optionBtn.requestFocus();
	}

	@FXML
	public void mouseEnteredClose() {
		closeBtn.requestFocus();
	}

	@FXML
	public void mouseExited() {
		root.requestFocus();
	}

	@FXML
	public void optionenButtonPushed() {
	}

	@FXML
	public void beendenButtonPushed() {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();
	}

}
