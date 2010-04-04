package com.leonardoborges.fallingdreams;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.leonardoborges.fallingdreams.models.Matrix;
import com.leonardoborges.fallingdreams.ui.MainFrame;
import com.leonardoborges.fallingdreams.ui.MatrixPanel;
import com.leonardoborges.fallingdreams.ui.StatsPanel;
import com.leonardoborges.fallingdreams.ui.listeners.MatrixKeyAdapter;

public class FallingDreams {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matrix matrix = new Matrix();
		
        MatrixPanel matrixPanel = new MatrixPanel(matrix);
        StatsPanel statsPanel = new StatsPanel(matrix);
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.addKeyListener(new MatrixKeyAdapter(matrix));
        mainFrame.getContentPane().add(matrixPanel, BorderLayout.WEST);
        mainFrame.getContentPane().add(statsPanel, BorderLayout.EAST);        
        mainFrame.setVisible(true);
	}

}
