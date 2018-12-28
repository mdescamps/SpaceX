package alien;

import java.io.Serializable;
import java.util.ArrayList;

import formes.Circle;
import formes.Point2D;

public class Planet implements Serializable {

	private static final long serialVersionUID = -6763150003302789101L;
	private int productionRate;
	private int productionRateUpgrades;
	private int player;
	private ArrayList<SpaceShip> spaceShips;
	private int nbSpaceShips;
	private int SpaceShipsFirePower;
	private Sprite sprite;
	private Circle c;
	private boolean selected;
	private int Shield;
	
	
	
	/**
	 * Constructeur de planete
	 * @param rate		vitesse de production
	 * @param player	joueur auquel appartient la planete
	 * @param sprite 	sprite associe a la planete
	 */
	public Planet(int rate, int player, Sprite sprite) {
		this.productionRate = rate;
		this.player = player;
		this.spaceShips = new ArrayList<SpaceShip>();
		this.nbSpaceShips = this.spaceShips.size();
		this.sprite = sprite;
		Point2D center = new Point2D(sprite.getX() + (sprite.width()/2) - 10, sprite.getY() + (sprite.height()/2) - 10);
		this.c = new Circle(center,(sprite.height() / 2));
		this.selected = false;
		this.Shield = 0;
		this.SpaceShipsFirePower = 1;
	}
	
	
	
	/**
	 * Methoden qui informe sur la puissance des vaisseaux d'une planete
	 * @param SpaceShipsFirePower 	Un entier qui correspond a la puissance des vaisseaux de la planete
	 */
	public void addFirePowerUpgrade() {
		this.nbSpaceShips -= 50;
		this.SpaceShipsFirePower++;
	}
	
	/**
	 * Methode qui retourne la puissance des vaisseaux de la planete
	 * @return		Un entier qui correspon a la puissance des vaisseaux
	 */
	public int getSpaceShipsFirePower() {
		return this.SpaceShipsFirePower;
	}
	
	
	
	/**
	 * Methode qui active le bouclier d'une planete 
	 */
	public void setShield(int Shield) {
		if (this.nbSpaceShips > 100) {
			this.nbSpaceShips -= 100;
			this.Shield = Shield;
		}
	}
	
	/**
	 * Methode qui retourne l'etat du bouclier
	 * @return		Un entier qui correspond a l'etat du bouclier
	 */
	public int getShield() {
		return this.Shield;
	}
	
	public void dammageShield() {
		this.Shield -= 1;
	}
	
	
	
	/**
	 * Methode qui informe si la planete a etait selectionnee
	 * @return		Vrai si elle l'a ete, faux sinon
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Methode qui selectionne la planete
	 */
	public void select() {
		this.selected = true;
	}
	
	/**
	 * Methode qui deselectionne la planete
	 */
	public void unSelect() {
		this.selected = false;
	}
	
	
	
	/**
	 * Methode qui informe sur le nombre de vaisseaux que possede la planete
	 * @return	nombre de vaisseaux
	 */
	public int getNbSpaceShips() {
		return nbSpaceShips;
	}

	/**
	 * Methode qui modifie le nombre de vaisseaux
	 * @param nbSpaceShips  nombre de vaisseaux auquel la planete doit etre modifiee			
	 */
	public void setNbSpaceShips(int nbSpaceShips) {
		this.nbSpaceShips = nbSpaceShips;
	}
	
	/**
	 * Methode qui incremente de 1 le nombre de vaisseaux de la planete
	 * (utilisee pour les interactions vaisseau/planete)
	 */
	public void productShip(int prod) {
		this.nbSpaceShips += prod;
	}
	

	
	/**
	 * Methode qui gere l'interaction d'un vaisseau qui n'appartient pas au proprietaire de la planete 
	 * et donc decremente le nombre de vaisseaux qu'elle possede
	 */
	public void  getAttacked(int power) {
		this.nbSpaceShips -= power;
	}
	
	
	/**
	 * Methode qui informe sur le proprietaire de la planete
	 * @return 	numero du joueur
	 */
	public int getPlayer() {
		return this.player;
	}
	
	/**
	 * Methode qui modifie le proprietaire de la planete
	 * @param p  Numero du joueur à modifier
	 */
	public void setPlayer(int p) {
		this.player = p;
	}
	
	
	
	/**
	 * Methode qui retourne le cercle definit pour la planete definissant une surface physique pour cette derniere 
	 * permettant de gerer les interactions avec d'autres objets
	 * @return	Le cercle qui correspond a la planete
	 */
	public Circle getCircle() {
		return c;
	}
	
	
	
	/**
	 * Methode qui informe sur la vitesse de production de la planete
	 * @return 	taux de production
	 */
	public int getProductionRate() {
		return productionRate;
	}

	/**
	 * Methode qui permet de modifier le taux de production de la planete
	 * @param productionRate	nouveau taux de production 
	 */
	public void setProductionRate(int productionRate) {
		this.productionRate = productionRate;
	}
	
	/**
	 * Methode qui modifie la vitesse de production de la planete
	 * @param Rate		Un entier qui correspond a la quantite dont il faut modifier la production
	 */
	public void upgradeProductionRate(int Rate) {
		this.nbSpaceShips -= 50;
		this.productionRate -= Rate;
	}
	
	/**
	 * Methode qui exprime le fait que la production de la planete a etait amelioree par l'augmentation d'un entier
	 */
	public void addProductionRateUpgrade() {
		this.productionRateUpgrades++;
	}
	
	/**
	 * Methode qui informe sur le nombre de fois qu'a etait ameliore la production de la planete
	 * @return	Un entier qui correspond au nombre d'amelioration de la production
	 */
	public int getProductionRateUpgrade() {
		return this.productionRateUpgrades;
	}
	
	
	
	/**
	 * Methode qui informe sur le sprite correspondant a la planete
	 * @return 	sprite associe a la planete
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Methode qui permet de modifier le sprite associé de la planete
	 * @param s 	nouveau sprite que l'on souhaite associer a la planete
	 */
	public void setSprite(Sprite s) {
		this.sprite = s;
	}


}
