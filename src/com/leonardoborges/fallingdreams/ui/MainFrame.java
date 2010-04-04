package com.leonardoborges.fallingdreams.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("FallingDreams - by Leonardo Borges (www.leonardoborges.com)");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(550,400));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
	}

}
