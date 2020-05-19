package com.calculator.de;

public class ElementalFormular extends Formular_A {

	private double element1;
	private double element2;

	public ElementalFormular(double o1, double o2, Operation_I operation, IndexCouple indexCouple) {
		super(operation, indexCouple);
		this.element1 = o1;
		this.element2 = o2;
	}

	@Override
	public double calculate() {
		return operation.calculate(element1, element2);
	}
	
	@Override
	public String toString() {
		return this.element1 + "; " + this.element2;
	}

}
