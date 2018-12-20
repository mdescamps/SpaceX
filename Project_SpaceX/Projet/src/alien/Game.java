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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
	private int players;
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
		
		gc.drawImage(space, 0, 0);
		stage.setScene(scene);
		stage.show();
		
		
Alert StartBox = new Alert(AlertType.CONFIRMATION);
		
		StartBox.getDialogPane().setPrefWidth(500);
		StartBox.getDialogPane().setPrefHeight(100);
		StartBox.setTitle("SpaceX Main Menu");
		StartBox.setHeaderText(null);
		StartBox.setGraphic(null);
		StartBox.setContentText(null);
		
		ButtonType btnReload = new ButtonType ("reload last game");
		ButtonType btnNew = new ButtonType ("new game");
		ButtonType btnQuit = new ButtonType ("Quit" , ButtonData.CANCEL_CLOSE);
		
		
		StartBox.getButtonTypes().setAll(btnReload,btnNew,btnQuit);
		
		Optional<ButtonType> choice = StartBox.showAndWait();
		
		if(choice.get() == btnReload) {
			
		}
		else if(choice.get() == btnNew) {
			
			
			
			StartBox.getDialogPane().setPrefWidth(800);
			StartBox.getDialogPane().setPrefHeight(100);
			StartBox.setTitle("Choose the number you are before start the game");
			StartBox.setHeaderText(null);
			StartBox.setGraphic(null);
			StartBox.setContentText("How many players ?");
			
			ButtonType btn0 = new ButtonType ("0 player");
			ButtonType btn1 = new ButtonType ("1 player");
			ButtonType btn2 = new ButtonType ("2 players");
			ButtonType btnx = new ButtonType ("Back" , ButtonData.CANCEL_CLOSE);
			
			
			StartBox.getButtonTypes().setAll(btn0,btn1,btn2,btnx);
			
			Optional<ButtonType> choice2 = StartBox.showAndWait();
			
			if(choice2.get() == btn0) {
				players = 0;
			}
			else if(choice2.get() == btn1) {
				players = 1;
			}
			else if(choice2.get() == btn2) {
				players = 2;
			}
			else {
				start(stage);
			}
			
		}
		else {
			stage.close();
		}

		
		
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
			planets.add(new Planet(r.nextInt(60) + 30,-1,planet));
		}
		
		for (Planet p : planets) {
			if(p.getPlayer() == -1) {
				p.setNbSpaceShips(r.nextInt(145) + 5);
			} 
		}
		planets.get(0).setPlayer(0);
		planets.get(1).setPlayer(1);
		planets.get(0).setNbSpaceShips(0);
		planets.get(1).setNbSpaceShips(0);
		planets.get(0).setProductionRate(40);
		planets.get(1).setProductionRate(40);

		
		
		ArrayList<SpaceShip> SSBase = new ArrayList<SpaceShip>();
		ArrayList<SpaceShip> SSLauch = new ArrayList<SpaceShip>();
		
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				
				
				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
					unSelectAll(planets);
					Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
					for (Planet planet : planets) {
						if (planet.getCircle().isInside(p) && planet.getPlayer() != -1) {
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
										int player = planet1.getPlayer();
										number = (int)(percent[player] * planet1.getNbSpaceShips());
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
										
										
										
										if (number <= planet1.getNbSpaceShips()) {
											for (int i = 0 ; i < number ; i++) {
												planet1.getAttacked();
												SpaceShip S = new SpaceShip(0,planet2,new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, 0, 0, WIDTH, HEIGHT));												
												S.setPlayer(planet1.getPlayer());
												S.setStart(planet1);
												if (planet1.getSprite().getX() < planet2.getSprite().getX()) {
													if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
													} else {
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
													}
												} else {
													if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
													} else {
														S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
																  planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
													}
												}
												S.setPosition();
												S.lauch();
												SSBase.add(S);
											}
										}
									}
								}
								planet1.unSelect();
							}
						}
				}
				
				
				
