package alien;

import java.io.Serializable;

import formes.Point2D;

public class SpaceShip implements Serializable {
	
	
	private static final long serialVersionUID = 2244946884762466707L;
	private int speed;
	private Sprite sprite;
	private Point2D p;
	private int player;
	private Planet destination;
	private Planet start;
	
	
	
	/**
	 * Constructeur de vaisseau avec une vitesse, une planete de destination et un sprite associe
	 * par définition, un vaisseau cree n'appartient a aucun joueur
	 * @param s			vitesse du vaisseau
	 * @param p			Planete de destination du vaisseau
	 * @param sprite 	Sprite associe au vaisseau
	 */
	public SpaceShip(int s, Planet p, Sprite sprite) {
		this.speed = s;
		this.sprite = sprite;
		this.sprite.setSpeed(this.sprite.getXSpeed() * speed, this.sprite.getYSpeed());
		this.player = -1;
		this.destination = p;
	}

	
	
	/**
	 * Methode qui informe sur le proprietaire du vaisseau
	 */
	public int getPlayer() {
		return this.player;
	}
	
	/**
	 * Methode qui modifie le proprietaire du vaisseau
	 * @param player 	numero du joueur
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
	
	
	
	/**
	 * Methode qui modifie le sprite associe au vaisseau
	 * @param sprite 	sprite que l'on souhaite associer au vaisseau
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Methode qui informe sur le sprite associe au vaisseau
	 * @return 	sprite associe au vaisseau
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	
	
	
	/**
	 * Methode qui modifie la position du vaisseau sous la forme d'un point
	 */
	public void setPosition() {
		this.p = new Point2D(this.sprite.getX(), this.sprite.getY());
	}
	
	/**
	 * Methode qui informe sur la position du vaisseau
	 * @return 	Le point qui correspond a la position du vaisseau
	 */
	public Point2D getPosition() {
		return this.p;
	}
	
	

	/**
	 * Methode qui informe sur la planete de destination du vaisseau
	 * @return planete de destinantion
	 */
	public Planet getDestination() {
		return destination;
	}
	
	/**
	 * Methode qui modifie la planete de destiniantion
	 * @param destination	nouvelle planete de destination
	 */
	public void setDestination(Planet destination) {
		this.destination = destination;
	}
	
	/**
	 * Methode qui informe sur la planete de depart du vaisseau
	 * @return planete de depart
	 */
	public Planet getStart() {
		return start;
	}

	/**
	 * Methode qui modifie la planete de depart
	 * @param destination	nouvelle planete de depart
	 */
	public void setStart(Planet start) {
		this.start = start;
	}
	
	
	
	/**
	 * Methode qui modifie la vitesse et la trajectoire du vaisseau en fonction de sa vitesse 
	 * et de sa position par rapport a sa planete de destination
	 */
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
	
	/**
	 * methode gerant l'envoi des vaisseaux dans l'espace en leur configurant une vitesse de base
	 */
	public void lauch() {
		if (this.getSprite().getX() < start.getCircle().getCenter().getX()) {
			if (this.getSprite().getY() < start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(-0.5, -0.5);
			}
			if (this.getSprite().getY() > start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(-0.5, 0.5);
			}
			if (this.getSprite().getY() == start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(-0.5, 0);
			}
		}
		if (this.getSprite().getX() > start.getCircle().getCenter().getX()) {
			if (this.getSprite().getY() < start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(0.5, -0.5);
			}
			if (this.getSprite().getY() > start.getCircle().getCenter().getY()){
				this.getSprite().setSpeed(0.5, 0.5);
			}
			if (this.getSprite().getY() == start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(0.5, 0);
			}
		}
		if (this.getSprite().getX() == start.getCircle().getCenter().getX()) {
			if (this.getSprite().getY() < start.getCircle().getCenter().getY()) {
				this.getSprite().setSpeed(0, -0.5);
			}
			if (this.getSprite().getY() > start.getCircle().getCenter().getY()){
				this.getSprite().setSpeed(0, 0.5);
			}
		}
	}
	

