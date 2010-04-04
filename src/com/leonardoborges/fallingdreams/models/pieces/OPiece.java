package com.leonardoborges.fallingdreams.models.pieces;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.leonardoborges.fallingdreams.models.Matrix;

public class OPiece extends AbstractPiece {

	public OPiece(Matrix matrix) {
		super(matrix);
		Rectangle rect1 = new Block(getStartPosition().x - BLOCK_SIDE_HEIGHT, getStartPosition().y, BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, Color.green);
		Rectangle rect2 = new Block(getStartPosition().x - BLOCK_SIDE_HEIGHT, getStartPosition().y + BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, Color.green);
		Rectangle rect3 = new Block(getStartPosition().x,  getStartPosition().y, BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, Color.green);
		Rectangle rect4 = new Block(getStartPosition().x, getStartPosition().y + BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, BLOCK_SIDE_HEIGHT, Color.green);
		blocks = new Rectangle[] {rect1, rect2, rect3, rect4};
	}
	
	@Override
	public void rotateClockWise() {
		// this piece does not rotate
	}
	
	@Override
	protected Point getPieceCenter() {
		return new Point(0, 0); //it doesn't matter since it doesn't rotate
	}	
}
