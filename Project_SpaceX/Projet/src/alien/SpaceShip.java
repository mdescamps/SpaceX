package alien;

import formes.Point2D;

public class SpaceShip {
	
	private int speed;
	private Sprite sprite;
	private Point2D p;
	private int player;
	private Planet destination;
	private Planet start;
	
	public SpaceShip(int s, Planet p, Sprite sprite) {
		this.speed = s;
		this.sprite = sprite;
		this.sprite.setSpeed(this.sprite.getXSpeed() * speed, this.sprite.getYSpeed());
		this.player = 0;
		this.destination = p;
	}
	
	public int getPlayer() {
		return this.player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
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

	public Planet getDestination() {
		return destination;
	}

	public void setDestination(Planet destination) {
		this.destination = destination;
	}
	
	public Planet getStart() {
		return start;
	}

	public void setStart(Planet start) {
		this.start = start;
	}

	public void travel() {
		if (this.sprite.getXSpeed() < 0 && this.sprite.getX() <= this.destination.getCircle().getCenter().getX()) {
			this.getSprite().setSpeed(0,this.getSprite().getYSpeed() * 2);
		}
		if (this.sprite.getXSpeed() > 0 && this.sprite.getX() >= this.destination.getCircle().getCenter().getX()) {
			this.getSprite().setSpeed(0,this.getSprite().getYSpeed() * 2);
		}
		if (this.sprite.getYSpeed() < 0 && this.sprite.getY() <= this.destination.getCircle().getCenter().getY()) {
			this.getSprite().setSpeed(this.getSprite().getXSpeed() * 2,0);
		}
		if (this.sprite.getYSpeed() > 0 && this.sprite.getY() >= this.destination.getCircle().getCenter().getY()) {
			this.getSprite().setSpeed(this.getSprite().getXSpeed() * 2,0);
		}
		
	}
	
	public void lauch() {
		if (this.getSprite().getX() < start.getCircle().getCenter().getX()) {
			if (this.getSprite().getY() < start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(-0.5, -0.5);
			} else {
				this.getSprite().setSpeed(-0.5, 0.5);
			}
		} else {
			if (this.getSprite().getY() < start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(0.5, -0.5);
			} else {
				this.getSprite().setSpeed(0.5, 0.5);
			}
		}
	}

}
