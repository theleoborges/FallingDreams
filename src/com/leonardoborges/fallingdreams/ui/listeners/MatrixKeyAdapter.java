package com.leonardoborges.fallingdreams.ui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.leonardoborges.fallingdreams.models.Matrix;

public class MatrixKeyAdapter extends KeyAdapter {
	private Matrix matrix;
	
	public MatrixKeyAdapter(Matrix matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!matrix.running()) {
			return;
		}
		super.keyPressed(e);		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			this.matrix.movePieceDown();
			break;
		case KeyEvent.VK_LEFT:
			this.matrix.movePieceLeft();					
			break;
		case KeyEvent.VK_RIGHT:
			this.matrix.movePieceRight();							
			break;			
		case KeyEvent.VK_R:
			this.matrix.rotatePiece();
			break;
		case KeyEvent.VK_UP:
			this.matrix.rotatePiece();
			break;
		case KeyEvent.VK_P:
			this.matrix.pauseResume();			
			break;			
		default:
			break;
		}
	}	
}
