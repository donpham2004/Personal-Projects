package com.breakout;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	public Window(BreakOut bout, String name) {
		frame = new JFrame(name);
		frame.add(bout);
		frame.setPreferredSize(new Dimension(BreakOut.WIDTH,BreakOut.HEIGHT));
		frame.setMinimumSize(new Dimension(BreakOut.WIDTH,BreakOut.HEIGHT));
		frame.setMaximumSize(new Dimension(BreakOut.WIDTH,BreakOut.HEIGHT));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}
}
