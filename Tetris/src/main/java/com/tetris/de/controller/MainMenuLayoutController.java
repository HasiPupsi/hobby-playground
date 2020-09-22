package com.tetris.de.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tetris.de.constant.Const;
import com.tetris.de.model.GameDataModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuLayoutController {

	private Logger logger = Logger.getLogger(MainMenuLayoutController.class);

	@FXML
	private Button closeBtn;

	@FXML
	public void startButtonPushed() {
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/tetris/de/TetrisConfig.xml")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../GameLayout.fxml"));
			Pane root = loader.load();
			this.logger.info("Lade Spiellayout...");
			GameLayoutController gameController = loader.getController();
			gameController.init((GameDataModel) ctx.getBean("dataModel"));
			this.logger.info("Initialisierunge Gamecontroller...");

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
	public void optionenButtonPushed() {
	}

	@FXML
	public void beendenButtonPushed() {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();
	}

}
