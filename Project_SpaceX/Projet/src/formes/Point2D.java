package formes;

import java.io.Serializable;

public class Point2D implements Serializable {
	
	
	
    private double x, y;
    
    
    
    /*
     * Constructeur de points prenant en parametre deux entiers correspondant a la position du point
     * @param x 	La position du point sur l'axe des abscisses
     * @param y 	La position du point sur l'axe des ordonnees
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * Constructeur de points prenant en parametre un entier correspondant a la position du point
     * @param p 	La position du point sur l'axe des abscisses et des ordonnees
     */
    public Point2D(Point2D p){
        this(p.x, p.y);
    }
    
    
    
    /*
     * Methode qui informe sur la position par rapport a l'axe des abscisses du point
     * @return 	L'entier qui correspond a la poition du point sur l'axe des abscisses
     */
    public double getX(){
        return this.x;
    }

    /*
     * Methode qui informe sur la position par rapport a l'axe des ordonnees du point
     * @return 	L'entier qui correspond a la poition du point sur l'axe des ordonnees
     */
    public double getY(){
        return this.y;
    }

    
    
    /*
     * Methode qui modifie la position du point celon l'axe des abscisses
     * @param x		La nouvelle position du point celon l'axe des abscisses
     */
    public void setX(double x){
        this.x = x;
    }
    
    /*
     * Methode qui modifie la position du point celon l'axe des ordonnees
     * @param y		La nouvelle position du point celon l'axe des ordonnees
     */
    public void setY(double y){
        this.y = y;
    }
    
    /*
     * Methode qui donne l'aire d'un point (constante = 0)
     * @return 	Le reel qui correspond a l'aire qui correspond au point (donc constant a 0)
     */
    public double area() {
		return 0;
	}
    
    /*
     * Methode qui donne lE perimetre d'un point (constante = 0)
     * @return 	Le reel qui correspond au perimetre qui correspond au point (donc constant a 0)
     */
	public double perimeter() {
		return 0;
	}
    
	/*
	 * Methode qui deplace le point vers une nouvelle position
	 * @param dx 	Le deplacement induit sur l'axe des abscisses
	 * @param dy 	Le deplacement induit sur l'axe des ordonnees
	 */
    public void translate(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }
    
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ( "Point2D (" + x + ", "  + y + ")");
    }
    
    
    
    /*
     * Methode qui calcule la distance entre deux points
     * @param p 	Le point dont on souhaite connaitre la distance
     * @return 		Le reel qui correspond a cette distance
     */
    public double distance(Point2D p){
        double d1 = p.x - x;
        double d2 = p.y - y;
        return Math.sqrt(d1*d1 + d2*d2);
   }
 
    
    
    /*
     * Methode qui compare le point avec celui place en parametre
     * @param p		Le point que l'on souhaite comparer au notre
     * @return 		Vrais si ils ont la meme position, Faux sinon
     */
    public boolean equals(Point2D p) {
    	if (p == null || p.getClass() != this.getClass()) {
    		return false;
    	}
    	return (this.getX() == p.getX() && this.getY() == p.getY());
    }
    
    
    
}
