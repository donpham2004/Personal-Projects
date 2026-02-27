package com;

import java.util.LinkedList;

public class SplitPolygon {
	private LinkedList<Polygon> polygons;
	
	public SplitPolygon() {
		polygons = new LinkedList<Polygon>();
	}
	public void split(Polygon polygon, Vector2D normal, Vector2D point1, Vector2D point2) {
		LinkedList<Vector2D> polygonVertices = new LinkedList<Vector2D>();
		
		for(int i=0;i<polygon.getMesh().getVertices().size();i++) {
			polygonVertices.add(
					polygon.getMesh().getVertices().get(i).multiply(
							Matrix2D.rotation(
									polygon.getMesh().getAngularPosition().getW()))
							.add(polygon.getMesh().getLinearPosition())
							);
		}
		Polygon p1 = new Polygon();
		
		Polygon p2 = new Polygon();
		LinkedList<Vector2D> p1Vertices = new LinkedList<Vector2D>();
		LinkedList<Vector2D> p2Vertices = new LinkedList<Vector2D>();
		int found = 0;
		for(int i=0;i<polygonVertices.size();i++) {
			Vector2D l0 = polygonVertices.get(i);
			Vector2D l = polygonVertices.get((i+1)%polygonVertices.size()).sub(l0);
			double ln = normal.dotProduct(l);
			if(ln==0)
				continue;
			
			double d = point1.sub(l0).dotProduct(normal)/ln;
			double d2 = l0.add(l.scale(d)).sub(point1).dotProduct(point2.sub(point1));
			p1Vertices.add(l0);
			if(!(d<=0||d>=1) && l0.add(l.scale(d)).sub(point1).magnitude()<= point2.sub(point1).magnitude()&&d2>=0) {
				found++;
				p1Vertices.add(l0.add(l.scale(d)));
				p2Vertices.add(l0.add(l.scale(d)));
				
				LinkedList<Vector2D> tmp = p1Vertices;
				p1Vertices = p2Vertices;
			    p2Vertices = tmp;
			}
		}
		if(found!=2) {
			polygons.add(polygon);
			return;
		}
		
		
		Mesh mesh = new Mesh();
		p1.setMesh(mesh);
		mesh.setAngularPosition(new Vector2D(0));
		p1.setAngularVelocity(new Vector2D(polygon.getAngularVelocity()));
		
		double x = 0;
		double y = 0;
		for(int i=0;i<p1Vertices.size();i++) {
			x+=p1Vertices.get(i).getX();
			y+=p1Vertices.get(i).getY();
		}
		x/=p1Vertices.size();
		y/=p1Vertices.size();
		mesh.setLinearPosition(new Vector2D(x,y));
		for(int i=0;i<p1Vertices.size();i++) {
			mesh.addVertices(p1Vertices.get(i).sub(mesh.getLinearPosition()));
		}
		
		LinkedList<Vector2D> normals = new LinkedList<>();
		for(int i=0;i<p1Vertices.size();i++) {
		
			mesh.addNormals(new Vector2D(0,0,1)
					.crossProduct( p1Vertices.get((i+1)%p1Vertices.size())
						       .sub(p1Vertices.get(i))
				).normalize());
		}
		
		p1.setLinearVelocity(
				polygon.getLinearVelocity()
				.add(   polygon.getAngularVelocity()
						.crossProduct(p1.getMesh().getLinearPosition().sub(polygon.getMesh().getLinearPosition()))
						)
				);
		p1.setDensity(polygon.getDensity());
		p1.calcInertia();
		polygons.add(p1);
		mesh = new Mesh();
		p2.setMesh(mesh);
		p2.setLinearVelocity(new Vector2D(polygon.getLinearVelocity()));
		mesh.setAngularPosition(new Vector2D(0));
		p2.setAngularVelocity(new Vector2D(polygon.getAngularVelocity()));
		x = 0;
		y = 0;
		for(int i=0;i<p2Vertices.size();i++) {
			x+=p2Vertices.get(i).getX();
			y+=p2Vertices.get(i).getY();
		}
		x/=p2Vertices.size();
		y/=p2Vertices.size();
		mesh.setLinearPosition(new Vector2D(x,y));
		for(int i=0;i<p2Vertices.size();i++) {
			mesh.addVertices(p2Vertices.get(i).sub(mesh.getLinearPosition()));
		}
		
		normals = new LinkedList<>();
		for(int i=0;i<p2Vertices.size();i++) {
			mesh.addNormals(new Vector2D(0,0,1)
						.crossProduct( p2Vertices.get((i+1)%p2Vertices.size())
								       .sub(p2Vertices.get(i))
						).normalize());
		}
		p2.setLinearVelocity(
				polygon.getLinearVelocity()
				.add(   polygon.getAngularVelocity()
						.crossProduct(mesh.getLinearPosition().sub(polygon.getMesh().getLinearPosition()))
						)
				);
		p2.setDensity(polygon.getDensity());
		p2.calcInertia();
		polygons.add(p2);
		
	}
	
	public LinkedList<Polygon> getPolygons() {
		return polygons;
	}
	
	
}
