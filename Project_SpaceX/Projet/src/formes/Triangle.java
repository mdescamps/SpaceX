package formes;

import java.io.FileWriter;
import java.io.IOException;

public class Triangle {
	
	private Point2D p1, p2, p3;
	
	public Triangle() {
		
	}
	
	public Triangle(Point2D p1, Point2D p2, Point2D p3) {
		this.p1 = new Point2D(p1.getX(), p1.getY());
		this.p2 = new Point2D(p2.getX(), p2.getY());
		this.p3 = new Point2D(p3.getX(), p3.getY());
	}
	
	public Point2D getp1() {
		return p1;
	}
	
	public Point2D getp2() {
		return p2;
	}
	
	public Point2D getp3() {
		return p3;
	}
	
	public double area() {
		double a = p1.distance(p2);
		double b = p1.distance(p3);
		double c = p2.distance(p3);
		double p = (a + b + c)/2;
		return Math.sqrt(p * (p - a) * (p - b) * (p - c));
	}
	
	public double perimeter() {
		return p1.distance(p2) + p1.distance(p3) + p2.distance(p3);
	}
	
	public void translate(double dx, double dy) {
		p1.setX(p1.getX() + dx);
		p1.setY(p1.getY() + dy);
		p2.setX(p2.getX() + dx);
		p2.setY(p2.getY() + dy);
		p3.setX(p3.getX() + dx);
		p3.setY(p3.getY() + dy);
	}
	
	public String toString() {
		return "Triangle (" + p1 + ";" + p2 + ";" + p3 + ")";
	}

	public Point2D[] verticales() {
		Point2D[] t = new Point2D[3];
		t[0] = p1;
		t[1] = p2;
		t[2] = p3;
		return t;
	}

	public boolean isInside(Point2D p) {
		Triangle t1 = new Triangle(p,p1,p2);
		Triangle t2 = new Triangle(p,p1,p3);
		Triangle t3 = new Triangle(p,p2,p3);
		return ( this.area() == t1.area() + t2.area() +  t3.area());
	}

	public void svg() throws IOException {
		FileWriter out = new FileWriter("circle.svg");
		out.write("<?xml version='1.0' encoding='utf-8'?>\n");
		out.write("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='100' height='100'>");
		out.write("<circle cx='50' cy='50' r='40' />"); 
		out.write("</svg>");
		out.close();
		
	}
	
}
