package alien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {
	private final static int WIDTH = 900;
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
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);

		Image space = new Image(getRessourcePathByName("images/space.jpg"), WIDTH, HEIGHT, false, false);
		ArrayList<Planet> planets = new ArrayList<Planet>();
		for (int i = 0 ; i < 5 ; i++) {
			Random r = new Random();
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
			planets.add(new Planet(r.nextInt(120) + 30,"",planet));
		}

		stage.setScene(scene);
		stage.show();

		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					Planet p1 = (Planet) e.getSource();
					Planet p2 = (Planet) e.getTarget();
					p1.sendShip(p2);
				}
				
			}
		};

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
					
					String text = "40";
					gc.fillText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.strokeText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.setTextAlign(TextAlignment.CENTER);
					
					if (timer%(planet.getProductionRate()) == 0) {
						planet.productShip();
					}
				}
				timer++;
			}
		}.start();
	}
}
