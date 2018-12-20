package formes;

import java.io.Serializable;

public class Circle implements Serializable {
	
	private Point2D p;
	private double radius;
	
	public Circle(Point2D p, double r) {
		this.p = new Point2D(p.getX(), p.getY());
		this.radius = r;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Point2D getCenter() {
		return this.p;
	}
	
	public void move(Point2D p) {
		this.p = new Point2D(p.getX(), p.getY());
	}
	
	public void translate(int dx, int dy) {
		
	}
	
	public double perimeter() {
		return Math.PI * (2 * this.radius);
	}
	
	public double area() {
		return Math.PI * Math.pow(this.radius, 2);
	}
	
	@Override
	public String toString() {
		return "Circle : radius " + this.radius + ", perimeter : " + this.perimeter() + ", area : " + this.area();
	}

	public boolean equals(Circle c) {
		if (c == null || c.getClass() != this.getClass()) {
			return false;
		}
		return (this.getCenter().equals(c.getCenter()) && this.getRadius() == c.getRadius());
	}

	public boolean isInside(Point2D p) {
		return (this.getCenter().distance(p) <= this.getRadius());
	}
	
	public boolean isNear(Point2D p) {
		return (this.getCenter().distance(p) <= this.getRadius() + 20);
	}

}
