package com.tetris.de;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tetris.de.helper.PredicateHelper;

import javafx.scene.transform.Translate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("TetrisConfig.xml")
public class FieldGridTest {

	@Autowired
	private FieldGrid fieldGrid;

	@Autowired
	private List<TetrisStone> tetrisStoneList;

	@Test
	public void testExistFullLine() {
		TetrisStone tetrisStone = tetrisStoneList.get(0);
		Block_I block = tetrisStone.getBlockList().get(0);
		fillLine(19, block);
		assertEquals(19, this.fieldGrid.existFullLine());
	}

	@Test
	public void testRemoveLine() {
		TetrisStone tetrisStone = tetrisStoneList.get(0);
		Block_I block = tetrisStone.getBlockList().get(0);
		Translate translate = new Translate(0, 0);
		fillLine(19, block);
		fillLine(18, block);
		this.fieldGrid.removeLine(19, translate);
		assertEquals(19, this.fieldGrid.existFullLine());
		this.fieldGrid.removeLine(19, translate);
		assertEquals(-1, this.fieldGrid.existFullLine());
	}

	@Test
	public void testRemoveLine2() {
		TetrisStone tetrisStone = tetrisStoneList.get(0);
		Block_I block = tetrisStone.getBlockList().get(0);
		Translate translate = new Translate(0, 0);
		fillLine(19, block);
		fillLineAtPosition(18, 0, block);
		fillLineAtPosition(18, 1, block);
		fillLineAtPosition(18, 3, block);
		fillLineAtPosition(18, 4, block);
		fillLine(17, block);
		fillLineAtPosition(16, 5, block);
		fillLineAtPosition(16, 8, block);
		this.fieldGrid.removeLine(19, translate);
		assertEquals(18, this.fieldGrid.existFullLine());
		this.fieldGrid.removeLine(18, translate);
		assertEquals(-1, this.fieldGrid.existFullLine());
		Block_I[][] blockArray = this.fieldGrid.getBlockArray();
		assertTrue(PredicateHelper.isBlockInLine.test(blockArray[19]));
		assertTrue(PredicateHelper.isBlockInLine.test(blockArray[18]));
		assertFalse(PredicateHelper.isBlockInLine.test(blockArray[17]));
		assertFalse(PredicateHelper.isBlockInLine.test(blockArray[17]));
		assertNotNull(blockArray[19][0]);
		assertNotNull(blockArray[19][1]);
		assertNull(blockArray[19][2]);
		assertNotNull(blockArray[19][3]);
		assertNotNull(blockArray[19][4]);
		assertNull(blockArray[19][5]);
		assertNull(blockArray[19][6]);
		assertNull(blockArray[19][7]);
		assertNull(blockArray[19][8]);
		assertNull(blockArray[19][9]);

		assertNull(blockArray[18][0]);
		assertNull(blockArray[18][1]);
		assertNull(blockArray[18][2]);
		assertNull(blockArray[18][3]);
		assertNull(blockArray[18][4]);
		assertNotNull(blockArray[18][5]);
		assertNull(blockArray[18][6]);
		assertNull(blockArray[18][7]);
		assertNotNull(blockArray[18][8]);
		assertNull(blockArray[18][9]);
	}

	private void fillLine(int lineIndex, Block_I block) {
		IntStream.range(0, this.fieldGrid.getVerticalLines().size() + 1).forEach(i -> {
			this.fieldGrid.getBlockArray()[lineIndex][i] = copyBlock(block);
		});
	}

	private void fillLineAtPosition(int lineIndex, int blockIndex, Block_I block) {
		this.fieldGrid.getBlockArray()[lineIndex][blockIndex] = block;
	}

	private Block_I copyBlock(Block_I block) {
		return new Block(block.getBlockLineList());
	}

}
