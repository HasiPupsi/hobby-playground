package com.calculator.de;

public abstract class Formular_A implements Formular_I {

	protected Operation_I operation;
	protected IndexCouple indexCouple;

	public Formular_A(Operation_I operation, IndexCouple indexCouple) {
		this.operation = operation;
		this.indexCouple = indexCouple;
	}

	public Operation_I getOperation() {
		return operation;
	}

	@Override
	public IndexCouple getIndexCouple() {
		return indexCouple;
	}

	@Override
	public int compareTo(Formular_I o) {
		int result = 0;
		if (o == null || o.getIndexCouple() == null || this.indexCouple.getStartIndex() > o.getIndexCouple().getStartIndex()) {
			result = 1;
		} else if (getIndexCouple() == null || this.indexCouple.getStartIndex() < o.getIndexCouple().getStartIndex()) {
			result = -1;
		}
		return result;
	}

}
