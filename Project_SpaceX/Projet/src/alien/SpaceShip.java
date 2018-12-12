package alien;

import formes.Point2D;
import formes.Triangle;

public class SpaceShip {
	
	private int speed;
	private int productionSpeed;
	private int firePower;
	private Sprite sprite;
	
	
	public SpaceShip(int s, int production, int power, Sprite sprite) {
		this.speed = s;
		this.productionSpeed = production;
		this.firePower = 1;
		this.sprite = sprite;
		this.sprite.setSpeed(this.sprite.getXSpeed() * speed, this.sprite.getYSpeed());
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}

}
