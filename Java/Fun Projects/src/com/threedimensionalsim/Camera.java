package com.threedimensionalsim;

public class Camera extends Object4D {
	private Vector4D directionVector;
	private Matrix4D cameraMatrix;
	
	

	public Camera(Vector4D positionVector, Vector4D xVector, Vector4D yVector, Vector4D zVector) {
		super(positionVector, xVector, yVector, zVector);
		cameraMatrix = Matrix4DFactory.getMatrix();
		directionVector = zVector.copy();
		
	}
	
	public Matrix4D getCameraMatrix() {
		updateCameraMatrix();
		return cameraMatrix;
	}
	public Vector4D getDirectionVector() {
		return directionVector;
	}
	
	public void tick() {
		
	}
	
	public void updateCameraMatrix() {
		Vector4D xVector = getXVector();
		Vector4D yVector = getYVector();
		Vector4D zVector = getZVector();
		Vector4D positionVector = getPositionVector();
		cameraMatrix.setElem(0, 0, xVector.getXVal());
		cameraMatrix.setElem(1, 0, yVector.getXVal());
		cameraMatrix.setElem(2, 0, zVector.getXVal());
		cameraMatrix.setElem(0, 3, -positionVector.dotProduct(xVector));
		
		cameraMatrix.setElem(0, 1, xVector.getYVal());
		cameraMatrix.setElem(1, 1, yVector.getYVal());
		cameraMatrix.setElem(2, 1, zVector.getYVal());
		cameraMatrix.setElem(1, 3, -positionVector.dotProduct(yVector));
		
		cameraMatrix.setElem(0, 2, xVector.getZVal());
		cameraMatrix.setElem(1, 2, yVector.getZVal());
		cameraMatrix.setElem(2, 2, zVector.getZVal());
		cameraMatrix.setElem(2, 3, -positionVector.dotProduct(zVector));
		cameraMatrix.setElem(3, 3, 1);
	}
	
	public Matrix4D getXYZMatrixCopy() {
		Vector4D xVector = getXVector();
		Vector4D yVector = getYVector();
		Vector4D zVector = getZVector();
		Matrix4D xyz = Matrix4DFactory.getMatrix();
		xyz.setElem(0, 0, xVector.getXVal());
		xyz.setElem(0, 1, yVector.getXVal());
		xyz.setElem(0, 2, zVector.getXVal());
		xyz.setElem(0, 3, 0);
		
		xyz.setElem(1, 0, xVector.getYVal());
		xyz.setElem(1, 1, yVector.getYVal());
		xyz.setElem(1, 2, zVector.getYVal());
		xyz.setElem(1, 3, 0);
		
		xyz.setElem(2, 0, xVector.getZVal());
		xyz.setElem(2, 1, yVector.getZVal());
		xyz.setElem(2, 2, zVector.getZVal());
		xyz.setElem(2, 3, 0);
		
		xyz.setElem(3, 0, 0);
		xyz.setElem(3, 1, 0);
		xyz.setElem(3, 2, 0);
		xyz.setElem(3, 3, 1);
		return xyz;
	}
}
