package alien;

import formes.Point2D;
import formes.Triangle;

public class SpaceShip {
	
	private int speed;
	private Sprite sprite;
	private Point2D p;
	
	
	public SpaceShip(int s, Sprite sprite) {
		this.speed = s;
		this.sprite = sprite;
		this.sprite.setSpeed(this.sprite.getXSpeed() * speed, this.sprite.getYSpeed());
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public void setPosition() {
		this.p = new Point2D(this.sprite.getX(), this.sprite.getY());
	}
	
	public Point2D getPosition() {
		return this.p;
	}

}
