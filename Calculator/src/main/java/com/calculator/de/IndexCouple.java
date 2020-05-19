package com.calculator.de;

public class IndexCouple {

	private Integer startIndex;
	private Integer endIndex;

	public IndexCouple(Integer startIndex, Integer endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public boolean isIntersection(IndexCouple indexCouple) {
		boolean result = false;
		if((indexCouple.getStartIndex() > this.startIndex && indexCouple.getStartIndex() < this.endIndex)
				|| indexCouple.getEndIndex() > this.startIndex && indexCouple.getEndIndex() < this.endIndex) {
			result = true;
		}
		return result;
	}

	public boolean isBetweenIndex(int value) {
		return value > this.startIndex && value < this.endIndex;
	}

}
