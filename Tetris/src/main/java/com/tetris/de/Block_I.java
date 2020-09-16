package com.tetris.de;

import java.util.List;

import javafx.scene.shape.Polygon;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public interface Block_I {

	String getColor();

	void setColor(String color);

	public List<Blockline> getBlockLineList();

	public Polygon getPolygon();

	public void translateBlock(Translate translate);

	public void handleRotation(boolean cw, Transform rotationTransform);

	public void addBlockListener(BlockListener_I blockListener);
	
	public void notifyBlockListener();

}