//				if (e.getEventType() != MouseEvent.MOUSE_EXITED) {
//					int Player0Number = 0;
//					int Player1Number = 0;
//					for (Planet planet : planets) {
//						if(planet.getPlayer() == 0) {
//							Player0Number++;
//						}
//						if(planet.getPlayer() == 1) {
//							Player1Number++;
//						}
//					}
//					if (Player0Number == 0) {
//						Alert victory1 = new Alert(AlertType.CONFIRMATION);
//						
//						victory1.setTitle("Game Over");
//						victory1.setHeaderText(null);
//						victory1.setGraphic(null);
//						victory1.setContentText("Red Win, Congratulation");
//						
//						ButtonType btnRestart = new ButtonType("Restart");
//						ButtonType btnEnd = new ButtonType("End Game");
//						
//						victory1.getButtonTypes().setAll(btnRestart, btnEnd);
//						
//						Optional<ButtonType> choice2 = victory1.showAndWait();
//						
//						if (choice2.get() == btnRestart) {
//							start(stage);
//						}
//						else {
//							stage.close();
//						}
//					}
//					if (Player1Number == 0) {
//						Alert victory2 = new Alert(AlertType.CONFIRMATION);
//						
//						victory2.setTitle("Game Over");
//						victory2.setHeaderText(null);
//						victory2.setGraphic(null);
//						victory2.setContentText("Blue Win, Congratulation");
//						
//						ButtonType btnRestart = new ButtonType("Restart");
//						ButtonType btnEnd = new ButtonType("End Game");
//						
//						victory2.getButtonTypes().setAll(btnRestart, btnEnd);
//						
//						Optional<ButtonType> choice2 = victory2.showAndWait();
//						
//						if (choice2.get() == btnRestart) {
//							start(stage);
//						}
//						else {
//							stage.close();
//						}
//					}
//				}
				
					
				
				if (e.isShiftDown()) {
					SpaceShip S = new SpaceShip(0,planets.get(0),new Sprite(getRessourcePathByName("images/spaceship.png"), 20, 15, 0, 0, WIDTH, HEIGHT));
					S.setPlayer(1);
					S.setStart(planets.get(0));
					S.getSprite().setSpeed(1, 1);
					S.getSprite().setPosition(e.getX() , e.getY());
					S.setPosition();
					SSLauch.add(S);
				}
			}
		};
		
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnDragDetected(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED || event.getEventType() != MouseEvent.MOUSE_PRESSED || event.getEventType() == MouseEvent.ANY) {
						
					int Player0Number = 0;
					int Player1Number = 0;
					for (Planet planet : planets) {
						if(planet.getPlayer() == 0) {
							Player0Number++;
						}
						if(planet.getPlayer() == 1) {
							Player1Number++;
						}
					}
					if (Player0Number == 0) {
						Alert victory1 = new Alert(AlertType.CONFIRMATION);
						
						victory1.setTitle("Game Over");
						victory1.setHeaderText(null);
						victory1.setGraphic(null);
						victory1.setContentText("Red Win, Congratulation");
						
						ButtonType btnRestart = new ButtonType("Restart");
						ButtonType btnEnd = new ButtonType("End Game");
						
						victory1.getButtonTypes().setAll(btnRestart, btnEnd);
						
						Optional<ButtonType> choice2 = victory1.showAndWait();
						
						if (choice2.get() == btnRestart) {
							start(stage);
						}
						else {
							stage.close();
						}
					}
					if (Player1Number == 0) {
						Alert victory2 = new Alert(AlertType.CONFIRMATION);
						
						victory2.setTitle("Game Over");
						victory2.setHeaderText(null);
						victory2.setGraphic(null);
						victory2.setContentText("Blue Win, Congratulation");
						
						ButtonType btnRestart = new ButtonType("Restart");
						ButtonType btnEnd = new ButtonType("End Game");
						
						victory2.getButtonTypes().setAll(btnRestart, btnEnd);
						
						Optional<ButtonType> choice2 = victory2.showAndWait();
						
						if (choice2.get() == btnRestart) {
							start(stage);
						}
						else {
							stage.close();
						}
					}
				}		
			}

		});
		
		
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
						percent[1] -= 0.05;
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
				
				if(e.getCode() == KeyCode.P) {
					
					
					Alert StartBox = new Alert(AlertType.CONFIRMATION);
					
					StartBox.getDialogPane().setPrefWidth(500);
					StartBox.getDialogPane().setPrefHeight(100);
					StartBox.setTitle("Pause");
					StartBox.setHeaderText(null);
					StartBox.setGraphic(null);
					StartBox.setContentText(null);
					
					ButtonType btnSave = new ButtonType ("Save");
					ButtonType btnSaveQuit = new ButtonType ("Save & Quit", ButtonData.CANCEL_CLOSE);
					ButtonType btnRestart = new ButtonType ("Restart");
					ButtonType btnContinue = new ButtonType ("Continue", ButtonData.CANCEL_CLOSE);
					ButtonType btnx = new ButtonType ("Quit" , ButtonData.CANCEL_CLOSE);
					
					
					StartBox.getButtonTypes().setAll(btnSave,btnSaveQuit,btnRestart,btnContinue,btnx);
					
					Optional<ButtonType> choice = StartBox.showAndWait();
					
					
					if(choice.get() == btnSave) {
						
					}
					else if(choice.get() == btnSaveQuit) {
						stage.close();
					}
					else if(choice.get() == btnRestart) {
						start(stage);
					}
					else if(choice.get() == btnContinue) {
						
					}
					else {
						stage.close();
					}
				}
			}
		});
		
		

		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);
				for (Planet planet : planets) {
					planet.getSprite().render(gc);
					
					
					
					String text =  "" + planet.getNbSpaceShips();
					
					switch (planet.getPlayer()) {
					case(0):
						gc.setFill(Color.DEEPSKYBLUE);
						break;
					case(1):
						gc.setFill(Color.RED);
						break;
					default:
						gc.setFill(Color.BISQUE);
					}
					
					gc.fillText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.strokeText(text, planet.getSprite().getX() + (planet.getSprite().width()/2), planet.getSprite().getY() + (planet.getSprite().height()/2));
					gc.setTextAlign(TextAlignment.CENTER);
					
					if (timer%(planet.getProductionRate()) == 0 && planet.getPlayer() != -1) {
						planet.productShip();
					}
					
					if (timer % 5 == 0 && SSBase.size() > 0) {
						SSLauch.add(SSBase.get(SSBase.size() - 1));
						SSBase.remove(SSBase.size() - 1);
					}
					
				
					Iterator<SpaceShip> it = SSLauch.iterator();
					while(it.hasNext()) {
						SpaceShip SpaceShip = it.next();
						SpaceShip.travel();
						SpaceShip.getSprite().updatePosition();
						SpaceShip.setPosition();
						if(planet.getCircle().isInside(SpaceShip.getPosition()) && SpaceShip.getDestination() == planet) {
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
						
						if (planet.getCircle().isNear(SpaceShip.getPosition()) && SpaceShip.getDestination() != planet) {
							SpaceShip.getSprite().evitate(planet);
							SpaceShip.getSprite().render(gc);
						}
					}
				}
		
				
				
				timer++;
				
				gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
				for (int i = 0 ; i < percent.length ; i++) {
					String percentage = "percentage : " + (int)((percent[i]+0.002)*100)+ "%";
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
