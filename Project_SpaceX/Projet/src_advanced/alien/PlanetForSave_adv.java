package alien;

import java.io.Serializable;

import formes.Circle_adv;

public class PlanetForSave_adv  implements Serializable {
	
	private static final long serialVersionUID = 5673132420682377142L;
	private int productionRate;
	private int player;
	private int nbSpaceShips;
	private SpriteForSave_adv sprite;
	private Circle_adv c;
	
	public PlanetForSave_adv(int rate, int player, SpriteForSave_adv sprite, Circle_adv c, int nb) {
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

	public SpriteForSave_adv getSprite() {
		return sprite;
	}

	public Circle_adv getC() {
		return c;
	}
	
	public static Planet_adv convertForInput(PlanetForSave_adv pfs, Sprite_adv s) {
		Planet_adv p =  new Planet_adv(pfs.getProductionRate(), pfs.getPlayer(), s);
		p.setNbSpaceShips(pfs.getNbSpaceShips());
		return p;
	}

}
