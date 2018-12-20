package formes;

import java.io.Serializable;

public class Point2D implements Serializable {
    private double x, y;
    
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2D(Point2D p){
        this(p.x, p.y);
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
    
    public double area() {
		return 0;
	}

	public double perimeter() {
		return 0;
	}
    
    public void translate(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }

    @Override
    public String toString() {
        return ( "Point2D (" + x + ", "  + y + ")");
    }

    public double distance(Point2D p){
        double d1 = p.x - x;
        double d2 = p.y - y;
        return Math.sqrt(d1*d1 + d2*d2);
   }
 
    public boolean equals(Point2D p) {
    	if (p == null || p.getClass() != this.getClass()) {
    		return false;
    	}
    	return (this.getX() == p.getX() && this.getY() == p.getY());
    }

	public boolean isInside(Point2D p) {
		return this.equals(p);
	}

}
