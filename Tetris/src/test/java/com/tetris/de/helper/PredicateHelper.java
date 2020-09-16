package com.tetris.de.helper;

import java.util.Arrays;
import java.util.function.Predicate;

import com.tetris.de.Block_I;

public class PredicateHelper {

	public static Predicate<Block_I[]> isBlockInLine = blockArray -> {
		long count = Arrays.stream(blockArray).filter(block -> {
			return block != null;
		}).count();
		return count != 0;
	};

}
