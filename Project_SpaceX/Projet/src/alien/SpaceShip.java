package alien;

import formes.Point2D;
import formes.Triangle;

public class SpaceShip {
	
	private int speed;
	private int productionSpeed;
	private int firePower;
	private Sprite sprite;
	private Triangle triangle;
	private Planet origin;
	private Planet destination;
	
	public SpaceShip(int s, int production, int power) {
		this.speed = s;
		this.productionSpeed = production;
		this.firePower = 1;
		this.origin = null;
		this.destination = null;
		
	}
	
	public void setDestination(Planet destination) {
		this.destination = destination;
	}
	
	public void setOrigin(Planet origin) {
		this.origin = origin;
	}
	
	

}