	/**
	 * methode qui permet a un vaisseau d'eviter une planete quand celle ci n'est pas sa planete de destination
	 * @param p planete a evite par le vaisseau
	 */
	public void evitate(Planet p) {
		double xDestination;
		double yDestination;
		
/*		if (this.getSprite().getX() <= p.getCircle().getCenter().getX()) {
			if (this.getSprite().getXSpeed() != 0) {
				xDestination = this.getSprite().getX() + ((p.getCircle().getCenter().getX() - this.getSprite().getX()) * 2);
			} else {
				xDestination = this.getSprite().getX();
			}
			if (this.getSprite().getY() <= p.getCircle().getCenter().getY()) {
				if (this.getSprite().getYSpeed() != 0) {
					yDestination = this.getSprite().getY() + ((p.getCircle().getCenter().getY() - this.getSprite().getY()) * 2);
				} else {
					yDestination = this.getSprite().getY();
				}
			} else {
				if (this.getSprite().getYSpeed() != 0) {
					yDestination = this.getSprite().getY() - ((this.getSprite().getY() - p.getCircle().getCenter().getY()) * 2);
				} else {
					yDestination = this.getSprite().getY();
				}
			}
			this.getSprite().setX(xDestination);
			this.getSprite().setY(yDestination);
			
		} else {
			
			if (this.getSprite().getXSpeed() != 0) {
				xDestination = this.getSprite().getX() - ((this.getSprite().getX() - p.getCircle().getCenter().getX()) * 2);
			} else {
				xDestination = this.getSprite().getX();
			}
			if (this.getSprite().getY() <= p.getCircle().getCenter().getY()) {
				if (this.getSprite().getYSpeed() != 0) {
					yDestination = this.getSprite().getY() + ((p.getCircle().getCenter().getY() - this.getSprite().getY()) * 2);
				} else {
					yDestination = this.getSprite().getY();
				}
			} else {
				if (this.getSprite().getYSpeed() != 0) {
					yDestination = this.getSprite().getY() - ((this.getSprite().getY() - p.getCircle().getCenter().getY()) * 2);
				} else {
					yDestination = this.getSprite().getY();
				}
			}
			
			if (this.getSprite().getXSpeed() != 0 && this.getSprite().getYSpeed() != 0) {
				
				if ((xDestination >= this.destination.getCircle().getCenter().getX() || xDestination <= this.destination.getCircle().getCenter().getX())  && yDestination < p.getCircle().getCenter().getY() && yDestination > this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX();
					yDestination = p.getCircle().getCenter().getY() - p.getCircle().getRadius() -10;
				}
				if ((xDestination >= this.destination.getCircle().getCenter().getX() || xDestination <= this.destination.getCircle().getCenter().getX()) && yDestination > p.getCircle().getCenter().getY() && yDestination < this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX();
					yDestination = p.getCircle().getCenter().getY() + p.getCircle().getRadius() + 10;
				}
				if ((yDestination >= this.destination.getCircle().getCenter().getY() || yDestination <= this.destination.getCircle().getCenter().getY()) && xDestination < p.getCircle().getCenter().getX() && xDestination > this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX() - p.getCircle().getRadius() - 10;
					yDestination = p.getCircle().getCenter().getY();
				}
				if ((yDestination >= this.destination.getCircle().getCenter().getY() || yDestination <= this.destination.getCircle().getCenter().getY()) && xDestination > p.getCircle().getCenter().getX() && xDestination < this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX() + p.getCircle().getRadius() + 10;
					yDestination = p.getCircle().getCenter().getY();
				}
				
			}
			
			this.getSprite().setX(xDestination);
			this.getSprite().setY(yDestination);
		}
		*/
		
			if (this.getSprite().getXSpeed() != 0) {
				xDestination = this.getSprite().getX() + ((p.getCircle().getCenter().getX() - this.getSprite().getX()) * 2);
			} else {
				xDestination = this.getSprite().getX();
			}
			
			if (this.getSprite().getYSpeed() != 0) {
				yDestination = this.getSprite().getY() + ((p.getCircle().getCenter().getY() - this.getSprite().getY()) * 2);
			} else {
				yDestination = this.getSprite().getY();
			}
			
			if (this.getSprite().getXSpeed() != 0 && this.getSprite().getYSpeed() != 0) {
				
				if ((xDestination >= this.destination.getCircle().getCenter().getX() || xDestination <= this.destination.getCircle().getCenter().getX())  && yDestination < p.getCircle().getCenter().getY() && yDestination > this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX();
					yDestination = p.getCircle().getCenter().getY() - p.getCircle().getRadius() -10;
				}
				if ((xDestination >= this.destination.getCircle().getCenter().getX() || xDestination <= this.destination.getCircle().getCenter().getX()) && yDestination > p.getCircle().getCenter().getY() && yDestination < this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX();
					yDestination = p.getCircle().getCenter().getY() + p.getCircle().getRadius() + 10;
				}
				if ((yDestination >= this.destination.getCircle().getCenter().getY() || yDestination <= this.destination.getCircle().getCenter().getY()) && xDestination < p.getCircle().getCenter().getX() && xDestination > this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX() - p.getCircle().getRadius() - 10;
					yDestination = p.getCircle().getCenter().getY();
				}
				if ((yDestination >= this.destination.getCircle().getCenter().getY() || yDestination <= this.destination.getCircle().getCenter().getY()) && xDestination > p.getCircle().getCenter().getX() && xDestination < this.destination.getCircle().getCenter().getY()) {
					xDestination = p.getCircle().getCenter().getX() + p.getCircle().getRadius() + 10;
					yDestination = p.getCircle().getCenter().getY();
				}
				
			}
			
			this.getSprite().setX(xDestination);
			this.getSprite().setY(yDestination);
	}

}
