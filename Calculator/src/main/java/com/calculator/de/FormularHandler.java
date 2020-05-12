package com.calculator.de;

public class FormularHandler {

	private static FormularHandler instance;

	public static FormularHandler getInstance() {
		if (instance == null) {
			instance = new FormularHandler();
		}
		return instance;
	}

	private FormularHandler() {
	}

	public Formular_I createFormular(String formular) {
		return null;
	}

}
