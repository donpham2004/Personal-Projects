package com.threedimensionalsim;

import java.awt.*;

public class Cube extends RenderableObject4D {
	public Cube() {
		this(new Vector4D(0,0,0),new Vector4D(1,0,0),new Vector4D(0,1,0),new Vector4D(0,0,1));
	}
	
	public Cube(Vector4D positionVector, Vector4D xVector, Vector4D yVector, Vector4D zVector) {
		super(positionVector, xVector, yVector, zVector,"/Users/donpham/eclipse/EclipseWorkPlace/Fun Projects/src/com/threedimensionalsim/Cube");
	}
	
	public Cube(double x, double y, double z) {
		this(new Vector4D(x,y,z),new Vector4D(1,0,0), new Vector4D(0,1,0),new Vector4D(0,0,1));
	}
}
