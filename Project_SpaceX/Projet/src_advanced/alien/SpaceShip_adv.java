package alien;

import java.io.Serializable;

import formes.Point2D_adv;

public class SpaceShip_adv implements Serializable {
	
	
	private static final long serialVersionUID = 2244946884762466707L;
	private int speed;
	private int firePower;
	private Sprite_adv sprite;
	private Point2D_adv p;
	private int player;
	private Planet_adv destination;
	private Planet_adv start;
	private boolean evitate;
	double[] dest = new double[2];
	double initX;
	double initY;
	double initSX;
	double initSY;
	double cX;
	double cY;
	
	
	
	/**
	 * Constructeur de vaisseau avec une vitesse, une planete de destination et un sprite associe
	 * par définition, un vaisseau cree n'appartient a aucun joueur
	 * @param s			vitesse du vaisseau
	 * @param p			Planete de destination du vaisseau
	 * @param sprite 	Sprite associe au vaisseau
	 */
	public SpaceShip_adv(int s, Planet_adv p, Sprite_adv sprite, int firePower) {
		this.speed = s;
		this.sprite = sprite;
		this.sprite.setSpeed(this.sprite.getXSpeed() * speed, this.sprite.getYSpeed());
		this.player = -1;
		this.destination = p;
		this.evitate = false;
		this.firePower = firePower;
	}
	
	
	/**
	 * Methode qui infome sur la puissance du vaisseau
	 * @return	Un entier qui représente la puissance du vaisseau
	 */
	public int getFirePower() {
		return this.firePower;
	}
	
