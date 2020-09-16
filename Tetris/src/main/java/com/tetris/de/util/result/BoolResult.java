package com.tetris.de.util.result;

import java.util.Optional;

public class BoolResult implements Result_I<Boolean> {

	private Optional<Boolean> result = Optional.empty();

	public BoolResult() {
		this(false);
	}

	public BoolResult(Boolean result) {
		this.result = Optional.of(result);
	}

	@Override
	public void setResult(Boolean result) {
		this.result = Optional.of(result);
	}

	@Override
	public Boolean getResult() {
		return result.orElse(false);
	}

}
