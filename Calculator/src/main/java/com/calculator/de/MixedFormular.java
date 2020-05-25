package com.calculator.de;

public class MixedFormular extends Formular_A {

	private boolean firstElementFormular = true;
	private double primitivElement;
	private Formular_I formularElement;

	public MixedFormular(double primitivElement, Formular_I formularElement, Operation_I operation) {
		this(primitivElement, formularElement, operation, true);
	}

	public MixedFormular(double primitivElement, Formular_I formularElement, Operation_I operation, boolean firstElementFormular) {
		super(operation);
		this.primitivElement = primitivElement;
		this.formularElement = formularElement;
		this.firstElementFormular = firstElementFormular;
	}

	@Override
	public double calculate() {
		return firstElementFormular ? operation.calculate(formularElement.calculate(), primitivElement) : operation.calculate(primitivElement, formularElement.calculate());
	}
	
	@Override
	public String toString() {
		return this.primitivElement + "; " + this.formularElement.toString();
	}

}
