package formes;

import java.io.Serializable;

public class Circle implements Serializable {
	
	private static final long serialVersionUID = 8078636859780901686L;
	private Point2D p;
	private double radius;
	
	
	/**
	 * Constructeur de Cercle prenant en parametre un point2D et un reel
	 * @param p 	Le point qui est le centre du cercle
	 * @param r		rayon du cercle
	 */
	public Circle(Point2D p, double r) {
		this.p = new Point2D(p.getX(), p.getY());
		this.radius = r;
	}
	
	
	
	/**
	 * Methode qui informe sur le rayon du cercle
	 * @return rayon du cercle
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Methode qui informe sur le point qui symbolise le centre du cercle
	 * @return 	Le point qui correspond au centre du cercle
	 */
	public Point2D getCenter() {
		return this.p;
	}
	
	
	
	/**
	 * Methode qui modifie la position du centre du cercle en creeant un nouveau point
	 * @param p 	nouveau point qui sert de centre
	 */
	public void move(Point2D p) {
		this.p = new Point2D(p.getX(), p.getY());
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Circle : radius " + this.radius;
	}
	
	/**
	 * Methode qui compare deux cercle
	 * @param c 	Le Cercle auquel on souhaite le comparer
	 * @return 		Vrais si les cercles sont exactement similaire (position / rayon) Faux sinon
	 */
	public boolean equals(Circle c) {
		if (c == null || c.getClass() != this.getClass()) {
			return false;
		}
		return (this.getCenter().equals(c.getCenter()) && this.getRadius() == c.getRadius());
	}
	
	/**
	 * Methode qui informe si un point est a l'interrieur d'un cercle
	 * @param p 	Le point dont on veut savoir sa position par rapport au cercle
	 * @return 		Vrai si le point est dans le cercle, Faux sinon
	 */
	public boolean isInside(Point2D p) {
		return (this.getCenter().distance(p) <= this.getRadius());
	}
	
	/**
	 * Methode qui infome sur la proximite d'un point avec le cercle
	 * @param p 	Le point dont on veut comparer la position par rapport au cercle
	 * @return 		Vrais si le point est assez proche (ici distance de 10 pixels) du cerlce, Faux sinon
	 */
	public boolean isNear(Point2D p) {
		return (this.getCenter().distance(p) <= this.getRadius() + 20);
	}

}
