package com;
import javax.swing.JFrame;
import java.awt.Canvas;

public class Window extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Window(Canvas c) {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		add(c);
		pack();
		setLocationRelativeTo(null);
		
	}
}
