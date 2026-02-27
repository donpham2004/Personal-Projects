package com.kinematics;


import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	public Window(int width, int height, String title, Kinematic kinematic) {
		kinematic.setMinimumSize(new Dimension(width, height));
		kinematic.setMaximumSize(new Dimension(width, height));
		kinematic.setPreferredSize(new Dimension(width, height));
		this.setLocation(500, 200);
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(kinematic);
		this.requestFocus();
		pack();
	}
}

