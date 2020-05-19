package com.calculator.de;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.calculator.de.Calculator.OPERATOR;

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

	public static void main(String[] args) {
		System.out.println("Formel: 1+(2-3)");
		LinkedList<String> linked = new LinkedList<String>();
		Formular_I result = FormularHandler.stackWay("1+(2-3)");
		System.out.println(result.toString());
		System.out.println(Calculator.getInstance().calculate(result));
//		System.out.println("Formel: a + b - c");
//		FormularHandler.splitFormulars("a + b - c", 0, linked);
//		linked.forEach(value -> System.out.println(value));
//		linked.clear();
//		System.out.println("Formel: a + (b - (c + d))");
//		FormularHandler.splitFormulars("a + (b - (c + d))", 0, linked);
//		linked.forEach(value -> System.out.println(value));
//		linked.clear();
//		System.out.println("Formel: ((a + b) - c) + d");
//		FormularHandler.splitFormulars("((a + b) - c) + d", 0, linked);
//		linked.forEach(value -> System.out.println(value));
//		linked.clear();
//		System.out.println("Formel: (a + b) - c");
//		FormularHandler.splitFormulars("(a + b) - c", 0, linked);
//		linked.forEach(value -> System.out.println(value));
//		linked.clear();
	}

//	public static Formular_I getFormular(String formular) {
//		LinkedList<String> formularList = new LinkedList<String>();
//		splitFormulars(formular, 0, formularList);
//		formularList.removeLast();
//		// Formular-Elemente draus erstellen (von hinten nach vorn)
//		return null;
//	}

	// FIXME umschreiben, so das die ganze Formel am Ende nicht mit in der Liste ist
