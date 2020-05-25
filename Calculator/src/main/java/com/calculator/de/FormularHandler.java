package com.calculator.de;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calculator.de.Calculator.OPERATOR;

public class FormularHandler {

	private static FormularHandler instance;

	private static String currentFormular;
	private static Map<String, Formular_I> partialFormulars = new HashMap<String, Formular_I>();

	public static FormularHandler getInstance() {
		if (instance == null) {
			instance = new FormularHandler();
		}
		return instance;
	}

	private FormularHandler() {
	}

	public Formular_I parseFormular(String formular) {
		currentFormular = "(" + formular + ")";
		partialFormulars.clear();
		return newWay(0);
	}

	private Formular_I newWay(int startIndex) {
		Formular_I result = null;
		int length = currentFormular.length();
		int currentPosition = startIndex + 1;
		while (currentPosition < length) {
			if (currentFormular.charAt(currentPosition) == '(') {
				newWay(currentPosition);
				length = currentFormular.length();
				continue;
			} else if (currentFormular.charAt(currentPosition) == ')') {
				result = handlePartialFormular(currentFormular.substring(startIndex + 1, currentPosition), Calculator.getInstance().getOperators(true))
						.get(currentFormular.substring(startIndex + 1, currentPosition));
				int newNumber = 1;
				for (String key : partialFormulars.keySet()) {
					if (Integer.valueOf(key.substring(1)) >= newNumber) {
						newNumber = Integer.valueOf(key.substring(1)) + 1;
					}
				}
				currentFormular = currentFormular.replace(currentFormular.substring(startIndex, currentPosition + 1), "@" + newNumber);
				partialFormulars.put("@" + newNumber, result);
				break;
			} else {
				currentPosition++;
			}
		}
		return result;
	}

	private Map<String, Formular_I> handlePartialFormular(String partialFormular, List<OPERATOR> sortedOperators) {
		Map<String, Formular_I> resultMap = new HashMap<String, Formular_I>();
		OPERATOR operator = sortedOperators.get(0);
		sortedOperators.remove(0);
		String[] splitedFormularArray = partialFormular.split(operator.getRegexValue());
		Map<String, Formular_I> recursiveFormularMap = new HashMap<String, Formular_I>();
		for (int i = 0; i < splitedFormularArray.length; i++) {
			if (sortedOperators.size() > 0) {
				Map<String, Formular_I> partialMap = handlePartialFormular(splitedFormularArray[i], sortedOperators.stream().collect(Collectors.toList()));
				recursiveFormularMap.putAll(partialMap);
				for (String keyResult : partialMap.keySet()) {
					int newNumber = 1;
					for (String key : partialFormulars.keySet()) {
						if (Integer.valueOf(key.substring(1)) >= newNumber) {
							newNumber = Integer.valueOf(key.substring(1)) + 1;
						}
					}
					splitedFormularArray[i] = splitedFormularArray[i].replace(keyResult, "@" + newNumber);
					partialFormulars.put("@" + newNumber, partialMap.get(keyResult));
				}

			}
		}
		// Found Operator
		if (splitedFormularArray.length != 1) {
			Formular_I resultFormular = null;
			String lastValue = null;
			for (String currentValue : splitedFormularArray) {
				Formular_I currentFormular = null;
				if (partialFormulars.containsKey(currentValue)) {
					// TODO @ bleibt bestehen, muss aber entfernt werden, nachdem es verwendet
					// wurde.
					currentFormular = partialFormulars.get(currentValue);
					partialFormulars.remove(currentValue);
				} else if (recursiveFormularMap.containsKey(currentValue)) {
					currentFormular = recursiveFormularMap.get(currentValue);
				}
				if (resultFormular == null && lastValue != null && currentFormular == null) {
					// Elementalformular
					resultFormular = FormularFactory.createElementalFormular(Double.valueOf(lastValue), Double.valueOf(currentValue), Calculator.getInstance().getOperation(operator));
				} else if (resultFormular == null && lastValue != null && currentFormular != null) {
					// Mixedformular (double, Formular_I)
					resultFormular = FormularFactory.createMixedFormular(currentFormular, Double.valueOf(lastValue), Calculator.getInstance().getOperation(operator), false);
				} else if (resultFormular != null && currentFormular == null) {
					// Mixedformular (Formular_I, double)
					resultFormular = FormularFactory.createMixedFormular(resultFormular, Double.valueOf(currentValue), Calculator.getInstance().getOperation(operator));
				} else if (resultFormular != null && currentFormular != null) {
					// Complexformular
					resultFormular = FormularFactory.createComplexFormular(resultFormular, currentFormular, Calculator.getInstance().getOperation(operator));
				} else if (resultFormular == null && currentFormular != null) {
					resultFormular = currentFormular;
				}
				lastValue = currentValue;
			}
			if (resultFormular != null) {
				resultMap.put(partialFormular, resultFormular);
			} else {

			}
		} else if (resultMap.isEmpty()) {
			resultMap.putAll(recursiveFormularMap);
		}
		return resultMap;
	}

}
