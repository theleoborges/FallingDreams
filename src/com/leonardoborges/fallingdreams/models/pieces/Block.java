package com.leonardoborges.fallingdreams.models.pieces;

import java.awt.Color;
import java.awt.Rectangle;

public class Block extends Rectangle {

	private Color color;

	public Block(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
