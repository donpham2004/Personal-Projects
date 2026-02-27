package com.tetris;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	public Window(String name, int width, int height,Tetris tetris) {
		setPreferredSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setTitle(name);
		add(tetris);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}
}
