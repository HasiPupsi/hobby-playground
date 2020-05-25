package com.calculator.de;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Calculator {

	public enum OPERATOR {
		ADD('+', "\\+", 1), MINUS('-', "-", 1), MULTIPLY('*', "\\*", 2), DIVIDE('/', "/", 2);

		private char value;
		private String regexValue;
		private Integer priority;

		private OPERATOR(char value, String regexValue, int priority) {
			this.value = value;
			this.regexValue = regexValue;
			this.priority = priority;
		}

		public char getValue() {
			return value;
		}

		public String getRegexValue() {
			return regexValue;
		}

		public int getPriority() {
			return priority;
		}

		public static OPERATOR getOperator(char value) {
			OPERATOR result = null;
			for (OPERATOR operator : values()) {
				if (operator.getValue() == value) {
					result = operator;
					break;
				}
			}
			return result;
		}
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

	public List<OPERATOR> getOperators(boolean sortedPriority) {
		List<OPERATOR> result = null;
		if (sortedPriority) {
			result = new LinkedList<Calculator.OPERATOR>(Arrays.asList(OPERATOR.values()));
			result.sort((OPERATOR o1, OPERATOR o2) -> o1.priority.compareTo(o2.priority));
		} else {
			result = new LinkedList<Calculator.OPERATOR>(Arrays.asList(OPERATOR.values()));
		}
		return result;
	}

}
