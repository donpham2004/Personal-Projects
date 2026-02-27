package com.threedimensionalsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Renderer {
	private Camera camera;
	private Vector4D coord1, coord2, coord3;
	private Color color1, color2, color3;
	private Color color;
	private BufferedImage bi;
	private int[] pixels;
	private double[] depthBuffer;
	private int xRes;
	private int yRes;
	private Vector4D lightPos, lightDir;
	private  Matrix4D projectionMatrix = new Matrix4D(new double[][]
			{{1/((double)ThreeDimension.WIDTH/ThreeDimension.HEIGHT*Math.tan(ThreeDimension.POV/2)),0,0,0},
			 {0,1/Math.tan(ThreeDimension.POV/2),0,0},
			 {0,0,ThreeDimension.ZFAR/(ThreeDimension.ZFAR-ThreeDimension.ZNEAR), -ThreeDimension.ZFAR*ThreeDimension.ZNEAR/(ThreeDimension.ZFAR-ThreeDimension.ZNEAR)},
			 {0,0,1,0}});
	private Matrix4D cameraMatrix;
	public Renderer(Camera camera) {
		lightPos = new Vector4D(0,0,0);
		lightDir = new Vector4D(0,0,1);
		xRes = ThreeDimension.WIDTH;
		yRes = ThreeDimension.HEIGHT;
		bi = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
		pixels=((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
		depthBuffer = new double[xRes*yRes];
		this.camera = camera;
	}
	
	public double getDistance(Vector4D plane, Vector4D normala, Vector4D point) {
		Vector4D normal = normala.copy();
		normal.normalize();
		double distance = point.dotProduct(normal)-plane.dotProduct(normal);
		Matrix4DFactory.add(normal);
		return distance;
	}
	
	public Matrix4D getProjectionMatrix() {
		projectionMatrix.setElem(0, 0, 1/((double)ThreeDimension.WIDTH/ThreeDimension.HEIGHT*Math.tan(ThreeDimension.POV/2)));
		projectionMatrix.setElem(1, 1, 1/Math.tan(ThreeDimension.POV/2));
		return projectionMatrix;
	}
	
	// Finds where line intersects plane and returns coordinates
	public Vector4D vectorIntersectPlane(Vector4D plane, Vector4D normal, Vector4D start, Vector4D end) {

		Vector4D plane_n = normal.copy();
		plane_n.normalize();
		double plane_d = -plane_n.dotProduct(plane);
		double ad = start.dotProduct(plane_n);
		double bd = end.dotProduct(plane_n);
		double t = (-plane_d - ad)/(bd - ad);
		Vector4D lineStartToEnd = end.copy();
		lineStartToEnd.sub(start);
		lineStartToEnd.scale(t);
		Vector4D s = start.copy();
		s.add(lineStartToEnd);
		Matrix4DFactory.add(plane_n);
		Matrix4DFactory.add(lineStartToEnd);
		
		return s;
	}
	
	public Triangle[] triangleClipAgainstPlane(Vector4D plane, Vector4D normal, Triangle toClip) {
		
		normal.normalize();
		
		Vector4D[] inside = new Vector4D[3];
		int numInside = 0;
		int numOutside = 0;
		Vector4D[] outside = new Vector4D[3];
		double d0 = getDistance(plane, normal, coord1);
		double d1 = getDistance(plane, normal, coord2);
		double d2 = getDistance(plane, normal, coord3);
		if(d0>=0) {
			inside[numInside++] = coord1.copy();
		}else {
			outside[numOutside++] = coord1.copy();
		}
		if(d1>=0) {
			inside[numInside++] = coord2.copy();
		}else {
			outside[numOutside++] = coord2.copy();
		}
		if(d2>=0) {
			inside[numInside++] = coord3.copy();
		}else {
			outside[numOutside++] = coord3.copy();
		}
		if(numInside == 0) {
			return new Triangle[0];
		}
		
		if(numInside==3) {
			Triangle[] t = new Triangle[1];
			t[0] = toClip;
			return t;
		}
		
		if(numInside == 1 && numOutside == 2) {
			Triangle[] t = new Triangle[1];
			t[0] = new Triangle(inside[0],
					vectorIntersectPlane(plane,normal,inside[0],outside[0]),
					vectorIntersectPlane(plane,normal,inside[0],outside[1]), color
					);
			return t;
		}
		
		if(numInside == 2 && numOutside == 1) {
			Triangle[] t = new Triangle[2];
			t[0] = new Triangle(inside[0],
					inside[1],
					vectorIntersectPlane(plane,normal,inside[0],outside[0]), color
					);
			t[1] = new Triangle(inside[1].copy(),
					vectorIntersectPlane(plane,normal,inside[0],outside[0]),
					vectorIntersectPlane(plane,normal,inside[1],outside[0]), color
					);
			return t;
		}
		
		return null;
	}
	private Color black = Color.BLACK;
	public void render(LinkedList<RenderableObject4D> objects, Graphics g) {
		cameraMatrix = camera.getCameraMatrix();
		for(int i=0;i<depthBuffer.length;i++) {
			depthBuffer[i] = Double.NEGATIVE_INFINITY;
		}
		
		for(int i=0;i<pixels.length;i++) {
			pixels[i] = black.getRGB();
		}
		
		for(int i=0;i<objects.size();i++) {

			
			RenderableObject4D renderObject = objects.get(i);
			LinkedList<Triangle> triangleMesh = renderObject.getTriangleMesh();
			Iterator<Triangle> itr = triangleMesh.iterator();
			while(itr.hasNext()) {
				Triangle triangle = itr.next();
				startRender(renderObject, triangle, g);
			}
		}
		g.drawImage(bi, 0, 0, ThreeDimension.WIDTH, ThreeDimension.HEIGHT, 0, 0, xRes, yRes, null);
	}
	
	
	public void startRender(RenderableObject4D object, Triangle triangle, Graphics g) {
		// Step #1 Get copy of triangle coordinates 	
	
		coord1 = triangle.getCoord(0).copy();
		coord2 = triangle.getCoord(1).copy();
		coord3 = triangle.getCoord(2).copy();
		color = triangle.getColor();
		// Step #2 Mesh Space to World Space
		meshToWorldSpace(object);
		
		// Step #3 Do Lighting
		doLighting();
		
		
		// Step #4 World Space to Camera space
		worldToCameraSpace();
				
		// Step #5 Check if triangle is visible in camera space
		if(!visible()) {
			Matrix4DFactory.add(coord1);
			Matrix4DFactory.add(coord2);
			Matrix4DFactory.add(coord3);
			return;
		}
		Queue<Triangle> listTriangle = new LinkedList<>();
		Triangle tmp = new Triangle(coord1, coord2, coord3, color);
		
		Vector4D v1 =	Matrix4DFactory.getVector();
		Vector4D v2 = Matrix4DFactory.getVector();
		v1.setXVal(0);
		v1.setYVal(0);
		v1.setZVal(ThreeDimension.ZNEAR);
		v2.setXVal(0);
		v2.setYVal(0);
		v2.setZVal(1);
		Triangle[] t = triangleClipAgainstPlane(v1,v2,tmp);
		
		
		
		
		v1.setXVal(0);
		v1.setYVal(0);
		v1.setZVal(ThreeDimension.ZFAR);
		v2.setXVal(0);
		v2.setYVal(0);
		v2.setZVal(1);
		Triangle t2[] = triangleClipAgainstPlane(v1,v2,tmp);
		Matrix4DFactory.add(v1);
		Matrix4DFactory.add(v2);
		
		
		for(int i=0;i<t.length;i++) {
			coord1 = t[i].getCoord(0);
			coord2 = t[i].getCoord(1);
			coord3 = t[i].getCoord(2);
			color = t[i].getColor();
			// Step #6 Camera to Projection Space
			cameraToProjectionSpace();
			
			// Step #7 Projection to Screen space
			projectionToScreenSpace();
			listTriangle.add(t[i]);
		}
		
		for(int i=0;i<t2.length;i++) {
			coord1 = t2[i].getCoord(0);
			coord2 = t2[i].getCoord(1);
			coord3 = t2[i].getCoord(2);
			color = t2[i].getColor();
			// Step #6 Camera to Projection Space
			cameraToProjectionSpace();
			
			// Step #7 Projection to Screen space
			projectionToScreenSpace();
			listTriangle.add(t2[i]);
		}
		
		int newTriangles = listTriangle.size();
		for(int p=0;p<4;p++) {
			Triangle[] trianglesClipped=null;
			while(newTriangles>0) {
				Triangle test = listTriangle.remove();
				coord1 = test.getCoord(0);
				coord2 = test.getCoord(1);
				coord3 = test.getCoord(2);
				newTriangles--;
				switch(p) {
				case 0: trianglesClipped = this.triangleClipAgainstPlane(new Vector4D(0,0,0),new Vector4D(0,1,0),test);
					break;
				case 1: trianglesClipped = this.triangleClipAgainstPlane(new Vector4D(0,yRes,0),new Vector4D(0,-1,0),test);
					break;
				case 2: trianglesClipped = this.triangleClipAgainstPlane(new Vector4D(0,0,0),new Vector4D(1,0,0),test);
				break;
				case 3: trianglesClipped = this.triangleClipAgainstPlane(new Vector4D(xRes,0,0),new Vector4D(-1,0,0),test);
				break;
				}
				for(int i=0;i<trianglesClipped.length;i++) {
					listTriangle.add(trianglesClipped[i]);
				}
			}
			newTriangles = listTriangle.size();
		}
		
		// Step #8 render Triangle
		while(!listTriangle.isEmpty()) {
			Triangle tr= listTriangle.remove();
			
			coord1 = tr.getCoord(0);
			coord2 = tr.getCoord(1);
			coord3 = tr.getCoord(2);
			color = tr.getColor();
			
			rasterizeTriangle();
			Matrix4DFactory.add(coord1);
			Matrix4DFactory.add(coord2);
			Matrix4DFactory.add(coord3);
		}
		
		
		
	}
	
	public void rasterizeTriangle() {
		if(coord1.getYVal()>coord2.getYVal()) {
			Vector4D tmp = coord1;
			coord1 = coord2;
			coord2 = tmp;
		}
		
		if(coord2.getYVal()>coord3.getYVal()) {
			Vector4D tmp = coord2;
			coord2 = coord3;
			coord3 = tmp;
		}
		
		if(coord1.getYVal()>coord2.getYVal()) {
			Vector4D tmp = coord1;
			coord1 = coord2;
			coord2 = tmp;
		}
		int x1 = (int) coord1.getXVal();
		int y1 = (int) coord1.getYVal();
		double z1 = 1.0/linearizeDepth(coord1.getZVal());
		int x2 = (int)coord2.getXVal();
		int y2 = (int)coord2.getYVal();
		double z2 = 1.0/linearizeDepth(coord2.getZVal());
		int x3 = (int)coord3.getXVal();
		int y3 = (int)coord3.getYVal();
		double z3 = 1.0/linearizeDepth(coord3.getZVal());
		
		int dx1 = x2-x1;
		int dy1 = y2-y1;
		double dz1 = z2 - z1;
		int dy2 = y3-y1;
		int dx2 = x3-x1;
		
		double dz2 = z3 -z1;
		
		double da_step = (dy1==0)?0:dx1/(double)Math.abs(dy1);
		double db_step = (dy2==0)?0:dx2/(double)Math.abs(dy2);
		double dz1_step = (dy1==0)?0: dz1/(double)Math.abs(dy1);
		double dz2_step = (dy2==0)?0: dz2/(double)Math.abs(dy2);
		if(dy1!=0) {
			for(int i=y1;i<=y2;i++) {
				int ax = (int) (x1 + (double)(i-y1)*da_step);
				int bx = (int) (x1 + (double)(i-y1)*db_step);
				double sz = z1 + (double)(i-y1)*dz1_step;
				double ez = z1 + (double)(i-y1)*dz2_step;
				if(ax>bx) {
					int tmp = bx;
					bx = ax;
					ax = tmp;
					
					double t = sz;
					sz = ez;
					ez = t;
				}
				
				double tstep = 1.0/((double)(bx-ax));
				double t = 0;
				
				
				for(int j=ax;j<bx;j++) {
					double z = (1.0 - t)*sz + t *ez;
					if(i*xRes+j<pixels.length && depthBuffer[i*xRes+j]<z) {
						depthBuffer[i*xRes+j] = z;
						pixels[i*xRes+j] = color.getRGB();
					}
					t+=tstep;
					
				}
			}
		}
		dy1 = y3 - y2;
		dx1 = x3 - x2;
		dz1 = z3 - z2;
		
		
	    da_step = (dy1==0)?0:dx1/(double)Math.abs(dy1);
		db_step = (dy2==0)?0:dx2/(double)Math.abs(dy2);
		dz1_step = (dy1 == 0)?0:dz1/(double)Math.abs(dy1);
		
		
		for(int i=y2;i<=y3;i++) {
			int ax = (int) (x2 + (double)(i-y2)*da_step);
			int bx = (int) (x1 + (double)(i-y1)*db_step);
			double sz = z2 + (double)(i-y2)*dz1_step;
			double ez = z1 + (double)(i-y1)*dz2_step;
			
			if(ax>bx) {
				int tmp = bx;
				bx = ax;
				ax = tmp;
				
				double t = sz;
				sz = ez;
				ez = t;
			}
			double tstep = 1.0/((double)(bx-ax));
			double t = 0;
			
	
			
			for(int j=ax;j<=bx;j++) {
				double z = (1.0-t)*sz+t*ez;
				if(i*xRes+j<pixels.length && depthBuffer[i*xRes+j]<z) {
					depthBuffer[i*xRes+j] = z;
					pixels[i*xRes+j] = color.getRGB();
				}
				t+=tstep;
			}
		}
	}
	
	private double linearizeDepth(double depth) {
		return ThreeDimension.ZNEAR+(ThreeDimension.ZFAR-ThreeDimension.ZNEAR)*depth/ThreeDimension.ZFAR;
	}
	
	
	public void meshToWorldSpace(RenderableObject4D object) {
		coord1.add(object.getPositionVector());
		coord2.add(object.getPositionVector());
		coord3.add(object.getPositionVector());
	}
	private float [] hsb = new float[3];
	public void doLighting() {
		
		
		Vector4D light = Matrix4DFactory.getVector();
		
		light.copy(camera.getDirectionVector());
		
		Vector4D normal = getNormalVector();
		
		double dp = light.dotProduct(normal);
				
		hsb[2] = dp>0.4f? (float)dp: 0.4f;
		color = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
				
		Matrix4DFactory.add(light);
		Matrix4DFactory.add(normal);
	}
	
	public void worldToCameraSpace() {
		
				
		coord1.multiply(cameraMatrix);
		coord2.multiply(cameraMatrix);
		coord3.multiply(cameraMatrix);
	}
	
	public Vector4D getNormalVector() {
		Vector4D vectorA = Matrix4DFactory.getVector();
		vectorA.setXVal(coord2.getXVal()-coord1.getXVal());
		vectorA.setYVal(coord2.getYVal()-coord1.getYVal());
		vectorA.setZVal(coord2.getZVal()-coord1.getZVal());
		Vector4D vectorB = Matrix4DFactory.getVector();
		vectorB.setXVal(coord3.getXVal()-coord1.getXVal());
		vectorB.setYVal(coord3.getYVal()-coord1.getYVal());
		vectorB.setZVal(coord3.getZVal()-coord1.getZVal());
		
		
		
		vectorA.crossProduct(vectorB);
		vectorA.normalize();
		Matrix4DFactory.add(vectorB);
		return vectorA;
	}
	public boolean visible() {
		Vector4D vectorA = getNormalVector();
		boolean res = vectorA.dotProduct(coord1)>0;
		Matrix4DFactory.add(vectorA);
		return res;
	}
	
	public void cameraToProjectionSpace() {
		Matrix4D matrix = getProjectionMatrix();
		coord1.multiply(matrix);
		coord2.multiply(matrix);
		coord3.multiply(matrix);
	}
	
	public void projectionToScreenSpace() {
		coord1.setXVal((coord1.getXVal()+1)*xRes/2);
		coord1.setYVal((coord1.getYVal()+1)*yRes/2);
		coord2.setXVal((coord2.getXVal()+1)*xRes/2);
		coord2.setYVal((coord2.getYVal()+1)*yRes/2);
		coord3.setXVal((coord3.getXVal()+1)*xRes/2);
		coord3.setYVal((coord3.getYVal()+1)*yRes/2);
	}
	
	public void drawCrossHair(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xRes/2-1,yRes/2-10,2, 20);
		g.fillRect(xRes/2-10,yRes/2-1,20, 2);
	}
}
