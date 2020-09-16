package com.tetris.de;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Block implements Block_I {

	private List<BlockListener_I> listeners = new ArrayList<>();
	private List<Blockline> blockLineList = new ArrayList<>();
	private Polygon polygon;
	private String color = "FF0000";

	public Block(List<Blockline> blockLineList) {
		this("FFFF00", blockLineList);
	}

	public Block(String color, List<Blockline> blockLineList) {
		this.color = color;
		this.blockLineList = blockLineList;
		init();
	}

	private void init() {
		List<Double> pointList = new ArrayList<>();
		this.blockLineList.forEach(blockline -> pointList.addAll(blockline.getPoints()));
		double[] pointArray = new double[pointList.size()];
		for (int i = 0; i < pointList.size(); i++) {
			pointArray[i] = pointList.get(i);
		}
		polygon = new Polygon(pointArray);
		polygon.setStroke(Color.BLACK);
		polygon.setFill(Color.valueOf(this.color));
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public List<Blockline> getBlockLineList() {
		return blockLineList;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	/**
	 * 
	 * @return index of rotation transformation or -1 if there is no
	 */
	private int indexOfFirstRotation() {
		int result = -1;
		Object[] rotationTransform = polygon.getTransforms().stream().filter(transform -> transform instanceof Rotate).toArray();
		if (rotationTransform.length > 0) {
			result = polygon.getTransforms().indexOf(rotationTransform[0]);
		}
		return result;
	}

	@Override
	public void translateBlock(Translate translate) {
		int index = indexOfFirstRotation();
		if (index != -1) {
			polygon.getTransforms().add(index, translate);
			blockLineList.forEach(blockLine -> {
				blockLine.getTransforms().add(index, translate);
			});
		} else {
			polygon.getTransforms().add(translate);
			blockLineList.forEach(blockLine -> blockLine.getTransforms().add(translate));
		}
	}

	@Override
	public void handleRotation(boolean cw, Transform rotationTransform) {
		this.polygon.getTransforms().add(rotationTransform);
		this.blockLineList.forEach(blockline -> blockline.handleRotation(cw, rotationTransform));
	}

	@Override
	public void addBlockListener(BlockListener_I blockListener) {
		this.listeners.add(blockListener);
	}

	@Override
	public void notifyBlockListener() {
		listeners.forEach(blockListener -> {
			blockListener.destroyBlock(this);
		});
	}

}
