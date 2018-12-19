package alien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import formes.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {
	private final static int WIDTH = 1900;
	private final static int HEIGHT = 1100;
	private double timer = 0;

	public static String getRessourcePathByName(String name) {
		return Game.class.getResource('/' + name).toString();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void unSelectAll(ArrayList<Planet> planets) {
		for (Planet planet : planets) {
			planet.unSelect();
		}
	}

	public void start(Stage stage) {

		stage.setTitle("SpaceX");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.TRANSPARENT);
		gc.setLineWidth(1);
		
		Image space = new Image(getRessourcePathByName("images/space.jpg"), WIDTH, HEIGHT, false, false);
		ArrayList<Planet> planets = new ArrayList<Planet>();
		Random r = new Random();
		for (int i = 0 ; i < r.nextInt(5) + 5 ; i++) {
			double h = 0;
			while (h < HEIGHT / 8) {
				h = r.nextInt(HEIGHT / 4);
			}
			double w = 1.75 * h;
			Sprite planet = new Sprite(getRessourcePathByName("images/Planet.png"), w, h, WIDTH, HEIGHT);
			planet.setPosition(WIDTH * Math.random(), HEIGHT * Math.random());
			for (int j = 0 ; j < planets.size() ; j++) {
				while (planets.get(j).getSprite().intersects(planet)) {
					planet.setPosition(WIDTH * Math.random(), HEIGHT * Math.random());
					j = 0;
				}
			}
			planet.validatePosition();
			planets.add(new Planet(r.nextInt(60) + 30,0,planet));
		}
		planets.get(0).setPlayer(1);
		planets.get(1).setPlayer(2);

		stage.setScene(scene);
		stage.show();
		
		ArrayList<SpaceShip> SpaceShips = new ArrayList<SpaceShip>();

		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
					unSelectAll(planets);
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					for (Planet planet : planets) {
						if (planet.getCircle().isInside(p) && planet.getPlayer() != 0) {
							planet.select();
						}
					}
				}
				if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
					int number = 10;
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					for (Planet planet1 : planets) {
						if (planet1.isSelected()) {
							for (Planet planet2 : planets) {
								if (planet2.getCircle().isInside(p) && planet2 != planet1 && planet1.getNbSpaceShips() >= number) {
									/*SpaceShip S = new SpaceShip(0,0,0,new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, WIDTH, HEIGHT));
									S.getSprite().setSpeed(1, 0);
									S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius() + 5 ,
															  planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius() + 5);
									S.setPosition();
									SpaceShips.add(S);*/
									planet1.sendShip(planet2,number);
									if (planet2.getNbSpaceShips() < 0) {
										planet2.setPlayer(planet1.getPlayer());
										planet2.setNbSpaceShips(0);
									}
									planet1.unSelect();
								}
							}
						}
					}
				}
						
				if (e.isControlDown() && e.getEventType() == MouseEvent.MOUSE_PRESSED) {
					SpaceShip S = new SpaceShip(0,0,0,new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, WIDTH, HEIGHT));
					S.getSprite().setSpeed(1, 1);
					S.getSprite().setPosition(e.getX() , e.getY());
					S.setPosition();
					SpaceShips.add(S);
				}
			}
		};

		scene.setOnMouseReleased(mouseHandler);
		scene.setOnDragDetected(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
			}
		});

		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);
				for (int i = 0 ; i < planets.size() ; i ++) {
					Planet planet = planets.get(i);
					planet.getSprite().render(gc);
					
					String text =  "" + planet.getNbSpaceShips();
					
					switch (planet.getPlayer()) {
					case (1):
						gc.setFill(Color.BLUE);
						break;
					case (2):
						gc.setFill(Color.RED);
						break;
					default:
						gc.setFill(Color.BISQUE);
					}
					
					gc.fillText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.strokeText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.setTextAlign(TextAlignment.CENTER);
				
					Iterator<SpaceShip> it = SpaceShips.iterator();
					while(it.hasNext()) {
						SpaceShip SpaceShip = it.next();
						SpaceShip.getSprite().updatePosition();
						SpaceShip.setPosition();
						if(planet.getCircle().isInside(SpaceShip.getPosition())) {
							it.remove();
							planet.productShip();
						}
						else {
							SpaceShip.getSprite().render(gc);
						}
					}
					
					if (timer%(planet.getProductionRate()) == 0  && planet.getPlayer() != 0) {
						planet.productShip();
					}
				}
				timer++;
			}
		}.start();
	}
}
