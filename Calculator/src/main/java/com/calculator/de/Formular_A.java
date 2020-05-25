package com.calculator.de;

public abstract class Formular_A implements Formular_I {

	protected Operation_I operation;

	public Formular_A(Operation_I operation) {
		this.operation = operation;
	}

	public Operation_I getOperation() {
		return operation;
	}

}
