package com.calculator.de;

import java.util.HashMap;
import java.util.Map;

public class Calculator {

	public enum OPERATOR {
		ADD, MINUS, MULTIPLY, DIVIDE;
	}

	private Map<OPERATOR, Operation_I> operationMap = new HashMap<OPERATOR, Operation_I>();

	private static Calculator instance;

	public static Calculator getInstance() {
		if (instance == null) {
			instance = new Calculator();
		}
		return instance;
	}

	private Calculator() {
		init();
	}

	private void init() {
		operationMap.put(OPERATOR.ADD, (a, b) -> a + b);
		operationMap.put(OPERATOR.MINUS, (a, b) -> a - b);
		operationMap.put(OPERATOR.MULTIPLY, (a, b) -> a * b);
		operationMap.put(OPERATOR.DIVIDE, (a, b) -> a / b);
	}

	public double calculate(double a, double b, OPERATOR operator) {
		return operationMap.get(operator).calculate(a, b);
	}

	public double calculate(Formular_I formular) {
		return formular.calculate();
	}

	public double calculate(String formular) {
		return FormularHandler.getInstance().createFormular(formular).calculate();
	}

	public Operation_I getOperation(OPERATOR operator) {
		return operationMap.get(operator);
	}

}
