package com.calculator.de;

public class MixedFormular extends Formular_A {

	private double element1;
	private Formular_I element2;
	
	public MixedFormular(double element1, Formular_I element2, Operation_I operation) {
		super(operation);
		this.element1 = element1;
		this.element2 = element2;
	}
	
	@Override
	public double calculate() {
		return operation.calculate(element1, element2.calculate());
	}

}
