package com.calculator.de;

public class ComplexFormular extends Formular_A {

	private Formular_I form1;
	private Formular_I form2;

	public ComplexFormular(Formular_I form1, Formular_I form2, Operation_I operation, IndexCouple indexCouple) {
		super(operation, indexCouple);
		this.form1 = form1;
		this.form2 = form2;
	}

	@Override
	public double calculate() {
		return operation.calculate(form1.calculate(), form2.calculate());
	}
	
	@Override
	public String toString() {
		return this.form1.toString() + "; " + form2.toString();
	}

}
