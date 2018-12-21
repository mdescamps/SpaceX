package alien;

import java.io.Serializable;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Sprite implements Serializable {
	
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
	
	

	public Sprite(String path, double width, double height, double minX, double minY, double maxX, double maxY) {
		this.image = new Image(path,width,height, false, false);
		this.width = width;
		this.height = height;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public Sprite(Sprite s) {
		image = s.image;
		width = s.width;
		height = s.height;
		maxX = s.maxX;
		maxY = s.maxY;
	}
	
	
	public double width() {
		return width;
	}

	public double height() {
		return height;
	}
	
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

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

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}
	
	

	public void setSpeed(double xSpeed, double ySpeed) {
		if (this.xSpeed == 0 && this.ySpeed == 0) {
			this.xSpeedOrigin = xSpeed;
			this.ySpeedOrigin = ySpeed;
		}
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void setOriginalSpeed() {
		this.setSpeed(xSpeedOrigin, ySpeedOrigin);
	}
	
	public double getXSpeed() {
		return this.xSpeed;
	}
	
	public double getYSpeed() {
		return this.ySpeed;
	}

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
	
	

	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
		validatePosition();
	}
	
	

	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}
	
	

	public boolean intersects(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width) || (s.x >= x && s.x <= x + width))
				&& ((y >= s.y && y <= s.y + s.height) || (s.y >= y && s.y <= y + height));
	}
	
	public boolean intersectsPlanet(Sprite s) {
		return ((x >= s.x - 20 && x <= s.x + s.width + 20) || (s.x >= x - 20 && s.x <= x + width + 20))
				&& ((y >= s.y - 20 && y <= s.y + s.height + 20) || (s.y >= y - 20 && s.y <= y + height + 20));
	}
	
	

	public String toString() {
		return "Sprite<" + x + ", " + y + ">" + this.xSpeed + ";" + this.ySpeed;
	}

}
