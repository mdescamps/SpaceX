package alien;

import java.io.Serializable;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Sprite implements Serializable {
	
	private static final long serialVersionUID = 7830947624607390353L;
	private Image image;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private double xSpeedOrigin;
	private double ySpeedOrigin;
	private double width;
	private double height;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	
	
	/**
	 * Constructeur de Sprite avec un lien vers une image et 4 reels correspondant aux dimentions et condition sur la position en paramètres
	 * @param path 		Lien vers l'image du sprite
	 * @param width		Largeur de l'image
	 * @param height	Hauteur de l'image
	 * @param minX		Position minimum selon l'ordonnée
	 * @param minY		Position minimum selon l'abscisse
	 * @param maxX		Poisiton maximum selon l'ordonnée
	 * @param maxY		Position maximum selon l'abscisse
	 */
	public Sprite(String path, double width, double height, double minX, double minY, double maxX, double maxY) {
		this.image = new Image(path,width,height, false, false);
		this.width = width;
		this.height = height;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/**
	 * Constructeur de Sprite avec un autre sprite en paramètre
	 * @param s		Sprite dont on va copier les propriétées
	 */
	public Sprite(Sprite s) {
		image = s.image;
		width = s.width;
		height = s.height;
		maxX = s.maxX;
		maxY = s.maxY;
	}
	
	
	
	/**
	 * Methode qui renseigne sur la largeur du sprite
	 * @return 		Un réel qui correspond a cette largeur
	 */
	public double width() {
		return width;
	}

	/**
	 * Methode qui renseigne sur la hauteur du sprite 
	 * @return 		Un réel qui correspond a cette hauteur 
	 */
	public double height() {
		return height;
	}
	
	
	
	/**
	 * Methode qui renseigne sur la position du sprite selon les ordonnées
	 * @return		Un réel qui correspond cette position
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Methode qui renseigne sur la position du sprite selon les abscisses
	 * @return		Un réel qui correspond a cette positon
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Methode qui initialise la position du sprite sur l'axe des abscisse
	 * @param x		Un réel
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Methode qui initialise la position du sprite sur l'axe des ordonnées 
	 * @param y 	Un réel
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	
	
	/**
	 * Methode qui permet de garder le sprite dans les limites que l'on lui impose
	 */
	public void validatePosition() {
		if (x <= minX) {
			x = minX;
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}
		
		if (x + width >= maxX) {
			x = maxX - width;
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}
		
		if (y <= minY) {
			y = minY;
			ySpeed *= 0;
		} else if (y < 0) {
			y = 0;
			ySpeed *= 0;
		}

		if (y + height >= maxY) {
			y = maxY - height;
			ySpeed *= -1;
		} else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}
	
	/**
	 * Methode qui initialise la postion du sprite
	 * @param x		Reel qui correspond a la position sur l'axe des abscisses
	 * @param y		Reel qui correspond a la position sur l'axe des ordonnées
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}
	
	
	
	/**
	 * Methode qui modifie la vitesse d'un sprite
	 * @param xSpeed	Un reel donnant la vitesse sur les abscisses
	 * @param ySpeed	Un reel donnant la vitesse sur les ordonnées
	 */
	public void setSpeed(double xSpeed, double ySpeed) {
		if (this.xSpeed == 0 && this.ySpeed == 0) {
			this.xSpeedOrigin = xSpeed;
			this.ySpeedOrigin = ySpeed;
		}
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	/**
	 * Methode qui initialise la vitesse du sprite a une vitesse predefinie
	 */
	public void setOriginalSpeed() {
		this.setSpeed(xSpeedOrigin, ySpeedOrigin);
	}
	
	/**
	 * Methode qui renseigne sur la vitesse du sprtie selon l'axe des abscisses
	 * @return	Un reel
	 */
	public double getXSpeed() {
		return this.xSpeed;
	}
	
	/**
	 * Methode qui renseigne sur la vitesse du sprite selon l'axe des ordonées
	 * @return		Un reel
	 */
	public double getYSpeed() {
		return this.ySpeed;
	}
	
	/**
	 * Methode qui modifie la vitessse d'un sprite par rapport a une interaction avec le clavier
	 * @param code	La touche utiliser (haut,bas,droite,gauche)
	 */
	public void changeSpeed(KeyCode code) {
		switch (code) {
		case LEFT:
			xSpeed--;
			break;
		case RIGHT:
			xSpeed++;
			break;
		case UP:
			ySpeed--;
			break;
		case DOWN:
			ySpeed++;
			break;
		case SPACE:
			ySpeed = xSpeed = 0;
			break;
		default:
		}
	}
	
	

	/**
	 * Methode qui actualise la position du sprite en fonction de sa vitesse
	 */
	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
		validatePosition();
	}
	
	

	/**
	 * Methode qui affiche le sprite sur une fenetre graphique
	 * @param gc	La fenetre graphique
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}
	
	
	
	/**
	 * Methode qui permet de verifier si un sprite est en collision avec un autre
	 * @param s		L'autre sprite
	 * @return		Vrais si il y a collision, Faux sinon.
	 */
	public boolean intersects(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width) || (s.x >= x && s.x <= x + width))
				&& ((y >= s.y && y <= s.y + s.height) || (s.y >= y && s.y <= y + height));
	}
	
	/**
	 * Methode qui permet de verifier si un sprite est en collision avec une planete
	 * @param s		Le sprite de la planete
	 * @return		Vrais si il y a collision, Faux sinon.
	 */
	public boolean intersectsPlanet(Sprite s) {
		return ((x >= s.x - 20 && x <= s.x + s.width + 20) || (s.x >= x - 20 && s.x <= x + width + 20))
				&& ((y >= s.y - 20 && y <= s.y + s.height + 20) || (s.y >= y - 20 && s.y <= y + height + 20));
	}
	
	
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Sprite<" + x + ", " + y + ">" + this.xSpeed + ";" + this.ySpeed;
	}

}
