package com.chess;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	public Window(int width, int height, String title, Chess chess) {
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width,height));
		add(chess);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(title);
		
		setVisible(true);
		
	}
}
