package com.calculator.de;

public class FormularFactory {

	private FormularFactory() {
	}

	public static Formular_I createElementalFormular(double o1, double o2, Operation_I operation) {
		return new ElementalFormular(o1, o2, operation);
	}

	public static Formular_I createMixedFormular(double o1, Formular_I o2, Operation_I operation) {
		return new MixedFormular(o1, o2, operation);
	}

	public static Formular_I createMixedFormular(Formular_I o1, double o2, Operation_I operation) {
		return new MixedFormular(o2, o1, operation);
	}

	public static Formular_I createMixedFormular(Formular_I o1, double o2, Operation_I operation, boolean firstParameterFormular) {
		return new MixedFormular(o2, o1, operation, firstParameterFormular);
	}

	public static Formular_I createComplexFormular(Formular_I o1, Formular_I o2, Operation_I operation) {
		return new ComplexFormular(o1, o2, operation);
	}

}
