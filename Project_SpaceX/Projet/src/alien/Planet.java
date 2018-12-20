package alien;

import java.io.Serializable;
import java.util.ArrayList;

import formes.Circle;
import formes.Point2D;

public class Planet implements Serializable {

	
	
	private int productionRate;
	private int player;
	private ArrayList<SpaceShip> spaceShips;
	private int nbSpaceShips;
	private Sprite sprite;
	private Circle c;
	private boolean selected;
	
	
	
	/*
	 * Constructeur de planete avec deux entiers et un sprite en parametre
	 * @param rate		L'entier qui correspond a vitesse de production
	 * @param player	L'entier qui correspond au joueur auquel appartien la planete
	 * @param prite 	Le srite associé a la planete
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
	}
	
	
	
	/*
	 * Methode qui informe si la planete a etait selectionnée
	 * @return		Vrai si elle l'a ete, faux sinon
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/*
	 * Methode qui selectionne la planete
	 */
	public void select() {
		this.selected = true;
	}
	
	/*
	 * Methode qui de deselectionne la planete
	 */
	public void unSelect() {
		this.selected = false;
	}
	
	
	
	/*
	 * Methode qui informe sur le nomber de vaisseaux que possède la planete
	 * @return		 L'entier qui correspond au nombre de vaisseaux
	 */
	public int getNbSpaceShips() {
		return nbSpaceShips;
	}

	/*
	 * Methode qui initialise le nombre de vaisseaux
	 * @param nbSpaceShips	 	L'entier qui correspond au nombre de vaisseaux auquel la planete doit etre initialisee			
	 */
	public void setNbSpaceShips(int nbSpaceShips) {
		this.nbSpaceShips = nbSpaceShips;
	}
	
	/*
	 * Methode qui incremente de 1 le nombre de vaisseaux de la planete
	 */
	public void productShip() {
		this.nbSpaceShips ++;
	}
	
	/*
	 * Methode qui initie l'envoie de vaisseaux en dirrection d'une autre planete
	 * @param p			La planete cible
	 * @param number 	L'entier qui correspond a la quantite de vaisseaux deployes
	 */
	public void sendShip(Planet p, int number) {
		this.nbSpaceShips -= number;
		if (this.player != p.getPlayer()) {
			for (int i = 0 ; i < number ; i++) {
				p.getAttacked();
			}
		}
		else {
			for(int i = 0 ;i < number ; i++) {
				p.productShip();
			}
		}
	}
	
	/*
	 * Methode qui gere l'interaction d'un vaisseau qui n'appartien pas au proprietaire de la planete et donc decremente le nombre de vaisseaux
	 * qu'elle possede
	 */
	public void  getAttacked() {
		this.nbSpaceShips--;
	}
	
	
	/*
	 * Methode qui informe sur le proprietaire de la planete
	 * @return 	L'entier qui correspond au proprietaire 
	 */
	public int getPlayer() {
		return this.player;
	}
	
	/*
	 * Methode qui initie le proprietaire de la planete
	 */
	public void setPlayer(int p) {
		this.player = p;
	}
	
	
	
	/*
	 * Methode qui informe sur le Cercle definit par la planete definissant une surface physique pour cette derniere pour cette derniere permettant de gere les 
	 * interaction avec d'autre objets
	 * @return	Le cercle qui correspond a la planete
	 */
	public Circle getCircle() {
		return c;
	}
	
	
	
	/*
	 * Methode qui informe sur la vitesse de production de la planete
	 * @return 	L'entier qui correspond au taux de production
	 */
	public int getProductionRate() {
		return productionRate;
	}

	/*
	 * Methode qui permet d'initialiser le taux de production de la planete
	 * @param productionRate	L'entier qui correspond au taut de production 
	 */
	public void setProductionRate(int productionRate) {
		this.productionRate = productionRate;
	}
	
	
	
	/*
	 * Methode qui informe sur le sprite correspondant a la planete
	 * @return 	Le sprite qui correspond a la planete
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/*
	 * Methode qui permet d'initialiser le sprite de la planete
	 * @param s 	Le sprite que l'on souhaite associer a la planete
	 */
	public void setSprite(Sprite s) {
		this.sprite = s;
	}
	
	

}
