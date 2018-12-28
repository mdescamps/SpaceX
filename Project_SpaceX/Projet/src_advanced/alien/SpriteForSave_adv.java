package alien;

import java.io.Serializable;


public class SpriteForSave_adv implements Serializable {
	
	private static final long serialVersionUID = -7959013713449114925L;
	private String path;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private double width;
	private double height;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	
	public SpriteForSave_adv(String path, double x, double y, double width, double height, double xSpeed, double ySpeed, double minX, double minY, double maxX, double maxY) {
		this.path = path;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public String getPath() {
		return path;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getxSpeed() {
		return xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public static SpriteForSave_adv convertForOutput(String path, Sprite_adv s) {
		return new SpriteForSave_adv(path, s.getX(), s.getY(), s.width(), s.height(), s.getXSpeed(), s.getYSpeed(), s.getMinX(), s.getMinY(), s.getMaxX(), s.getMaxY());
	}
	
	public static Sprite_adv convertForInput(SpriteForSave_adv sfs) {
		Sprite_adv s = new Sprite_adv(sfs.getPath(),sfs.getWidth(),sfs.getHeight(),sfs.getMinX(),sfs.getMinY(),sfs.getMaxX(),sfs.getMaxY());
		s.setPosition(sfs.getX(), sfs.getY());
		s.setSpeed(sfs.getxSpeed(), sfs.getySpeed());
		return s;
	}

}
