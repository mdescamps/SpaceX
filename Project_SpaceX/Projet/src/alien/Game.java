package alien;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	ArrayList<SpaceShip> SSBase = new ArrayList<SpaceShip>();
	ArrayList<SpaceShip> SSLaunch = new ArrayList<SpaceShip>();
	ArrayList<Planet> planets = new ArrayList<Planet>();
	Random r = new Random();

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
		
		
		
		/**
		 * gere la fenetre lors du demarage du jeu
		 */
		Alert StartBox = new Alert(AlertType.CONFIRMATION);
		
		StartBox.getDialogPane().setPrefWidth(450);
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
		
		/**
		 * gere la recharge de la derniere partie
		 */
		if(choice.get() == btnReload) {
			
			try {
				
			FileInputStream fis = new FileInputStream("game.txt");
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    while (fis.available() != 0) {
		    	try {
					planets.add((Planet) ois.readObject());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
		    }
		    
		    ois.close();
			} catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			
		}
		/**
		 * cree une nouvelle partie
		 */
		else if(choice.get() == btnNew) {
			
			
			/**
			 * Gere la boite de dialogue qui renseigne sur le nombre de joueurs
			 */
			StartBox.getDialogPane().setPrefWidth(400);
			StartBox.getDialogPane().setPrefHeight(100);
			StartBox.setTitle("Choose the number you are");
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
			
			
			
			/**
			 * Cree de maniere aleatoire (sur la quantité, la position, l'appartenance, la taille et la quantité de vaisseaux) les planete du jeu tout en gerant les colition
			 * pour ne pas quelles se chevauchent
			 */
			int nbplanets = 0;
			for (; nbplanets < r.nextInt(5) + 5; nbplanets++) {
				double h = 0;
				while (h < HEIGHT / 10) {
					h = r.nextInt(HEIGHT / 4);
				}
				double w = 1.75 * h;
				Sprite planet = new Sprite("images/Planet.png", w, h, 20, 20, WIDTH - 20, HEIGHT - 20);
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
			planets.get(0).setProductionRate(30);
			planets.get(1).setProductionRate(30);
			
		}
		
		else {
			stage.close();
		}

		
		

		/**
		 * Gere les interaction avec la souris
		 */
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				
				/**
				 * Gestion de l'initialisation d'attaques par la selection de deux planete lors d'un cliqué - glissé
				 */
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
								if (planet2.getCircle().isInside(p) && planet2 != planet1 && (players == 2 || planet1.getPlayer() == 0 ) && players > 0) {
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
											SpaceShip S = new SpaceShip(0,planet2,new Sprite("images/spaceship.png", 20, 15, 0, 0, WIDTH, HEIGHT));												
											S.setPlayer(planet1.getPlayer());
											S.setStart(planet1);
											if (planet1.getSprite().getX() < planet2.getSprite().getX()) {
												if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
													S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
															planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
												} 
												else {
													S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius(),
															planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
												}
											} 
											else {
												if (planet1.getSprite().getY() < planet2.getSprite().getY()) {
													S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
															planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius());
												}
												else {
													S.getSprite().setPosition(planet1.getCircle().getCenter().getX() - planet1.getCircle().getRadius(),
															planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius());
												}
											}
											double diffX = planet1.getCircle().getCenter().getX() - planet2.getCircle().getCenter().getX();
											double diffY = planet1.getCircle().getCenter().getY() - planet2.getCircle().getCenter().getY();
											if (Math.abs(diffX) < 40 && diffY < 0) {
												S.getSprite().setPosition(planet1.getCircle().getCenter().getX(),
														planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius() + 20);
											}
											if (Math.abs(diffX) < 40 && diffY > 0) {
												S.getSprite().setPosition(planet1.getCircle().getCenter().getX(),
														planet1.getCircle().getCenter().getY() - planet1.getCircle().getRadius() - 20);
											}
											if (Math.abs(diffY) < 40 && diffX < 0) {
												S.getSprite().setPosition(planet1.getCircle().getCenter().getX() + planet1.getCircle().getRadius() + 20,
														planet1.getCircle().getCenter().getY());
											}
											if (Math.abs(diffY) < 40 && diffX > 0) {
												S.getSprite().setPosition(planet1.getCircle().getCenter().getX()  - planet1.getCircle().getRadius() - 20,
														planet1.getCircle().getCenter().getY());
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
					
					
					
					/**
					 * ia aleatoire qui permet le mode 1 joueur
					 */
					if (players == 1) {
								
						int aiPlanets = r.nextInt(planets.size());
						int aiCibles = r.nextInt(planets.size());
						
						while (planets.get(aiPlanets).getPlayer() != 1) {
							aiPlanets = r.nextInt(planets.size());
						}
						while(planets.get(aiCibles).getPlayer() == 1) {
							aiCibles = r.nextInt(planets.size());
						}
						
						Planet aiPlanet = planets.get(aiPlanets);
						Planet aiCible = planets.get(aiCibles);
						int aiSpaceShips = r.nextInt(aiPlanet.getNbSpaceShips());
						
						for (int i = 0 ; i < aiSpaceShips ; i++) {
							aiPlanet.getAttacked();
							SpaceShip S = new SpaceShip(0,aiCible,new Sprite("images/spaceship.png", 20, 15, 0, 0, WIDTH, HEIGHT));												
							S.setPlayer(aiPlanet.getPlayer());
							S.setStart(aiPlanet);
							if (aiPlanet.getSprite().getX() < aiCible.getSprite().getX()) {
								if (aiPlanet.getSprite().getY() < aiCible.getSprite().getY()) {
									S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX() + aiPlanet.getCircle().getRadius(),
											aiPlanet.getCircle().getCenter().getY() + aiPlanet.getCircle().getRadius());
								} 
								else {
									S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX() + aiPlanet.getCircle().getRadius(),
											aiPlanet.getCircle().getCenter().getY() - aiPlanet.getCircle().getRadius());
								}
							} 
							else {
								if (aiPlanet.getSprite().getY() < aiCible.getSprite().getY()) {
									S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX() - aiPlanet.getCircle().getRadius(),
											aiPlanet.getCircle().getCenter().getY() + aiPlanet.getCircle().getRadius());
								}
								else {
									S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX() - aiPlanet.getCircle().getRadius(),
											aiPlanet.getCircle().getCenter().getY() - aiPlanet.getCircle().getRadius());
								}
							}
							double diffX = aiPlanet.getCircle().getCenter().getX() - aiCible.getCircle().getCenter().getX();
							double diffY = aiPlanet.getCircle().getCenter().getY() - aiCible.getCircle().getCenter().getY();
							if (Math.abs(diffX) < 40 && diffY < 0) {
								S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX(),
										aiPlanet.getCircle().getCenter().getY() + aiPlanet.getCircle().getRadius() + 20);
							}
							if (Math.abs(diffX) < 40 && diffY > 0) {
								S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX(),
										aiPlanet.getCircle().getCenter().getY() - aiPlanet.getCircle().getRadius() - 20);
							}
							if (Math.abs(diffY) < 40 && diffX < 0) {
								S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX() + aiPlanet.getCircle().getRadius() + 20,
										aiPlanet.getCircle().getCenter().getY());
							}
							if (Math.abs(diffY) < 40 && diffX > 0) {
								S.getSprite().setPosition(aiPlanet.getCircle().getCenter().getX()  - aiPlanet.getCircle().getRadius() - 20,
										aiPlanet.getCircle().getCenter().getY());
							}
							S.setPosition();
							S.lauch();
							SSBase.add(S);
						}	
					}				
				}
				
					
				
				if (e.isShiftDown()) {
					SpaceShip S = new SpaceShip(0,planets.get(0),new Sprite("images/spaceship.png", 20, 15, 0, 0, WIDTH, HEIGHT));
					S.setPlayer(0);
					S.setStart(planets.get(0));
					S.getSprite().setSpeed(1, 1);
					S.getSprite().setPosition(e.getX() , e.getY());
					S.setPosition();
					SSLaunch.add(S);
				}
			}
		};
		
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnDragDetected(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		
		
		
		/**
		 * gere la fin de partie en calculant a chaque mouvement de la souris le nombre de planet qu'il reste a chaque joueurs et donc l'affichage graphique de fin de partie
		 */
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
							gc.clearRect(0, 0, WIDTH, HEIGHT);
							start(stage);
						}
						else {
							stage.close();
						}
					}
				}		
			}

		});
		
		
		
		/**
		 * gere les interaction avec la molette de la souris (controle du pourcentage de chaque joueurs)
		 */
		EventHandler<ScrollEvent> ScrollHandeler = new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent s) {
				
				if (players > 0 && s.isControlDown() || players == 1) {
					if(s.getDeltaY()>0) {
						percent[0] += 0.05;
					}
					else {
						percent[0] -= 0.05;
					}
				}
				if (s.isAltDown() && !(players != 2)) {
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
		
		
		/**
		 * gere les interractions avec le clavier en particulier la pause et donc la fenetre graphique associé
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				
				if(e.getCode() == KeyCode.P) {
					
					
					Alert StartBox = new Alert(AlertType.CONFIRMATION);
					
					StartBox.getDialogPane().setPrefWidth(565);
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
					
					/**
					 * gere la sauvegarde de partie 
					 */
					if(choice.get() == btnSave) {
						
						
						try {
							FileOutputStream fos = new FileOutputStream("game.txt");
						    ObjectOutputStream oos = new ObjectOutputStream(fos);
						    
						    for (Planet p : planets) {
						    	oos.writeObject(p);
						    }
						    oos.close();
						    
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
						}
						
					}
					
					/**
					 * gere la fin de partie et la sauvegarde
					 */
					else if(choice.get() == btnSaveQuit) {
						
						try {
							FileOutputStream fos = new FileOutputStream("game.txt");
						    ObjectOutputStream oos = new ObjectOutputStream(fos);
						    
						    for (Planet p : planets) {
						    	oos.writeObject(p);
						    }
						    oos.close();
						    
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
						}
						stage.close();
					}
					
					/**
					 * Permet de lancer une nouvelle partie
					 */
					else if(choice.get() == btnRestart) {
						stage.close();
						start(stage);
					}
					
					/**
					 * Permet de continuer la partie mise en pause
					 */
					else if(choice.get() == btnContinue) {
						
					}
					
					/**
					 * Met fin a la partie
					 */
					else {
						stage.close();
					}
				}
			}
		});
		
		
		/**
		 * Gere l'ensemble des animations
		 */
		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);
				for (Planet planet : planets) {
					//gc.drawImage(new Image(getRessourcePathByName(planet.getSprite().getUrl()),planet.getSprite().width(),planet.getSprite().height(), false, false), planet.getSprite().getX(), planet.getSprite().getY());
					planet.getSprite().render(gc);
					String text =  "" + planet.getNbSpaceShips();
					
					
					
					/**
					 * Gere la couleur des planete associé a chaques joueurs 
					 */
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
						int i = r.nextInt(SSBase.size());
						SSLaunch.add(SSBase.get(i));
						SSBase.remove(i);
					}
					
					
					
					/**
					 * Gere le deplacement des vaisseaux et l'interraction avec une planete
					 */
					Iterator<SpaceShip> it = SSLaunch.iterator();
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
							//gc.drawImage(new Image(getRessourcePathByName(SpaceShip.getSprite().getUrl()),SpaceShip.getSprite().width(),SpaceShip.getSprite().height(), false, false), SpaceShip.getSprite().getX(), SpaceShip.getSprite().getY());
							SpaceShip.getSprite().render(gc);
							
						}
						
						if (planet.getCircle().isNear(SpaceShip.getPosition()) && SpaceShip.getDestination() != planet) {
							SpaceShip.evitate(planet);
							SpaceShip.travel();
							//gc.drawImage(new Image(getRessourcePathByName(SpaceShip.getSprite().getUrl()),SpaceShip.getSprite().width(),SpaceShip.getSprite().height(), false, false), SpaceShip.getSprite().getX(), SpaceShip.getSprite().getY());
							SpaceShip.getSprite().render(gc);
						}
					}
				}
				
				timer++;
				
				
				
				/**
				 * Gere l'affichage des textes a actualiser tel que les pourcentages et toute leurs caracteristiques 
				 */
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
