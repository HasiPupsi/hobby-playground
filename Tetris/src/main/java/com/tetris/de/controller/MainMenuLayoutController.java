package com.tetris.de.controller;

import com.tetris.de.DataModel;

import javafx.fxml.FXML;

public class MainMenuLayoutController {

	private DataModel dataModel;

	public MainMenuLayoutController() {
	}

	public void initModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	@FXML
	public void startButtonPushed() {
		System.out.println("Start");
	}

	@FXML
	public void optionenButtonPushed() {
		System.out.println("optionen");
	}

	@FXML
	public void beendenButtonPushed() {
		System.out.println("Beenden");
	}

}
