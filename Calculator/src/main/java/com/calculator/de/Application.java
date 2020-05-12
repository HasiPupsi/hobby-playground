package com.calculator.de;

import com.calculator.de.Calculator.OPERATOR;

public class Application {

	public static void main(String[] args) {
		Calculator calc = Calculator.getInstance();
		ElementalFormular eleFormular1 = new ElementalFormular(10, 20, calc.getOperation(OPERATOR.ADD));
		System.out.println(calc.calculate(eleFormular1));
		ElementalFormular eleFormular2 = new ElementalFormular(20, 10, calc.getOperation(OPERATOR.MINUS));
		System.out.println(calc.calculate(eleFormular2));
		ComplexFormular complexFormular = new ComplexFormular(eleFormular1, eleFormular2,
				calc.getOperation(OPERATOR.DIVIDE));
		System.out.println(calc.calculate(complexFormular));
	}

}
