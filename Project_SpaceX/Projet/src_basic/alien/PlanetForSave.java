package alien;

import java.io.Serializable;

import formes.Circle;

public class PlanetForSave  implements Serializable {
	
	private static final long serialVersionUID = 5673132420682377142L;
	private int productionRate;
	private int player;
	private int nbSpaceShips;
	private SpriteForSave sprite;
	private Circle c;
	
	public PlanetForSave(int rate, int player, SpriteForSave sprite, Circle c, int nb) {
		this.productionRate = rate;
		this.player = player;
		this.nbSpaceShips = nb;
		this.sprite = sprite;
		this.c = c;
	}

	public int getProductionRate() {
		return productionRate;
	}

	public int getPlayer() {
		return player;
	}

	public int getNbSpaceShips() {
		return nbSpaceShips;
	}

	public SpriteForSave getSprite() {
		return sprite;
	}

	public Circle getC() {
		return c;
	}
	
	public static Planet convertForInput(PlanetForSave pfs, Sprite s) {
		Planet p =  new Planet(pfs.getProductionRate(), pfs.getPlayer(), s);
		p.setNbSpaceShips(pfs.getNbSpaceShips());
		return p;
	}

}
