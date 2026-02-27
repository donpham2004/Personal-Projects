package com.threedimensionalsim;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(Canvas c) {
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(c);
		this.pack();
		this.setLocationRelativeTo(null);
		
	}
}
