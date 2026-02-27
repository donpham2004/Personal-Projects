package com.fluid;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	public Window(int width, int height, String title, Fluid fluid) {
		fluid.setMinimumSize(new Dimension(width, height));
		fluid.setMaximumSize(new Dimension(width, height));
		fluid.setPreferredSize(new Dimension(width, height));
		this.setLocation(500, 200);
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(fluid);
		this.requestFocus();
		pack();
	}
}
