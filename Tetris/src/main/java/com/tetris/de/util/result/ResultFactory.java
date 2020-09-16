package com.tetris.de.util.result;

public class ResultFactory {

	private ResultFactory() {
	}

	public static Result_I<Boolean> createBooleanResult() {
		return new BoolResult();
	}

}
