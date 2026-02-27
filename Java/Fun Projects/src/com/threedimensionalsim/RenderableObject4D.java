package com.threedimensionalsim;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RenderableObject4D extends Object4D {
	private LinkedList<Triangle> triangleMesh;
	private String meshFile;
	public RenderableObject4D(Vector4D positionVector, Vector4D xVector, Vector4D yVector, Vector4D zVector, String meshFile) {
		super(positionVector, xVector, yVector, zVector);
		this.meshFile = meshFile;
		triangleMesh = new LinkedList<>();
		createTriangleMesh();
		updateTriangleMesh();
	}
	
	private void createTriangleMesh() {
		LinkedList<Vector4D> vertices = new LinkedList<Vector4D>();
		
		File file = new File(meshFile);
		if(!file.exists()) 
			return;
		
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNext()) {
			String str = scan.next();
			Vector4D vec = Matrix4DFactory.getVector();
			if(str.equals("v")) {
				vec.setXVal(scan.nextDouble());
				vec.setYVal(scan.nextDouble());
				vec.setZVal(scan.nextDouble());
				vertices.add(vec);
			}else if(str.equals("f")) {
				int v1 = scan.nextInt()-1;
				int v2 = scan.nextInt()-1;
				int v3 = scan.nextInt()-1;
				addTriangle(new Triangle(vertices.get(v1).copy(),
						vertices.get(v2).copy(),
						vertices.get(v3).copy(),Color.WHITE));
			}
		}
		while(!vertices.isEmpty()) {
			Matrix4DFactory.add(vertices.remove());
		}
	}
	
	public boolean renderable() {
		return true;
	}
	
	public void addTriangle(Triangle triangle) {
		triangleMesh.add(triangle);
	}
	

	public void multiplyTriangleMesh(Matrix4D matrix) {
		for(int i=0;i<triangleMesh.size();i++) {
			triangleMesh.get(i).multiplyCoords(matrix);
		}
	}
	
	public void updateTriangleMesh() {
		Matrix4D matrix = Matrix4DFactory.getMatrix();
		matrix.setElem(0, 0, getXVector().getXVal());
		matrix.setElem(1, 0, getXVector().getYVal());
		matrix.setElem(2, 0, getXVector().getZVal());
		matrix.setElem(0, 1, getYVector().getXVal());
		matrix.setElem(1, 1, getYVector().getYVal());
		matrix.setElem(2, 1, getYVector().getZVal());
		matrix.setElem(0, 2, getZVector().getXVal());
		matrix.setElem(1, 2, getZVector().getYVal());
		matrix.setElem(2, 2, getZVector().getZVal());
		matrix.setElem(3, 3, 1);
		multiplyTriangleMesh(matrix);
		Matrix4DFactory.add(matrix);
	}
	
	public LinkedList<Triangle> getTriangleMesh() {
		return triangleMesh;
	}
}
