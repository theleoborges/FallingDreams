package com.leonardoborges.fallingdreams.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.leonardoborges.fallingdreams.models.Matrix;
import com.leonardoborges.fallingdreams.models.pieces.Block;

public class MatrixPanel extends JPanel implements Observer {
	private Matrix matrix;

	public MatrixPanel(Matrix matrix) {
		super();
		this.matrix = matrix;
		this.matrix.addObserver(this);
		
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(200, 400));
        this.setBorder(BorderFactory.createLineBorder(Color.black));

	}	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (matrix.getCurrentFallingPiece() != null) {
			for (Rectangle block : matrix.getCurrentFallingPiece().getBlocks()) {
				drawBlock(g2, block);
			}
		}
		
		for (Rectangle[] rows : matrix.getBoardLayout()) {
			for (Rectangle block : rows) {
				if (block != null) {
					drawBlock(g2, block);	
				}
				
			}
		}
	}

	private void drawBlock(Graphics2D g2, Rectangle block) {
		g2.setColor(((Block)block).getColor());
		g2.fill(block);
		g2.setColor(Color.black);
		g2.draw(block);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();	
	}
	
}