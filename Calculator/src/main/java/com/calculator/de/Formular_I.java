package com.calculator.de;

public interface Formular_I extends Comparable<Formular_I>{

	public enum FORMULAR_TYPE {
		ELEMENTAL, MIXED, COMPLEX
	}

	public IndexCouple getIndexCouple();

	public double calculate();

}
