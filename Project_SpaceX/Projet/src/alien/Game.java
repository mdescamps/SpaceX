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
	private final static int WIDTH = 1600;
	private final static int HEIGHT = 900;
	private double timer = 0;

	public static String getRessourcePathByName(String name) {
		return Game.class.getResource('/' + name).toString();
	}

	public static void main(String[] args) {
		launch(args);
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
		for (int i = 0 ; i < 6 ; i++) {
			double h = 0;
			while (h < HEIGHT / 8) {
				h = r.nextInt(HEIGHT / 4);
			}
			double w = 1.75 * h;
			Sprite planet = new Sprite(getRessourcePathByName("images/Planet.png"), w, h, 20, 20, WIDTH - 20, HEIGHT - 20);
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
		
		for (Planet p : planets) {
			if(p.getPlayer() == 0) {
				p.setNbSpaceShips(r.nextInt(145) + 5);
			}
		}

		stage.setScene(scene);
		stage.show();
		
		ArrayList<SpaceShip> SpaceShips = new ArrayList<SpaceShip>();

		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				int planet1 = 2;
				boolean b = true;
				if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					for (int i = 0 ; i < planets.size() ; i++) {
						if (planets.get(i).getCircle().isInside(p)) {
							planet1 = 2;
						}
					}
				}
				if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					for (Planet planet2 : planets) {
						if (planet2.getCircle().isInside(p)) {
							if (b) {
								planet2.setNbSpaceShips(100);
								planets.get(planet1).setNbSpaceShips(10);
								planet1 = -1;
							}
						}
						else {
							planet1 = -1;
						}
					}
				}
						
				if (e.isControlDown()) {
					SpaceShip S = new SpaceShip(0,new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, 0, 0, WIDTH, HEIGHT));
					S.getSprite().setSpeed(0.5, 0.5);
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
					case(1):
						gc.setFill(Color.BLUE);
						break;
					case(2):
						gc.setFill(Color.RED);
						break;
					default:
						gc.setFill(Color.BISQUE);
					}
					
					gc.fillText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.strokeText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.setTextAlign(TextAlignment.CENTER);
					
					if (timer%(planet.getProductionRate()) == 0 && planet.getPlayer() != 0) {
						planet.productShip();
					}
					
				
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
					
				}
				timer++;
			}
		}.start();
	}
}
