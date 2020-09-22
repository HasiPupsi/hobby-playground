package com.tetris.de;

import org.apache.log4j.Logger;

import com.tetris.de.constant.Const;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Starter extends javafx.application.Application {

	public static final Logger logger = Logger.getLogger(Starter.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		logger.info("#####################################");
		logger.info("Starte Tetris");
		logger.info("Erstelle Context und Beans...");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuLayout.fxml"));
		Pane root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setMinWidth(Const.MINWIDTH);
		primaryStage.setMinHeight(Const.MINHEIGHT);
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(scene);
		primaryStage.show();
		logger.info("#####################################");
	}

}
