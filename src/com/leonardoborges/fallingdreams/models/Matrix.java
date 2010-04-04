package com.leonardoborges.fallingdreams.models;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Observable;

import com.leonardoborges.fallingdreams.models.pieces.AbstractPiece;
import com.leonardoborges.fallingdreams.models.pieces.PieceFactory;

public class Matrix extends Observable {
	public static final int MAX_LINES = 20;
	public static final int MAX_COLUMNS = 10;

	private AbstractPiece currentFallingPiece;
	private AbstractPiece nextPiece;
	
	private MainLoop mainLoop;
	private Rectangle[][] boardLayout;
	private int score;
	private int level;
	private int completeLinesAccumulator;

	public Matrix() {        
		initFields();		
	}
	
	public void start() {
		if (mainLoop != null) {
			mainLoop.stop();
		}
		initFields();		
		Thread t = new Thread(this.mainLoop);
		t.start();
	}		

	private void initFields() {
        this.mainLoop = new MainLoop(this);		
		this.boardLayout = new Rectangle[MAX_LINES][MAX_COLUMNS];
        this.score = 0;
    	this.level = 1;
    	this.completeLinesAccumulator = 0;
	}
	
	private void gameOver() {
		this.mainLoop.stop();
		setChangedAndNotifyObservers();
	}

	public void pauseResume() {
		this.mainLoop.pause();	
	}
	
	public boolean paused() {
		return this.mainLoop.paused();	
	}
	
	public boolean running() {
		return this.mainLoop.running();	
	}		
	
	public Rectangle[][] getBoardLayout() {
		return boardLayout;
	}	
	
	public AbstractPiece getCurrentFallingPiece() {
		return currentFallingPiece;
	}
	
	public AbstractPiece getNextPiece() {
		return nextPiece;
	}	

	public void landPiece(AbstractPiece piece) {
		for (Rectangle block : piece.getBlocks()) {
			landBlock(block);
		}
		clearCompleteRows();
	}	
	
	private void landBlock(Rectangle block) {
		int row = block.y / 20;
		int column = block.x / 20;		
		boardLayout[row][column] = block;
	}
	
	public void unlandBlock(Rectangle block) {
		int row = block.y / 20;
		int column = block.x / 20;		
		boardLayout[row][column] = null;
	}

	private void clearCompleteRows() {
		int completeLines = 0;
		for (int r = 0; r < boardLayout.length; r++) {
			Rectangle[] rows = boardLayout[r];
			int temp = 0;
			for (int c = 0; c < rows.length; c++) {
				Rectangle block = rows[c];
				if (block != null) {
					temp++;	
				}
			}
			if (temp == MAX_COLUMNS) {
				for (int c = 0; c < rows.length; c++) {
					Rectangle block = rows[c];
					if (block != null) {
						boardLayout[r][c] = null;	
					}
				}
				shiftRowsDown(r);
				completeLines++;
			} 
		}
		increaseScoreBy(completeLines);
	}

	private void shiftRowsDown(int rowNumber) {
		for (int r = rowNumber - 1; r >= 0; r--) {
			for (Rectangle block : boardLayout[r]) {
				if (block != null) {
					unlandBlock(block);
					block.setLocation((int)block.getX(), (int)block.getY() + AbstractPiece.MOVE_OFFSET);
					landBlock(block);
				}
			}
		}		
	}	
	
	private void increaseScoreBy(int completeLines) {
		this.completeLinesAccumulator += completeLines;
		if (completeLines > 0) {
			this.score += (100 * completeLines);
			if (this.completeLinesAccumulator >= 10) {
				this.level += 1;
				this.completeLinesAccumulator = this.completeLinesAccumulator - 10;
				this.mainLoop.increaseSpeed();
			}
			this.setChanged();
			this.notifyObservers();
		}
	}

	public void spawnNewPiece() {
		this.currentFallingPiece = this.nextPiece == null ? PieceFactory.getRandomPiece(this) : this.nextPiece;
		this.nextPiece = PieceFactory.getRandomPiece(this);
		if (!willFit(this.currentFallingPiece)) {
			gameOver();
		}
		
		setChangedAndNotifyObservers();
	}
	
	public boolean willFit(AbstractPiece piece) {
		for (Rectangle2D block : piece.getBlocks()) {
			int row = (int)block.getY() / 20;
			int column = (int)block.getX() / 20;
			if (getBoardLayout()[row][column] != null) {
				return false;
			}							
		}
		return true;
	}	

	public void movePieceDown() {
		getCurrentFallingPiece().moveDown();
		setChangedAndNotifyObservers();
		
	}
	
	public void movePieceRight() {
		getCurrentFallingPiece().moveRight();
		setChangedAndNotifyObservers();
		
	}	
	
	public void movePieceLeft() {
		getCurrentFallingPiece().moveLeft();
		setChangedAndNotifyObservers();
		
	}

	public void rotatePiece() {
		getCurrentFallingPiece().rotateClockWise();
		setChangedAndNotifyObservers();
		
	}
	
	private void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}	

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}	
}


class MainLoop implements Runnable {
	private Matrix matrix;
	private int gravity = 500;
	private boolean paused = false;
	private boolean running = false;
	
	public void pause() {
		this.paused = this.paused == true ? false : true;
	}
	
	public boolean paused() {
		return this.paused;
	}
	
	public boolean running() {
		return this.running;
	}
	
	public void stop() {
		this.running = false;
	}
	
	public void reset() {
		initFields();
	}	
	
	public void increaseSpeed() {
		this.gravity = this.gravity == 100 ? this.gravity : this.gravity - 50;
		
	}

	public MainLoop(Matrix matrix) {
		this.matrix = matrix;
		initFields();
	}

	private void initFields() {
		gravity = 500;
		paused = false;
		running = false;		
	}

	@Override
	public void run() {
		running = true;
		try {
			matrix.spawnNewPiece();
			while (running) {
				if (!paused) {
					Thread.sleep(gravity);
					matrix.movePieceDown();					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}