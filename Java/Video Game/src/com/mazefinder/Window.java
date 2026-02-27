package com.mazefinder;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int width, int height, String title,MazeFinder mazeFinder) {
		setPreferredSize(new Dimension(width,height));
		//setMaximumSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width,height));
		setTitle(title);
		add(mazeFinder);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}
}
