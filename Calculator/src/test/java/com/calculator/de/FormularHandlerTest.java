package com.calculator.de;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.calculator.de.Calculator.OPERATOR;

public class FormularHandlerTest {

	@Test
	public void basicFormularTests() {
		FormularHandler fhandler = FormularHandler.getInstance();
		Formular_I actual;
		Formular_I expected;

		// ADD
		actual = fhandler.parseFormular("3+5");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.ADD));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3+5+10+12");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.ADD));
		expected = FormularFactory.createMixedFormular(expected, 10, Calculator.getInstance().getOperation(OPERATOR.ADD));
		expected = FormularFactory.createMixedFormular(expected, 12, Calculator.getInstance().getOperation(OPERATOR.ADD));
		assertTrue(actual instanceof MixedFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3.5+5.2");
		expected = FormularFactory.createElementalFormular(3.5, 5.2, Calculator.getInstance().getOperation(OPERATOR.ADD));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		// MINUS
		actual = fhandler.parseFormular("3-5");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.MINUS));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3-5-10-12");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.MINUS));
		expected = FormularFactory.createMixedFormular(expected, 10, Calculator.getInstance().getOperation(OPERATOR.MINUS));
		expected = FormularFactory.createMixedFormular(expected, 12, Calculator.getInstance().getOperation(OPERATOR.MINUS));
		assertTrue(actual instanceof MixedFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3.5-5.2");
		expected = FormularFactory.createElementalFormular(3.5, 5.2, Calculator.getInstance().getOperation(OPERATOR.MINUS));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		// MULTIPLY
		actual = fhandler.parseFormular("3*5");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.MULTIPLY));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3*5*10*12");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.MULTIPLY));
		expected = FormularFactory.createMixedFormular(expected, 10, Calculator.getInstance().getOperation(OPERATOR.MULTIPLY));
		expected = FormularFactory.createMixedFormular(expected, 12, Calculator.getInstance().getOperation(OPERATOR.MULTIPLY));
		assertTrue(actual instanceof MixedFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3.5*5.2");
		expected = FormularFactory.createElementalFormular(3.5, 5.2, Calculator.getInstance().getOperation(OPERATOR.MULTIPLY));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		// DIVIDE
		actual = fhandler.parseFormular("3/5");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.DIVIDE));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3/5/10/12");
		expected = FormularFactory.createElementalFormular(3, 5, Calculator.getInstance().getOperation(OPERATOR.DIVIDE));
		expected = FormularFactory.createMixedFormular(expected, 10, Calculator.getInstance().getOperation(OPERATOR.DIVIDE));
		expected = FormularFactory.createMixedFormular(expected, 12, Calculator.getInstance().getOperation(OPERATOR.DIVIDE));
		assertTrue(actual instanceof MixedFormular);
		assertEquals(expected.calculate(), actual.calculate());

		actual = fhandler.parseFormular("3.5/5.2");
		expected = FormularFactory.createElementalFormular(3.5, 5.2, Calculator.getInstance().getOperation(OPERATOR.DIVIDE));
		assertTrue(actual instanceof ElementalFormular);
		assertEquals(expected.calculate(), actual.calculate());
	}

	@Test
	public void paranthesesTest() {
		FormularHandler fhandler = FormularHandler.getInstance();
		Formular_I actual;
		Formular_I expected;

	}

}