	/**
	 * Methode qui permet de modifier la puissance d'un vaisseau
	 * @param firePower 	Entier qui represente la nouvelle puissance du vaisseau
	 */
	public void setFirePower(int firePower) {
		this.firePower = firePower;
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
	public void setSprite(Sprite_adv sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Methode qui informe sur le sprite associe au vaisseau
	 * @return 	sprite associe au vaisseau
	 */
	public Sprite_adv getSprite() {
		return this.sprite;
	}
	
	
	
	/**
	 * Methode qui modifie la position du vaisseau sous la forme d'un point
	 */
	public void setPosition() {
		this.p = new Point2D_adv(this.sprite.getX(), this.sprite.getY());
	}
	
	/**
	 * Methode qui informe sur la position du vaisseau
	 * @return 	Le point qui correspond a la position du vaisseau
	 */
	public Point2D_adv getPosition() {
		return this.p;
	}
	
	

	/**
	 * Methode qui informe sur la planete de destination du vaisseau
	 * @return planete de destinantion
	 */
	public Planet_adv getDestination() {
		return destination;
	}
	
	/**
	 * Methode qui modifie la planete de destiniantion
	 * @param destination	nouvelle planete de destination
	 */
	public void setDestination(Planet_adv destination) {
		this.destination = destination;
	}
	
	/**
	 * Methode qui informe sur la planete de depart du vaisseau
	 * @return planete de depart
	 */
	public Planet_adv getStart() {
		return start;
	}

	/**
	 * Methode qui modifie la planete de depart
	 * @param destination	nouvelle planete de depart
	 */
	public void setStart(Planet_adv start) {
		this.start = start;
	}
	
	/**
	 * Methode qui informe si un vaisseau fait une manoeuvre d'evitement
	 * @return vrai si il est en train d'eviter, faux sinon
	 */
	public boolean isEvitate() {
		return this.evitate;
	}
	
	/**
	 * Methode qui modifie si un vaisseau entre en evitement
	 * @param e nouvelle valeur d'evitement
	 */
	public void setEvitate(boolean e) {
		this.evitate = e;
	}
	
	/**
	 * Methode qui donne la vitesse initiale avant evitement
	 * @return vitesse initiale en x
	 */
	public double getInitSX() {
		return initSX;
	}

	/**
	 * Methode qui informe de la vitesse initiale avant evitement
	 * @return vitesse initiale en y
	 */
	public double getInitSY() {
		return initSY;
	}

	/**
	 * modifie la destination de l'evitement
	 * @param dest nouvelle destination
	 */
	public void setDest(double[] dest) {
		this.dest = dest;
	}
	
	/**
	 * donne la destination suite a l'evitement
	 * @return la destination du vaisseau pour l'evitement
	 */
	public double[] getDest() {
		return this.dest;
	}

	/**
	 * modifie la position de base avant evitement
	 * @param initX position en x de base
	 */
	public void setInitX(double initX) {
		this.initX = initX;
	}

	/**
	 * modifie la position de base avant evitement
	 * @param initY position en y de base
	 */
	public void setInitY(double initY) {
		this.initY = initY;
	}

	/**
	 * modifie la vitesse de base avant evitement
	 * @param initSX vitesse de base en x
	 */
	public void setInitSX(double initSX) {
		this.initSX = initSX;
	}

	/**
	 * modifie la vitesse de base avant evitement
	 * @param initSY vitesse de base en y
	 */
	public void setInitSY(double initSY) {
		this.initSY = initSY;
	}

	/**
	 * modifie le centre de la planete a eviter
	 * @param cX coordonnee x du centre de la planete a eviter
	 */
	public void setcX(double cX) {
		this.cX = cX;
	}

	/**
	 * modifie le centre de la planete a eviter
	 * @param cY coordonnee y du centre de la planete a eviter
	 */
	public void setcY(double cY) {
		this.cY = cY;
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
	public double[] searchDest(Planet_adv p) {
		double[] dest = new double[2];
		double xDestination = 0;
		double yDestination = 0;
		double diffX;
		double diffY;		

		diffX = p.getCircle().getCenter().getX() - this.getSprite().getX();
		diffY = p.getCircle().getCenter().getY() - this.getSprite().getY();
		
		if (this.getSprite().getXSpeed() != 0 && this.getSprite().getYSpeed() != 0) {
			if (diffY < 0) {
				if (diffX < 0) {
					xDestination = p.getCircle().getCenter().getX() - Math.abs(diffY);
					yDestination = p.getCircle().getCenter().getY() - Math.abs(diffX);
				} else {
					xDestination = p.getCircle().getCenter().getX() + Math.abs(diffY);
					yDestination = p.getCircle().getCenter().getY() - Math.abs(diffX);
				}
			} else {
				if (diffX < 0) {
					xDestination = p.getCircle().getCenter().getX() - Math.abs(diffY);
					yDestination = p.getCircle().getCenter().getY() + Math.abs(diffX);
				} else {
					xDestination = p.getCircle().getCenter().getX() + Math.abs(diffY);
					yDestination = p.getCircle().getCenter().getY() + Math.abs(diffX);
				}
			}

		} else {
			if (this.getSprite().getXSpeed() == 0) {
				xDestination = this.getSprite().getX();
				yDestination = this.getSprite().getY() + diffY * 2;
			}
			if (this.getSprite().getYSpeed() == 0) {
				xDestination = this.getSprite().getX() + diffX * 2;
				yDestination = this.getSprite().getY();
			}
		}
		
		dest[0] = xDestination;
		dest[1] = yDestination;
		return dest;
	}


	/**
	 * ALGO D'EVITEMENT POUR MODIFIER LA VITESSE ET LA DIRECTION DU VAISSEAU 
	 * AFIN DE CONTOURNER UNE PLANETE
	 */
	public void evitate() {
		double r = 0;		
		if (initSY == 0) {
			r = Math.abs(cX - initX);
			if (dest[0] > initX && dest[1] < cY) {
				this.getSprite().setSpeed( 0, -0.5);
				if (this.getSprite().getY() <= cY - r) {
					this.getSprite().setSpeed(0.5, 0);
				} 
				if (this.getSprite().getX() >= dest[0]) {
					this.getSprite().setSpeed(0, 0.5);
				}
			}
			if (dest[0] > initX && dest[1] > cY) {
				this.getSprite().setSpeed( 0, 0.5);
				if (this.getSprite().getY() > cY + r) {
					this.getSprite().setSpeed(0.5, 0);
				} 
				if (this.getSprite().getX() > dest[0]) {
					this.getSprite().setSpeed(0, -0.5);
				}
			}
			if (dest[0] < initX && dest[1] < cY) {
				this.getSprite().setSpeed( 0, -0.5);
				if (this.getSprite().getY() <= cY - r) {
					this.getSprite().setSpeed(-0.5, 0);
				}
				if (this.getSprite().getX() <= dest[0]) {
					this.getSprite().setSpeed(0, 0.5);
				}
			}
			if (dest[0] < initX && dest[1] > cY) {
				this.getSprite().setSpeed( 0, 0.5);
				if (this.getSprite().getY() > cY + r) {
					this.getSprite().setSpeed(-0.5, 0);
				} 
				if (this.getSprite().getX() < dest[0]) {
					this.getSprite().setSpeed(0, -0.5);
				}
			}
		}
		
		if (initSX == 0) {
			r = Math.abs(cY - initY);
			if (dest[1] > initY && dest[0] < cX) {
				this.getSprite().setSpeed( -0.5, 0);
				if (this.getSprite().getX() <= cX - 1.5 * r) {
					this.getSprite().setSpeed(0, 0.5);
				}
				if (this.getSprite().getY() >= dest[1]) {
					this.getSprite().setSpeed(0.5, 0);
				}
			}
			if (dest[1] > initY && dest[0] > cX) {
				this.getSprite().setSpeed( 0.5, 0);
				if (this.getSprite().getX() >= cX + 1.5 *r) {
					this.getSprite().setSpeed(0, 0.5);
				}
				if (this.getSprite().getY() >= dest[1]) {
					this.getSprite().setSpeed(-0.5, 0);
				}
			}
			if (dest[1] < initY && dest[0] < cX) {
				this.getSprite().setSpeed( -0.5, 0);
				if (this.getSprite().getX() <= cX - 1.5 * r) {
					this.getSprite().setSpeed(0, -0.5);
				} 
				if (this.getSprite().getY() <= dest[1]) {
					this.getSprite().setSpeed(0.5, 0);
				}
			}
			if (dest[1] < initY && dest[0] > cX) {
				this.getSprite().setSpeed( 0.5, 0);
				if (this.getSprite().getX() >= cX + 1.5 * r) {
					this.getSprite().setSpeed(0, -0.5);
				} 
				if (this.getSprite().getY() <= dest[1]) {
					this.getSprite().setSpeed(-0.5, 0);
				}
			}
		}
		
		if (initSX != 0 && initSY != 0) {
			double diffX = Math.abs(dest[0] - initX) / 2;
			double diffY = Math.abs(dest[1] - initY) / 2;
			
			if (initX < dest[0] && initY < dest[1]) {
				diffX = initX + diffX;
				diffY = initY + diffY;
				if (diffX > cX) {
					this.getSprite().setSpeed(initSX, -initSY);
					
					if (this.getSprite().getX() >= diffX) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getY() >= diffY) {
						this.getSprite().setSpeed(-initSX, initSY);
					}
				}
				if (diffX < cX) {
					this.getSprite().setSpeed(-initSX, initSY);
					
					if (this.getSprite().getY() >= diffY) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getX() >= diffX) {
						this.getSprite().setSpeed(initSX, -initSY);
					}
				}
			}
			
			if (initX > dest[0] && initY < dest[1]) {
				diffX = initX - diffX;
				diffY = initY + diffY;
				if (diffX < cX) {
					this.getSprite().setSpeed(initSX, -initSY);
					
					if (this.getSprite().getX() <= diffX) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getY() >= diffY) {
						this.getSprite().setSpeed(-initSX, initSY);
					}
				}
				if (diffX > cX) {
					this.getSprite().setSpeed(-initSX, initSY);
					
					if (this.getSprite().getY() >= diffY) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getX() >= diffX) {
						this.getSprite().setSpeed(initSX, -initSY);
					}
				}
			}
			
			if (initX < dest[0] && initY > dest[1]) {
				diffX = initX + diffX;
				diffY = initY - diffY;
				if (diffX < cX) {
					this.getSprite().setSpeed(-initSX, initSY);
					
					if (this.getSprite().getY() <= diffY) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getX() >= diffX) {
						this.getSprite().setSpeed(initSX, -initSY);
					}
				}
				if (diffX > cX) {
					this.getSprite().setSpeed(initSX, -initSY);
					
					if (this.getSprite().getX() >= diffX) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getY() <= diffY) {
						this.getSprite().setSpeed(-initSX, initSY);
					}
				}
			}
			
			if (initX > dest[0] && initY > dest[1]) {
				diffX = initX - diffX;
				diffY = initY - diffY;
				if (diffX < cX) {
					this.getSprite().setSpeed(initSX, -initSY);
					
					if (this.getSprite().getX() <= diffX) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getY() <= diffY) {
						this.getSprite().setSpeed(-initSX, initSY);
					}
				}
				if (diffX > cX) {
					this.getSprite().setSpeed(-initSX, initSY);
					
					if (this.getSprite().getY() <= diffY) {
						this.getSprite().setSpeed(initSX, initSY);
					}
					
					if (this.getSprite().getX() <= diffX) {
						this.getSprite().setSpeed(initSX, -initSY);
					}
				}
			}
		}
		
	}

}
