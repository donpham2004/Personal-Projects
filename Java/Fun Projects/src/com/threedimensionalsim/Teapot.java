package com.threedimensionalsim;

public class Teapot extends RenderableObject4D{

	public Teapot(Vector4D positionVector, Vector4D xVector, Vector4D yVector, Vector4D zVector) {
		super(positionVector, xVector, yVector, zVector, "/Users/donpham/eclipse/EclipseWorkPlace/Fun Projects/src/com/threedimensionalsim/Teapot");
		// TODO Auto-generated constructor stub
	}
	
	public Teapot(double x, double y, double z) {
		this(new Vector4D(x,y,z), new Vector4D(1,0,0), new Vector4D(0,-1,0), new Vector4D(0,0,1));
		// TODO Auto-generated constructor stub
	}

}
