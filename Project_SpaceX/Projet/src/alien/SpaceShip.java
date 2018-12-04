package alien;

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
	}

}
