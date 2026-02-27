package com.map;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	public Window(int width, int height, String title, Map map) {
		
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(map);
		this.requestFocus();
		pack();
	}
}

