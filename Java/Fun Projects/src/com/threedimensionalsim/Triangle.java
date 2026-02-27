package com.threedimensionalsim;

import java.awt.*;

public class Triangle {
	private Vector4D[] coords;
	private Color color;

	public Triangle(Vector4D coord1, Vector4D coord2, Vector4D coord3, Color color) {
		coords = new Vector4D[3];
		coords[0] = coord1;
		coords[1] = coord2;
		coords[2] = coord3;
		this.color = color;
	}
	
	public void multiplyCoords(Matrix4D matrix) {
		for(int i=0;i<coords.length;i++) {
			coords[i].multiply(matrix);
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public Vector4D getCoord(int index) {
		return coords[index];
	}
}
