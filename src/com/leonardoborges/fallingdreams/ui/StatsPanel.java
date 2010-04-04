package com.leonardoborges.fallingdreams.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.leonardoborges.fallingdreams.models.Matrix;
import com.leonardoborges.fallingdreams.models.pieces.AbstractPiece;
import com.leonardoborges.fallingdreams.models.pieces.Block;

public class StatsPanel extends JPanel implements Observer {
	
	private NextPiecePanel nextPiecePanel;
	private JLabel currentScoreLabel;
	private JLabel currentLevelLabel;
	private Matrix matrix;
	private JLabel statusLabel;

	public StatsPanel(Matrix matrix) {
		super();
		this.matrix = matrix;
		this.matrix.addObserver(this);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(350, 400));
		
        initComponents();
	}

	private void initComponents() {
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.add(initScorePane());
        labelsPanel.add(initLevelPane());
        labelsPanel.add(initStatusPane());
        
        this.add(initTopPane());
        this.add(labelsPanel);
        this.add(initButtonsPane());
        this.add(initInstructionsPane());
	}

	private JPanel initStatusPane() {
		JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(350, 0));
        statusLabel = new JLabel();
        statusLabel.setForeground(Color.red);        
        statusPanel.add(statusLabel);
		return statusPanel;
	}

	private JPanel initLevelPane() {
		JPanel levelPanel = new JPanel();
        levelPanel.setPreferredSize(new Dimension(350, 0));
        JLabel levelLabel = new JLabel("Level:");
        currentLevelLabel = new JLabel("1");        
        levelPanel.add(levelLabel);
        levelPanel.add(currentLevelLabel);
		return levelPanel;
	}

	private JPanel initScorePane() {
		JPanel scorePanel = new JPanel();
        scorePanel.setPreferredSize(new Dimension(350, 0));
        JLabel scoreLabel = new JLabel("Score:");
        currentScoreLabel = new JLabel("0");        
        scorePanel.add(scoreLabel);
        scorePanel.add(currentScoreLabel);
		return scorePanel;
	}

	private JPanel initTopPane() {
		nextPiecePanel = new NextPiecePanel();
        matrix.addObserver(nextPiecePanel);
        
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(nextPiecePanel);
		return topPanel;
	}

	private JPanel initButtonsPane() {
		JButton aboutButton = new JButton("About");
        aboutButton.setFocusable(false);
        aboutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame aboutFrame = new JFrame("About");
				
				JEditorPane creditsEditorPane = new JEditorPane("text/html", "  Developed by Leonardo Borges (leonardoborges.rj@gmail.com)<br>"  
				 +"  <a href='http://twitter.com/leonardo_borges'>@leonardo_borges</a><br>"
				 +"  <a href='http://www.leonardoborges.com'>www.leonardoborges.com</a>"); 	
				
				 creditsEditorPane.setEditable(false);  
				 creditsEditorPane.setOpaque(false);
				 creditsEditorPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				 creditsEditorPane.addHyperlinkListener(new HyperlinkListener() {  
					 public void hyperlinkUpdate(HyperlinkEvent hle) {  
						 if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {  
							 try {
								java.awt.Desktop.getDesktop().browse(hle.getURL().toURI());
							} catch (Exception e) { e.printStackTrace(); }
						 }  
					 }  
				 });  				
				
				
				aboutFrame.getContentPane().add(creditsEditorPane);
				aboutFrame.getContentPane().setPreferredSize(new Dimension(450,100));
				aboutFrame.pack();
				aboutFrame.setLocationRelativeTo(null);
				aboutFrame.setResizable(false);
				aboutFrame.setVisible(true);
				
				
			}
		});
        
        JButton startStopButton = new JButton("New Game");
        startStopButton.setFocusable(false);
        startStopButton.addActionListener(new StartStopButtonListener(matrix));        
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(startStopButton);
        buttonsPanel.add(aboutButton);
		return buttonsPanel;
	}
	
	private JPanel initInstructionsPane() {
		JPanel instructionsPanel = new JPanel();
        JLabel instructionsLabel = new JLabel("UP - Rotate | P - Pause | LEFT, RIGHT, DOWN - Move");
        instructionsLabel.setForeground(Color.gray);        
        instructionsPanel.add(instructionsLabel);
		return instructionsPanel;
	}	

	@Override
	public void update(Observable o, Object arg) {
		Matrix matrix = (Matrix) o;
		
		this.currentScoreLabel.setText(String.valueOf(matrix.getScore()));
		this.currentLevelLabel.setText(String.valueOf(matrix.getLevel()));
		
		if(!matrix.running()) {
			this.statusLabel.setText("Game Over! Press 'New Game' to try again!");
		} else {
			this.statusLabel.setText("");
		}
		
	}
}

class NextPiecePanel extends JPanel implements Observer {
	private AbstractPiece nextPiece;

	public NextPiecePanel() {
		this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(60, 80));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Matrix matrix = (Matrix) o;
		if (matrix.getNextPiece() != null) {
			paintNextPiece(matrix.getNextPiece());
		}
	}
	
	private void paintNextPiece(AbstractPiece nextPiece) {
		this.nextPiece = nextPiece;
		this.repaint();	
	}		

	@Override
	public void paint(Graphics g) {
		if (nextPiece == null) { return; }		
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		for (Rectangle block : nextPiece.getBlocks()) {
			g2.setColor(((Block)block).getColor());
			g2.fillRect(block.x - nextPiece.getStartPosition().x + 20, block.y, block.width, block.height);
			g2.setColor(Color.black);			
			g2.drawRect(block.x - nextPiece.getStartPosition().x + 20, block.y, block.width, block.height);
		}		
	}
}

class StartStopButtonListener implements ActionListener {
	private Matrix matrix;

	public StartStopButtonListener(Matrix matrix) {
		this.matrix = matrix;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.matrix.start();
	}
}