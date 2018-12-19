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
	private boolean selected;
	
	public Planet(int rate, int p, Sprite sprite) {
		this.productionRate = rate;
		this.player = p;
		this.spaceShips = new ArrayList<SpaceShip>();
		this.nbSpaceShips = this.spaceShips.size();
		this.sprite = sprite;
		Point2D center = new Point2D(sprite.getX() + (sprite.width()/2) - 10, sprite.getY() + (sprite.height()/2) - 10);
		this.c = new Circle(center,(sprite.height() / 2));
		this.selected = false;
	}
	
	
	
	public boolean isSelected() {
		return selected;
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void unSelect() {
		this.selected = false;
	}
	
	
	
	public int getNbSpaceShips() {
		return nbSpaceShips;
	}

	public void setNbSpaceShips(int nbSpaceShips) {
		this.nbSpaceShips = nbSpaceShips;
	}
	
	public void productShip() {
		this.nbSpaceShips ++;
	}
	
	public void sendShip(Planet p, int number) {
		this.nbSpaceShips -= number;
		if (this.player != p.getPlayer()) {
			for (int i = 0 ; i < number ; i++) {
				p.getAttacked();
			}
		}
		else {
			for(int i = 0 ;i < number ; i++) {
				p.getHelped();
			}
		}
	}
	
	public void getHelped() {
		this.nbSpaceShips++;
	}
	
	public void  getAttacked() {
		this.nbSpaceShips--;
	}
	
	
	
	public void setPlayer(int player) {
		this.player = player;
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
	
	

}
