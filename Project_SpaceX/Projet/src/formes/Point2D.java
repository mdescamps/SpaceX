package formes;

import java.io.Serializable;

public class Point2D implements Serializable {
	
	private static final long serialVersionUID = -4883945686414205122L;
	private double x, y;
    
    
    /**
     * Constructeur de point prenant en parametre deux entiers correspondant a la position du point
     * @param x 	valeur sur l'axe des abscisses
     * @param y 	valeur sur l'axe des ordonnees
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructeur de point a partir d'un autre point
     * @param p 	point a partir duquel on cree le nouveau
     */
    public Point2D(Point2D p){
        this(p.x, p.y);
    }
    
    
    
    /**
     * Methode qui informe sur la position par rapport a l'axe des abscisses
     * @return 	position abscisses
     */
    public double getX(){
        return this.x;
    }

    /**
     * Methode qui informe sur la position par rapport a l'axe des ordonnees
     * @return 	position ordonnees
     */
    public double getY(){
        return this.y;
    }

    
    
    /**
     * Methode qui modifie la position du point selon l'axe des abscisses
     * @param x		nouvelle position des abscisses
     */
    public void setX(double x){
        this.x = x;
    }
    
    /**
     * Methode qui modifie la position du point selon l'axe des ordonnees
     * @param y		nouvelle position des ordonnees
     */
    public void setY(double y){
        this.y = y;
    }    
    
    
    /**
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ( "Point2D (" + x + ", "  + y + ")");
    }
    
    
    /**
     * Methode qui calcule la distance entre deux points
     * @param p 	Le point dont on souhaite connaitre la distance
     * @return 		distance entre les 2 points
     */
    public double distance(Point2D p){
        double d1 = p.x - x;
        double d2 = p.y - y;
        return Math.sqrt(d1*d1 + d2*d2);
   }
 
    
    
    /**
     * Methode qui compare le point avec celui place en parametre
     * @param p		Le point que l'on souhaite comparer
     * @return 		Vrais si ils ont la meme position, Faux sinon
     */
    public boolean equals(Point2D p) {
    	if (p == null || p.getClass() != this.getClass()) {
    		return false;
    	}
    	return (this.getX() == p.getX() && this.getY() == p.getY());
    }
    
    
    
}
