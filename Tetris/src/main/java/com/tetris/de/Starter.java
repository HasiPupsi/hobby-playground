package com.tetris.de;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tetris.de.constant.Const;
import com.tetris.de.controller.GameLayoutController;
import com.tetris.de.controller.MainMenuLayoutController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/tetris/de/TetrisConfig.xml")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuLayout.fxml"));
			Pane root = loader.load();

			DataModel dataModel = ctx.getBean(DataModel.class);
			MainMenuLayoutController controller = loader.getController();
			controller.initModel(dataModel);

			loader = new FXMLLoader(getClass().getResource("GameLayout.fxml"));
			root = loader.load();
			logger.info("Lade Spiellayout...");
			GameLayoutController gameController = loader.getController();
			gameController.init(dataModel);
			logger.info("Initialisierunge Gamecontroller...");

			Scene scene = new Scene(root);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, gameController);
			primaryStage.setMinWidth(Const.MINWIDTH);
			primaryStage.setMinHeight(Const.MINHEIGHT);
			primaryStage.setTitle("Hello World");
			primaryStage.setScene(scene);
			primaryStage.show();
			logger.info("Spiel erfolgreich gestartet.");
			logger.info("#####################################");
		}

	}

}