//	private static int splitFormulars(String formular, int index, LinkedList<String> formularList) {
//		int firstParenthesisIndex = formular.indexOf("(");
//		int lastParenthesisIndex = formular.lastIndexOf(")");
//		if (firstParenthesisIndex != -1 && lastParenthesisIndex != -1) {
//			if (firstParenthesisIndex != 0) {
//				index = splitFormulars(formular.substring(0, firstParenthesisIndex), index, formularList);
//			}
//			index = splitFormulars(formular.substring(firstParenthesisIndex + 1, lastParenthesisIndex), index, formularList);
//			if ((lastParenthesisIndex + 1) <= formular.length()) {
//				index = splitFormulars(formular.substring(lastParenthesisIndex + 1), index, formularList);
//			}
//		}
//		formularList.add(index++, formular);
//		return index;
//	}

	private static Formular_I stackWay(String formular) {
		List<Formular_I> formularList = new ArrayList<Formular_I>();
		Stack<Integer> parenthesisOpen = new Stack<Integer>();
		for (int i = 0; i < formular.length(); i++) {
			if (formular.charAt(i) == '(') {
				parenthesisOpen.push(i);
			} else if (formular.charAt(i) == ')') {
				IndexCouple indexCouple = new IndexCouple(parenthesisOpen.pop(), i);
				createFormular(formular, indexCouple, formularList);
			}
		}

		// All Parenthesis processed -> Last call to process the whole formular
		createFormular(formular, new IndexCouple(0, formular.length() - 1), formularList);
		if (formularList.size() > 1 || formularList.size() == 0) {
			StringBuilder builder = new StringBuilder();
			formularList.forEach(form -> builder.append(form.toString() + "\n"));
			throw new IllegalArgumentException("Etwas lief schief:\n" + builder.toString());
		}
		return formularList.get(0);
	}

	private static void createFormular(String formular, IndexCouple indexCouple, List<Formular_I> formularList) {
		List<Formular_I> intersectedFormulars = formularList.stream().filter(form -> indexCouple.isIntersection(form.getIndexCouple())).collect(Collectors.toList());
		formularList.removeAll(intersectedFormulars);
		for (int i = indexCouple.getStartIndex(); i < indexCouple.getEndIndex(); i++) {
			if ((formular.charAt(i) == OPERATOR.MULTIPLY.getValue() && !isOperatorUsed(OPERATOR.MULTIPLY, i, formularList))
					|| (formular.charAt(i) == OPERATOR.DIVIDE.getValue() && !isOperatorUsed(OPERATOR.DIVIDE, i, formularList))) {
				Formular_I o1 = null, o2 = null;
				for (Formular_I intersectedFormular : intersectedFormulars) {
					if (intersectedFormular.getIndexCouple().getEndIndex() == i - 1) {
						o1 = intersectedFormular;
					} else if (intersectedFormular.getIndexCouple().getStartIndex() == i + 1) {
						o2 = intersectedFormular;
					}
				}
				Formular_I newFormular = null;
				if (o1 == null && o2 != null) {
					int j = i;
					while (true) {
						j--;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							newFormular = FormularFactory.createMixedFormular(Double.valueOf(formular.substring(j + 1, i -1)), o2, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(j + 1, o2.getIndexCouple().getEndIndex()));
							break;
						}
					}
				} else if (o2 == null && o1 != null) {
					int j = i;
					while (true) {
						j++;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							newFormular = FormularFactory.createMixedFormular(Double.valueOf(formular.substring(i + 1, j - 1)), o2, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(o1.getIndexCouple().getStartIndex(), j - 1));
						}
						break;
					}
				} else if (o1 == null && o2 == null) {
					int j = i;
					int k = i;
					while (true) {
						j--;
						k++;
						Double leftValue = null, rightValue = null;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							leftValue = Double.valueOf(formular.substring(j + 1, i -1));
						}
						if (formular.charAt(k) == OPERATOR.ADD.getValue() || formular.charAt(k) == OPERATOR.MINUS.getValue() || formular.charAt(k) == OPERATOR.MULTIPLY.getValue() || formular.charAt(k) == OPERATOR.DIVIDE.getValue()) {
							rightValue = Double.valueOf(formular.substring(i + 1, k -1));
						}
						if(j == indexCouple.getStartIndex()) {
							leftValue = formular.charAt(j) == '(' ? Double.valueOf(formular.substring(j + 1, i -1)) : Double.valueOf(formular.substring(j, i -1));
						}
						if(k == indexCouple.getEndIndex()) {
							rightValue =  formular.charAt(k) == ')' ? Double.valueOf(formular.substring(i + 1, k -1)) : Double.valueOf(formular.substring(i +1, k));
						}
						if(leftValue != null && rightValue != null) {
							newFormular = FormularFactory.createElementalFormular(leftValue, rightValue, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(j + 1, k - 1));
							break;
						}
					}
				} else {
					newFormular = new ComplexFormular(o1, o2, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(o1.getIndexCouple().getStartIndex(), o2.getIndexCouple().getEndIndex()));
				}
				intersectedFormulars.remove(o1);
				intersectedFormulars.remove(o2);
				intersectedFormulars.add(newFormular);
				Collections.sort(intersectedFormulars);
			}
		}
		
		for (int i = indexCouple.getStartIndex(); i < indexCouple.getEndIndex(); i++) {
			if ((formular.charAt(i) == OPERATOR.ADD.getValue() && !isOperatorUsed(OPERATOR.ADD, i, formularList))
					|| (formular.charAt(i) == OPERATOR.MINUS.getValue() && !isOperatorUsed(OPERATOR.MINUS, i, formularList))) {
				Formular_I o1 = null, o2 = null;
				for (Formular_I intersectedFormular : intersectedFormulars) {
					if (intersectedFormular.getIndexCouple().getEndIndex() == i - 1) {
						o1 = intersectedFormular;
					} else if (intersectedFormular.getIndexCouple().getStartIndex() == i + 1) {
						o2 = intersectedFormular;
					}
				}
				Formular_I newFormular = null;
				if (o1 == null && o2 != null) {
					int j = i;
					while (true) {
						j--;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							newFormular = FormularFactory.createMixedFormular(o2, Double.valueOf(formular.substring(j, i)), Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), false, new IndexCouple(j, o2.getIndexCouple().getEndIndex()));
							break;
						}
						if(j == indexCouple.getStartIndex()) {
							newFormular = FormularFactory.createMixedFormular(o2, Double.valueOf(formular.substring(j, i)),  Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), false, new IndexCouple(j, o2.getIndexCouple().getEndIndex()));
							break;
						}
					}
				} else if (o2 == null && o1 != null) {
					int j = i;
					while (true) {
						j++;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							newFormular = FormularFactory.createMixedFormular(Double.valueOf(formular.substring(i, j)), o2, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(o1.getIndexCouple().getStartIndex(), j - 1));
						}
						break;
					}
				} else if (o1 == null && o2 == null) {
					int j = i;
					int k = i;
					Double leftValue = null, rightValue = null;
					while (true) {
						j--;
						k++;
						if (formular.charAt(j) == OPERATOR.ADD.getValue() || formular.charAt(j) == OPERATOR.MINUS.getValue() || formular.charAt(j) == OPERATOR.MULTIPLY.getValue() || formular.charAt(j) == OPERATOR.DIVIDE.getValue()) {
							leftValue = Double.valueOf(formular.substring(j + 1, i -1));
						}
						if (formular.charAt(k) == OPERATOR.ADD.getValue() || formular.charAt(k) == OPERATOR.MINUS.getValue() || formular.charAt(k) == OPERATOR.MULTIPLY.getValue() || formular.charAt(k) == OPERATOR.DIVIDE.getValue()) {
							rightValue = Double.valueOf(formular.substring(i + 1, k -1));
						}
						if(j == indexCouple.getStartIndex()) {
							leftValue = formular.charAt(j) == '(' ? Double.valueOf(formular.substring(j + 1, i)) : Double.valueOf(formular.substring(j, i));
						}
						if(k == indexCouple.getEndIndex()) {
							rightValue =  formular.charAt(k) == ')' ? Double.valueOf(formular.substring(i + 1, k)) : Double.valueOf(formular.substring(i +1, k));
						}
						if(leftValue != null && rightValue != null) {
							newFormular = FormularFactory.createElementalFormular(leftValue, rightValue, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(j, k));
							break;
						}
					}
				} else {
					newFormular = new ComplexFormular(o1, o2, Calculator.getInstance().getOperation(OPERATOR.getOperator(formular.charAt(i))), new IndexCouple(o1.getIndexCouple().getStartIndex(), o2.getIndexCouple().getEndIndex()));
				}
				intersectedFormulars.remove(o1);
				intersectedFormulars.remove(o2);
				intersectedFormulars.add(newFormular);
				i = newFormular.getIndexCouple().getEndIndex();
				Collections.sort(intersectedFormulars);
			}
		}
		formularList.addAll(intersectedFormulars);
		Collections.sort(formularList);
	}

	private static boolean isOperatorUsed(OPERATOR operator, int index, List<Formular_I> formularList) {
		boolean result = false;
		for (Formular_I formular : formularList) {
			if (formular.getIndexCouple().isBetweenIndex(index)) {
				result = true;
			}
		}
		return result;
	}

}
