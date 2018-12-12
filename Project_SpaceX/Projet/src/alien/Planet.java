package alien;

import java.util.ArrayList;

import formes.Circle;
import formes.Point2D;

public class Planet {

	private int productionRate;
	private int player;
	private ArrayList<SpaceShip> spaceShips;
	private int nbSpaceShips;
	private Sprite sprite;
	private Circle c;
	
	public Planet(int rate, int p, Sprite sprite) {
		this.productionRate = rate;
		this.player = p;
		this.spaceShips = new ArrayList<SpaceShip>();
		this.nbSpaceShips = this.spaceShips.size();
		this.sprite = sprite;
		Point2D center = new Point2D(sprite.getX() + (sprite.width()/2), sprite.getY() + (sprite.height()/2));
		this.c = new Circle(center,(sprite.height() / 2));
	}
	
	public int getNbSpaceShips() {
		return nbSpaceShips;
	}

	public void setNbSpaceShips(int nbSpaceShips) {
		this.nbSpaceShips = nbSpaceShips;
	}
	
	public int getPlayer() {
		return this.player;
	}
	
	public Circle getCircle() {
		return c;
	}
	
	public int getProductionRate() {
		return productionRate;
	}

	public void setProductionRate(int productionRate) {
		this.productionRate = productionRate;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite s) {
		this.sprite = s;
	}

	public void productShip() {
		this.spaceShips.add(new SpaceShip(10, 10, 10));
		this.nbSpaceShips ++;
	}
	
	public void sendShip(Planet p) {
		this.spaceShips.remove(this.nbSpaceShips);
		this.nbSpaceShips --;
		if (this.player == p.player) {
			p.nbSpaceShips++;
		} else {
			p.nbSpaceShips--;			
		}
	}

}
