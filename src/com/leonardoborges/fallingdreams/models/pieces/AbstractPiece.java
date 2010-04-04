package com.leonardoborges.fallingdreams.models.pieces;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.leonardoborges.fallingdreams.models.Matrix;

public abstract class AbstractPiece {
	
	public static final int MOVE_OFFSET = 20;
	protected static final int BLOCK_SIDE_HEIGHT = 20;
	protected Rectangle[] blocks;
	protected Matrix matrix;

	private Point startPosition;
	private Point center;

	public AbstractPiece(Matrix matrix) {
		this.matrix = matrix;
		int x = (this.matrix.MAX_COLUMNS * BLOCK_SIDE_HEIGHT) / 2;
		this.startPosition = new Point(x, 0); 
		this.center = getPieceCenter();
	}

	protected abstract Point getPieceCenter();

	public Point getStartPosition() {
		return this.startPosition;
	}

	public Rectangle[] getBlocks() {
		return blocks;
	}

	public void moveDown() {
		if (canMoveDown()) {
			moveBy(0, MOVE_OFFSET);
		}
	}
	
	private boolean canMoveDown() {
		for (Rectangle block : this.blocks) {
			int row = (block.y / 20) + 1;
			int column = (block.x / 20);
			if (withinBoundsAndNoCollision(row, column)) {
				matrix.landPiece(this);
				matrix.spawnNewPiece();
				return false;
			}
		}
		return true;
	}
	public void moveRight() {
		if (canMoveRight()) {
			moveBy(MOVE_OFFSET, 0);			
		}
	}

	private boolean canMoveRight() {
		boolean canMove = true;
		for (Rectangle block : this.blocks) {
				int row = block.y / 20;
				int column = (block.x / 20) + 1;
				if (withinBoundsAndNoCollision(row, column)) {
					return false;
				}							
		}
		return canMove;
	}

	public void moveLeft() {
		if (canMoveLeft()) {
			moveBy(-MOVE_OFFSET, 0);	
		}
	}

	private boolean canMoveLeft() {
		boolean canMove = true;
		for (Rectangle block : this.blocks) {
				int row = block.y / 20;
				int column = (block.x / 20) - 1;
				if (withinBoundsAndNoCollision(row, column)) {
					return false;
				}				
		}
		return canMove;
	}
	
	private void moveBy(int xOffset, int yOffset) {
		blocks[0].setLocation((int)blocks[0].getX() + xOffset, (int)blocks[0].getY() + yOffset);
		blocks[1].setLocation((int)blocks[1].getX() + xOffset, (int)blocks[1].getY() + yOffset);
		blocks[2].setLocation((int)blocks[2].getX() + xOffset, (int)blocks[2].getY() + yOffset);
		blocks[3].setLocation((int)blocks[3].getX() + xOffset, (int)blocks[3].getY() + yOffset);
		center.setLocation(center.x + xOffset, center.y + yOffset);
	}	

	public void rotateClockWise() {
		if (!canRotate())
			return;
		final double pivotX = center.getX();
		final double pivotY = center.getY();
		for (Rectangle2D r : this.blocks) {
			double newX = r.getY() + pivotX - pivotY;
			double newY = pivotX + pivotY - r.getX();
				r.setRect(newX, newY, r.getWidth(), r.getHeight());
		}
	}

	private boolean canRotate() {
		final double pivotX = center.getX();
		final double pivotY = center.getY();
		for (Rectangle2D block : this.blocks) {
			double newX = block.getY() + pivotX - pivotY;
			double newY = pivotX + pivotY - block.getX();

			int row = (int)newY / 20;
			int column = (int)newX / 20;
			if (withinBoundsAndNoCollision(row, column)) {
				return false;
			}							
		}		
		return true;
	}

	private boolean withinBoundsAndNoCollision(int row, int column) {
		return !withinBounds(row, column) || matrix.getBoardLayout()[row] != null && matrix.getBoardLayout()[row][column] != null;
	}

	private boolean withinBounds(int row, int column) {
		if ((row >= 0 && row < matrix.MAX_LINES) &&
				(column >= 0 && column < matrix.MAX_COLUMNS)) {
			return true;
		}
		return false;
	}
}