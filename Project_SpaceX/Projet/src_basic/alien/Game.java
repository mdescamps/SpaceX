package alien;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

import formes.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {
	
	
	
	private int[] aiPlayers;
	private int Difficulty;
	private float[] percent = new float[2];
	private final static int WIDTH = 1800;
	private final static int HEIGHT = 1000;
	private double timer = 0;
	private boolean Pause = false;
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
	
	
	
	/**
	 * Ia qui permet le mode 1 joueur avec 2 niveaux de difficulté.
	 * @param aiPlayer 	Le numero du joueur simulé par l'ia
	 */
	public void aiPlay(int aiPlayer) {
		
		ArrayList<Planet> aiPlanets = new ArrayList<Planet>();
		ArrayList<Planet> ennemyPlanets = new ArrayList<Planet>();
		ArrayList<Planet> freePlanets = new ArrayList<Planet>();
		for(Planet planet : planets) {
			
			if (planet.getPlayer() == aiPlayer) {
				aiPlanets.add(planet);
			}
			
			if (planet.getPlayer() != aiPlayer && planet.getPlayer() != -1) {
				ennemyPlanets.add(planet);
			}
			
			if (planet.getPlayer() == -1) {
				freePlanets.add(planet);
			}
		}
		
		ArrayList<Planet> aiCibles = ennemyPlanets;
		aiCibles.addAll(freePlanets);
		
		if (aiCibles.size() > 0 && aiPlanets.size() > 0 && Pause == false) {
		
			if (Difficulty == 1 && timer%360 == 0) {
			
				int aiPlanetRank = r.nextInt(aiPlanets.size());
				int aiCibleRank = r.nextInt(aiCibles.size());
			
				Planet aiPlanet = aiPlanets.get(aiPlanetRank);
				Planet aiCible = aiCibles.get(aiCibleRank);
			
				int aiSpaceShips = r.nextInt(aiPlanet.getNbSpaceShips());
			
				SpaceshipsLauch(aiSpaceShips, aiPlanet, aiCible);
			
			}
		
		
		
			if (Difficulty == 2){
			
				Planet weakestCible = aiCibles.get(0);
				for (Planet planet : aiCibles) {
					if (planet.getNbSpaceShips() < weakestCible.getNbSpaceShips()) {
						weakestCible = planet;
					}
				}
			
				Planet nearestPlanet = aiPlanets.get(0);
				for (Planet planet : aiPlanets) {
					if( planet.getCircle().getCenter().distance(weakestCible.getCircle().getCenter()) < nearestPlanet.getCircle().getCenter().distance(weakestCible.getCircle().getCenter()) && (nearestPlanet.getNbSpaceShips() >= weakestCible.getNbSpaceShips())) {
						nearestPlanet = planet;
					}
				}
			
				if (nearestPlanet.getNbSpaceShips() >= weakestCible.getNbSpaceShips() + 10 ) {
			
					SpaceshipsLauch(weakestCible.getNbSpaceShips() + 5, nearestPlanet, weakestCible);
				
				}
			}
		}
	}
	
	
	
public void SpaceshipsLauch(int number, Planet planet1, Planet planet2) {
	
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
		if (Math.abs(diffX) < 80 && diffY < 0) {
			S.getSprite().setPosition(planet1.getCircle().getCenter().getX(),
					planet1.getCircle().getCenter().getY() + planet1.getCircle().getRadius() + 20);
		}
		if (Math.abs(diffX) < 80 && diffY > 0) {
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
		
		File f1 = new File("game.txt");
		File f2 = new File("data.txt");
		
		if (!f1.exists() || !f2.exists()) {
			StartBox.getButtonTypes().setAll(btnNew,btnQuit);
		} else {
			StartBox.getButtonTypes().setAll(btnReload,btnNew,btnQuit);
		}
		
		Optional<ButtonType> choice = StartBox.showAndWait();
		
		/**
		 * gere la recharge de la derniere partie
		 */
		if(choice.get() == btnReload) {
			
			try {
			
				FileInputStream fis = new FileInputStream("game.txt");
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    
				FileInputStream fis1 = new FileInputStream("data.txt");
				ObjectInputStream ois1 = new ObjectInputStream(fis1);
				
				while (fis1.available() != 0) {
				    
						int size;
						size = ois1.readInt();
						this.aiPlayers = new int[size];
					    for (int i = 0 ; i < size ; i++) {
					    	this.aiPlayers[i] = ois1.readInt();
					    }
			    		this.Difficulty = ois1.readInt();
				}
				

	    		ois1.close();
				
				
    		
		    while (fis.available() != 0) {
		    	try {
		    		
		    		PlanetForSave pfs =(PlanetForSave) ois.readObject();
		    		SpriteForSave sfs = pfs.getSprite();
		    		Sprite s = SpriteForSave.convertForInput(sfs);
		    		Planet p = PlanetForSave.convertForInput(pfs, s);
					planets.add(p);
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
				aiPlayers = new int[2];
				aiPlayers[0] = 0;
				aiPlayers[1] = 1;
			}
			else if(choice2.get() == btn1) {
				aiPlayers = new int[1];
				aiPlayers[0] = 1;
				
				
				
			}
			else if(choice2.get() == btn2) {
				aiPlayers = new int[0];
			}
			else {
				stage.close();
				Game newGame = new Game();
				Stage newStage = new Stage();
				planets.clear();
				newGame.start(newStage);
			}
			
			if (aiPlayers.length > 0 ) {
				StartBox.getDialogPane().setPrefWidth(400);
				StartBox.getDialogPane().setPrefHeight(100);
				StartBox.setTitle("Choose the difficulty");
				StartBox.setHeaderText(null);
				StartBox.setGraphic(null);
				StartBox.setContentText("How strong will be your enemy?");
				
				ButtonType btnEasy = new ButtonType ("Easy");
				ButtonType btnHard = new ButtonType ("Hard");
				ButtonType btnB = new ButtonType ("Back" , ButtonData.BACK_PREVIOUS);
			
				
				StartBox.getButtonTypes().setAll(btnEasy,btnHard,btnB);
			
				Optional<ButtonType> choice3 = StartBox.showAndWait();
			
				if(choice3.get() == btnEasy) {
					Difficulty = 1;
				}
				else if(choice3.get() == btnHard) {
					Difficulty = 2;
				}
				else {
					stage.close();
					Game newGame = new Game();
					Stage newStage = new Stage();
					planets.clear();
					newGame.start(newStage);
				}
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
			System.exit(0);
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
								if (planet2.getCircle().isInside(p) && planet2 != planet1 && (aiPlayers.length == 0 || planet1.getPlayer() == 0 ) && aiPlayers.length < 2) {
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
										
										SpaceshipsLauch(number, planet1, planet2);
										
									}
								}
								
								else if(planet2.getCircle().isInside(p) && planet2 == planet1 && (aiPlayers.length == 0 || planet1.getPlayer() == 0 ) && aiPlayers.length < 2){
									
									for(Planet planet : planets) {
										if(planet.getCircle().isInside(p) && planet.getPlayer() != -1 && (aiPlayers.length == 0 || (aiPlayers.length == 1 && planet.getPlayer() == 0))) {
											
											Alert UpgradesBox = new Alert(AlertType.CONFIRMATION);
											
											UpgradesBox.getDialogPane().setPrefWidth(800);
											UpgradesBox.getDialogPane().setPrefHeight(100);
											UpgradesBox.setTitle("Upgrades Panel");
											UpgradesBox.setHeaderText(null);
											UpgradesBox.setGraphic(null);
											UpgradesBox.setContentText(null);
											
											ButtonType btnShield = new ButtonType ("Shield(100)");
											ButtonType btnRate = new ButtonType ("Production Rate(50)");
											ButtonType btnB = new ButtonType ("Back" , ButtonData.BACK_PREVIOUS);
											
											UpgradesBox.getButtonTypes().setAll(btnShield,btnRate,btnB);
										
											Optional<ButtonType> choice = UpgradesBox.showAndWait();
											
											if (choice.get() == btnShield) {
												if (planet.getNbSpaceShips() >= 100) {
													planet.setShield(300);
												}
												else {
													Alert dialogW = new Alert(AlertType.WARNING);
													dialogW.setTitle("Upgrade Impossible");
													dialogW.setHeaderText(null);
													dialogW.setContentText("No enough resources");
													dialogW.showAndWait();
												}
											}
											
											else if (choice.get() == btnRate) {
												if (planet.getNbSpaceShips() >= 50) {
													planet.upgradeProductionRate(5);
												}
												else {
													Alert dialogW = new Alert(AlertType.WARNING);
													dialogW.setTitle("Upgrade Impossible");
													dialogW.setHeaderText(null);
													dialogW.setContentText("No enough resources");
													dialogW.showAndWait();
												}
											}
										}
									}
									
								}
							}
							planet1.unSelect();
							
							
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
		scene.setOnMouseClicked(mouseHandler);
		
		
		
		/**
		 * gere la fin de partie en calculant a chaque mouvement de la souris le nombre de planet qu'il reste a chaque joueurs et donc l'affichage graphique de fin de partie
		 */
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if ((event.getEventType() == MouseEvent.MOUSE_PRESSED || event.getEventType() != MouseEvent.MOUSE_PRESSED) && SSBase.size() == 0 && SSLaunch.size() == 0) {
						
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
					
					if (Player0Number == 0 || Player1Number ==0) {
						String win;
						if (Player0Number == 0) {
							win = "Red Win,";
						}
						else {
							win = "Blue win,"; 
						}
						
						Alert victory = new Alert(AlertType.CONFIRMATION);
						
						victory.setTitle("Game Over");
						victory.setHeaderText(null);
						victory.setGraphic(null);
						victory.setContentText(win + " Congratulation");
						
						ButtonType btnRestart = new ButtonType("Restart");
						ButtonType btnEnd = new ButtonType("End Game");
						
						victory.getButtonTypes().setAll(btnRestart, btnEnd);
						
						Optional<ButtonType> choice2 = victory.showAndWait();
						
						if (choice2.get() == btnRestart) {
							Game newGame = new Game();
							Stage newStage = new Stage();
							planets.clear();
							newGame.start(newStage);
						}
						else {
							System.exit(0);
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
				
				if (aiPlayers.length < 2 && s.isControlDown() || aiPlayers.length == 1) {
					if(s.getDeltaY()>0) {
						percent[0] += 0.05;
					}
					else {
						percent[0] -= 0.05;
					}
				}
				if (s.isAltDown() && aiPlayers.length == 0) {
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
					
					Pause = true;
					int[] tmpPlanetsRates = new int[planets.size()];
					int i = 0;
					for (Planet planet : planets) {
						tmpPlanetsRates[i] = planet.getProductionRate();
						planet.setProductionRate(0);
						i++;
					}
					
					
					
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
							
							FileOutputStream fos1 = new FileOutputStream("data.txt");
						    ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
							
						    oos1.writeInt(aiPlayers.length);
						    for (int j = 0 ; j < aiPlayers.length ; j++) {
						    	oos1.writeInt(aiPlayers[j]);
						    }
						    
						    oos1.writeInt(Difficulty);
						    oos1.flush();
						    oos1.close();
							
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
						}
						i = 0;
						for (Planet planet : planets) {
							planet.setProductionRate(tmpPlanetsRates[i]);
							i++;
						}
						Pause = false;
						
						
						try {
							FileOutputStream fos = new FileOutputStream("game.txt");
						    ObjectOutputStream oos = new ObjectOutputStream(fos);
						    

						    
						    for (Planet p : planets) {
						    	SpriteForSave sfs = SpriteForSave.convertForOutput("images/Planet.png", p.getSprite());
						    	PlanetForSave pfs = new PlanetForSave(p.getProductionRate(),p.getPlayer(), sfs, p.getCircle(), p.getNbSpaceShips());
						    	oos.writeObject(pfs);
						    }
						    
						    oos.flush();
						    oos.close();
						    
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
				        }
						
					}
					
					else if(choice.get() == btnSaveQuit) {
						
						try {
							
							FileOutputStream fos1 = new FileOutputStream("data.txt");
						    ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
							
						    oos1.writeInt(aiPlayers.length);
						    for (int j = 0 ; j < aiPlayers.length ; j++) {
						    	oos1.writeInt(aiPlayers[j]);
						    }
						    
						    oos1.writeInt(Difficulty);
						    oos1.flush();
						    oos1.close();
							
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
						}
						i = 0;
						for (Planet planet : planets) {
							planet.setProductionRate(tmpPlanetsRates[i]);
							i++;
						}
						Pause = false;
						
						
						try {
							FileOutputStream fos = new FileOutputStream("game.txt");
						    ObjectOutputStream oos = new ObjectOutputStream(fos);
						    

						    
						    for (Planet p : planets) {
						    	SpriteForSave sfs = SpriteForSave.convertForOutput("images/Planet.png", p.getSprite());
						    	PlanetForSave pfs = new PlanetForSave(p.getProductionRate(),p.getPlayer(), sfs, p.getCircle(), p.getNbSpaceShips());
						    	oos.writeObject(pfs);
						    }
						    
						    oos.flush();
						    oos.close();
						    
						} catch (FileNotFoundException e1) {
				            e1.printStackTrace();
				        } catch (IOException e2) {
				            e2.printStackTrace();
							
						}
						
						System.exit(0);
						stage.close();
						
					}
					
					/**
					 * Permet de lancer une nouvelle partie
					 */
					else if(choice.get() == btnRestart) {
						stage.close();
						Game newGame = new Game();
						Stage newStage = new Stage();
						planets.clear();
						newGame.start(newStage);
					}
					
					/**
					 * Permet de continuer la partie mise en pause
					 */
					else if(choice.get() == btnContinue) {
						i = 0;
						for (Planet planet : planets) {
							planet.setProductionRate(tmpPlanetsRates[i]);
							i++;
						}
						Pause = false;
					}
					
					/**
					 * Met fin a la partie
					 */
					else {
						System.exit(0);
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
					if (Pause == false) {
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
									if (planet.getShield() <= 0) {
										planet.getAttacked();
										if (planet.getNbSpaceShips() < 0) {
											planet.setPlayer(SpaceShip.getPlayer());
											planet.setNbSpaceShips(0);
										}
									}
									else {
										planet.dammageShield();
										if(planet.getShield() < 0) {
											planet.setShield(0);
										}
									}
								}
								it.remove();
							}
							else {
								SpaceShip.getSprite().render(gc);		
							}
						
							if (planet.getCircle().isNear(SpaceShip.getPosition()) && SpaceShip.getDestination() != planet) {
								SpaceShip.evitate(planet);
								SpaceShip.travel();
								SpaceShip.getSprite().render(gc);
							}
						}
					}
				}
				
				timer++;
				
				
				if(aiPlayers.length > 0) {
					int aiPlayer = 0;
					for (int i = 0 ; i < aiPlayers.length ; i++) {
						aiPlayer = aiPlayers[i];
						aiPlay(aiPlayer);
					}
				}
				
				
				
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
