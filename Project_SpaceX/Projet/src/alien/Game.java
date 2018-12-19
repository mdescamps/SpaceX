package alien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
	private float[] percent = new float[2];
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
			while (h < HEIGHT / 10) {
				h = r.nextInt(HEIGHT / 4);
			}
			double w = 1.75 * h;
			Sprite planet = new Sprite(getRessourcePathByName("images/Planet.png"), w, h, 20, 20, WIDTH - 20, HEIGHT - 20);
			planet.setPosition(WIDTH * Math.random(), HEIGHT * Math.random());
			for (int j = 0 ; j < planets.size() ; j++) {
				while (planets.get(j).getSprite().intersectsPlanet(planet)) {
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
					
					
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					
					int number = 0;
					for (Planet planet1 : planets) {
						if (planet1.isSelected()) {
							for (Planet planet2 : planets) {
								if (planet2.getCircle().isInside(p) && planet2 != planet1) {
									if (e.isControlDown()) {
										number = (int)(percent[0] * planet1.getNbSpaceShips());
									}
									else {
																		
										TextInputDialog ask = new TextInputDialog("guest");
										ask.setTitle("control panel");
										ask.setContentText("Spaceships number :");
										ask.setHeaderText(null);
										ask.setGraphic(null);
										Optional<String> textIn = ask.showAndWait();
											if (textIn.isPresent()) {
												number = Integer.valueOf(textIn.get());
											}
									}
										
										
										
										if (number < planet1.getNbSpaceShips()) {
											for (int i = 0 ; i < number ; i++) {
												planet1.getAttacked();
												SpaceShip S = new SpaceShip(0,planet2,new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, 0, 0, WIDTH, HEIGHT));
												S.setPlayer(planet1.getPlayer());
												if (planet1.getSprite().getX() < planet2.getSprite().getX()) {
													if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
														S.getSprite().setSpeed(0.1, 0.1);
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
													} else {
														S.getSprite().setSpeed(0.1, -0.1);
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
													}
												} else {
													if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
														S.getSprite().setSpeed(-0.1, 0.1);
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
													} else {
														S.getSprite().setSpeed(-0.1, -0.1);
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
													}
												}
												S.setPosition();
												SpaceShips.add(S);
											}
										}
									}
								}
								planet1.unSelect();
							}
						}
				}		
					
				
				if (e.isShiftDown()) {
					SpaceShip S = new SpaceShip(0,planets.get(0),new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, 0, 0, WIDTH, HEIGHT));
					S.setPlayer(1);
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
		
		
		
		EventHandler<ScrollEvent> ScrollHandeler = new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent s) {
				
				if (s.isControlDown()) {
					if(s.getDeltaY()>0) {
						percent[0] += 0.05;
					}
					else {
						percent[0] -= 0.05;
					}
				}
				if (s.isAltDown()) {
					if(s.getDeltaY()>0) {
						percent[1] += 0.05;
					}
					else {
						percent[2] -= 0.05;
					}
				}
				if (percent[0]*100 > 100) {
					percent[0] = 1;
				}
				if (percent[1]*100 > 100) {
					percent[1] = 1;
				}
				if (percent[0] < 0) {
					percent[0] = 0;
				}
				if (percent[1] < 0) {
					percent[1] = 0;
				}
			}
		};
		
		scene.setOnScroll(ScrollHandeler);
		

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
			}
		});

		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);
				for (Planet planet : planets) {
					planet.getSprite().render(gc);
					
					String text =  "" + planet.getNbSpaceShips();
					
					switch (planet.getPlayer()) {
					case(1):
						gc.setFill(Color.DEEPSKYBLUE);
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
						SpaceShip.travel();
						SpaceShip.getSprite().updatePosition();
						SpaceShip.setPosition();
						if(planet.getCircle().isInside(SpaceShip.getPosition())) {
							if (planet.getPlayer() == SpaceShip.getPlayer()) {
								planet.productShip();
							}
							else {
								planet.getAttacked();
								if (planet.getNbSpaceShips() < 0) {
									planet.setPlayer(SpaceShip.getPlayer());
								}
							}
							it.remove();
						}
						else {
							SpaceShip.getSprite().render(gc);
						}
					}
				}
				
				int blueVictory = 0;
				int redVictory = 0;
				for (Planet planet : planets) {
					if(planet.getPlayer() == 2) {
						blueVictory++;
					}
					if(planet.getPlayer() == 1) {
						redVictory++;
					}
					if (blueVictory == 0) {
						break;
					}
					if (redVictory == 0) {
						break;
					}
					blueVictory = 0;
					redVictory = 0;
				}
				
				
				timer++;
				
				gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
				for (int i = 0 ; i < percent.length ; i++) {
					String percentage = "percentage : " + (int)(percent[i]*100)+ "%";
					switch (i) {
					case(0):
						gc.setFill(Color.DEEPSKYBLUE);
						gc.fillText(percentage, 95, 36);
						break;
					case(1):
						gc.setFill(Color.RED);
						gc.fillText(percentage, WIDTH - 95, 36);
						break;
					default:
						gc.setFill(Color.BISQUE);
					}
				}
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
				
			}
		}.start();
	}
}
