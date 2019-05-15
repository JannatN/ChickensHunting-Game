package edu.beth.ProjectGameF;

import javafx.scene.layout.BackgroundImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	Stage GameStage;

	Scene GameScene, StartScene, HelpScene;
	private Pane rootGame, rootStart, rootHelp;
	private HashMap<KeyCode, Boolean> keys = new HashMap<>();
	private HashMap<Integer, ImageView> live = new HashMap<Integer, ImageView>();
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameObject> enemies = new ArrayList<>();
	private List<GameObject> diamonds = new ArrayList<>();
	private AnimationTimer timer;
	private IntegerProperty score = new SimpleIntegerProperty();
	private IntegerProperty eggsScore = new SimpleIntegerProperty();
	private Button startbutton, soundbutton, exitButton, contButton, pauseButton, restartButton, helpButton, backButton,
			levelButton, backHelpButton;
	private Label timeLabel;
	Text levelText, textscore;
	private HBox effectBox, boxButtonsGame, HboxLives, boxLevelUp, titleBox, boxGameover, timeBox;
	private VBox boxTimeScore, boxStarts;
	private MediaPlayer gamesoundPlayer, shootMediaPlayer, gameOverPlayer, chickenPlayer;
	private Media shootMedia, gameoverMedia, gameSoundMedia, chickenMedia;
	private Timeline TimeLine;
	static Image chickenImage, fireImg, eggsImage, diamondsImg, livesImg, poopImg, helpImage, imageBackgroundGame,
			imgStart;
	static Image bossChickenImage = new Image("/pic/crazyChicken.gif", 200, 200, true, true);
	static final ImageView bossChicken = new ImageView(bossChickenImage);
	private int LIVES = 3;
	private int LEVELS = 1;
	private final Integer START_TIME = 60;
	private Integer SECONDS = START_TIME;
	Player player;
	BossChicken boss_Chicken;

	private Parent creatGamesceneContent() {
		rootGame = new Pane();

		player = new Player();
		boss_Chicken = new BossChicken();
		player.setVelocity(new Point2D(1, 0));
		boss_Chicken.setVelocity(new Point2D(1, 0));
		addGameObject(player, 250, 430);
		addGameObject(boss_Chicken, 300, 100);

		// add VBox time and score//
		boxTimeScore = new VBox();
		boxTimeScore.setLayoutX(460);
		boxTimeScore.setLayoutY(10);
		boxTimeScore.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, null, null)));

		// add HBox for buttons inside game//
		boxButtonsGame = new HBox();
		boxButtonsGame.setLayoutX(490);
		boxButtonsGame.setLayoutY(90);

		// add score //
		textscore = new Text();
		textscore.setFont(new Font("Elephant", 18));
		textscore.setFill(Color.DARKRED);
		textscore.textProperty().bind(score.asString("Diamonds:[%d]"));

		// add time //
		timeLabel = new Label();
		timeLabel.setFont(new Font("Elephant", 20));
		timeLabel.setTextFill(Color.DARKRED);

		// continue button//
		contButton = new Button("⏯ ");
		contButton.setFont(new Font("Elephant", 15));
		contButton.setTextFill(Color.DARKRED);

		// Pause button //
		pauseButton = new Button("||");
		pauseButton.setFont(new Font("Elephant", 15));
		pauseButton.setTextFill(Color.DARKRED);

		// add restart button //
		restartButton = new Button("⟲");
		restartButton.setFont(new Font("Elephant", 15));
		restartButton.setTextFill(Color.DARKRED);

		// background //
		imageBackgroundGame = new Image("/pic/backgroundGame.png");
		rootGame.setBackground(new Background(new BackgroundImage(imageBackgroundGame, null, null, null, null)));

		// Level Up state //
		boxLevelUp = effect_text(("  # Level Up #  "));
		boxLevelUp.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, null, null)));
		levelButton = new Button("Level [ " + ++LEVELS + " ]");
		levelButton.setFont(new Font("Elephant", 15));
		levelButton.setTextFill(Color.DARKRED);
		levelButton.setLayoutX(250);
		levelButton.setLayoutY(230);
		levelButton.setVisible(false);
		boxLevelUp.setVisible(false);

		boxGameover = effect_text("<<GAME OVER>>");
		boxGameover.setVisible(false);

		timeBox = effect_text("<< Time Out >>");
		timeBox.setVisible(false);

		// game sounds //
		gameoverMedia = new Media("file:///C:/Users/ncc/Downloads/GAMEOVER.mp3");
		shootMedia = new Media("file:///C:/Users/ncc/Downloads/diamondSong.mp3");
		chickenMedia = new Media("file:///C:/Users/ncc/Downloads/Chicken%20Clucking%20Sound%20Effect.mp3");
		chickenPlayer = new MediaPlayer(chickenMedia);
		chickenPlayer.setAutoPlay(true);
		chickenPlayer.setVolume(2);

		// lives //
		livesImg = new Image("/pic/LIVE.png", 30, 30, true, true);
		HboxLives = new HBox();
		for (int i = 1; i <= 3; i++) {
			live.put(i, new ImageView(livesImg));
		}
		for (Integer in : live.keySet()) {
			HboxLives.getChildren().add(live.get(in));
		}
		HboxLives.setLayoutX(10);
		HboxLives.setLayoutY(10);
		HboxLives.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, null, null)));

		// Add //
		boxTimeScore.getChildren().addAll(textscore, timeLabel);
		boxButtonsGame.getChildren().addAll(contButton, pauseButton, restartButton);
		rootGame.getChildren().addAll(backButton, boxTimeScore, boxButtonsGame, HboxLives, levelButton, boxLevelUp,
				boxGameover, timeBox);
		return rootGame;
	}

	private Parent creatStartsceneContent() {
		rootStart = new Pane();

		// HBox for buttons on start//
		boxStarts = new VBox();
		boxStarts.setLayoutX(460);
		boxStarts.setLayoutY(380);

		titleBox = effect_text("Chickens Hunting");
		titleBox.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, null, null)));

		soundbutton = new Button(" 🕪 ");
		soundbutton.setLayoutX(550);
		soundbutton.setLayoutY(10);
		soundbutton.setFont(new Font("Arial", 18));
		soundbutton.setTextFill(Color.DARKRED);

		startbutton = new Button("Start➤");
		startbutton.setFont(new Font("Elephant", 18));
		startbutton.setTextFill(Color.DARKRED);

		exitButton = new Button("  Exit ");
		exitButton.setFont(new Font("Elephant", 18));
		exitButton.setTextFill(Color.DARKRED);

		rootHelp = new Pane();
		helpButton = new Button("Help🔔");
		helpButton.setFont(new Font("Elephant", 18));
		helpButton.setTextFill(Color.DARKRED);

		// add instruction to help scene//
		helpImage = new Image("/pic/help.png");
		rootHelp.setBackground(new Background(new BackgroundImage(helpImage, null, null, null, null)));

		backButton = new Button("Home");
		backButton.setLayoutX(525);
		backButton.setLayoutY(450);
		backButton.setFont(new Font("Elephant", 13));
		backButton.setTextFill(Color.BLACK);

		// add background Start //
		imgStart = new Image("/pic/backgroundStart.png");
		rootStart.setBackground(new Background(new BackgroundImage(imgStart, null, null, null, null)));
		gameSoundMedia = new Media("file:///C:/Users/ncc/Downloads/GameSound.mp3");

		boxStarts.getChildren().addAll(startbutton, helpButton, exitButton);
		rootStart.getChildren().addAll(boxStarts, titleBox, soundbutton);
		rootHelp.getChildren().addAll(backButton);

		return rootStart;

	}

	public void update() {
		if (isPressed(KeyCode.RIGHT) && (player.getView().getTranslateX() <= 500)) {
			player.animation.play();
			player.animation.setOffsetY(64);
			player.setDirection(2);
			player.moveX();
		} else if (isPressed(KeyCode.LEFT) && (player.getView().getTranslateX() >= 0)) {
			player.animation.play();
			player.animation.setOffsetY(32);
			player.setDirection(2);
			player.changeDirection();
			player.moveX();

		} else if (isPressed(KeyCode.W)) {
			Fire bullet = new Fire();
			bullet.setVelocity(new Point2D(0, 1).subtract(0, 50));
			addFire(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
		} else if (isPressed(KeyCode.A)) {
			Fire bullet = new Fire();
			bullet.setVelocity(new Point2D(1, 0).subtract(50, 0));
			addFire(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
		} else if (isPressed(KeyCode.D)) {
			Fire bullet = new Fire();
			bullet.setVelocity(new Point2D(0, 1).add(50, 0));
			addFire(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
		} else {
			player.animation.stop();
		}

	}

	private void onupdate() {
		for (GameObject enemy : enemies) {
			for (GameObject fire : bullets) {
				if (fire.isColliding(enemy)) {
					fire.setalive(false);
					enemy.setalive(false);
					if (enemy instanceof ChickenEnemy) {
						addDiamond(new Diamond(), enemy.getView().getTranslateX(), enemy.getView().getTranslateY());
						score.set(score.get() + 1);
						shootMediaPlayer = new MediaPlayer(shootMedia);
						shootMediaPlayer.play();
						shootMediaPlayer.setVolume(2);

					} else if (enemy instanceof Eggs) {
						eggsScore.set(eggsScore.get() + 1);
					}
					rootGame.getChildren().removeAll(fire.getView(), enemy.getView());

				} else if (fire.isColliding(boss_Chicken)) {
					boss_Chicken.getView().setTranslateX(1 + Math.random() * 400);
					boss_Chicken.getView().setTranslateY(1 + Math.random() * 180);
					score.set(score.get() + 3);
					rootGame.getChildren().remove(fire.getView());
				}

				// lose life state //
				else if (enemy.isColliding(player) || boss_Chicken.isColliding(player)) {
					player.setalive(false);
					HboxLives.getChildren().remove(live.get(LIVES));
					rootGame.getChildren().removeAll(fire.getView(), enemy.getView());

					// game over state //
				} else if (LIVES == 0) {
					player.setalive(false);
					enemy.setalive(false);
					timer.stop();
					TimeLine.stop();
					boxGameover.setVisible(true);

					// game over sound //
//					gameOverPlayer = new MediaPlayer(gameoverMedia);
//					gameOverPlayer.setAutoPlay(true);
//					gameOverPlayer.setVolume(0.5);
					rootGame.getChildren().removeAll(fire.getView(), enemy.getView(), boss_Chicken.getView(),
							player.getView());
				}
				// win state //
				else if (score.get() >= 30) {
					timer.stop();
					TimeLine.stop();
					boxLevelUp.setVisible(true);
					levelButton.setVisible(true);
					rootGame.getChildren().removeAll(fire.getView(), enemy.getView(), player.getView(),
							boss_Chicken.getView());
				}
			}
		}
		if (boss_Chicken.getView().getTranslateX() == player.getView().getTranslateX()) {
			addPoops(new Poops(), (boss_Chicken.getView().getTranslateX() + 80),
					(boss_Chicken.getView().getTranslateY()));
		}
		// add Enemies//
		if (Math.random() < 0.008) {
			addChicken(new ChickenEnemy(), (1 + Math.random() * 300), 1);
			addEggs(new Eggs(), (1 + Math.random() * 400), 1);
		}

		bullets.removeIf(GameObject::isDead);
		enemies.removeIf(GameObject::isDead);
		diamonds.removeIf(GameObject::isDead);
		diamonds.forEach(GameObject::update);
		bullets.forEach(GameObject::update);
		enemies.forEach(GameObject::update);
	}

	private void addGameObject(GameObject object, double x, double y) {
		object.getView().setTranslateX(x);
		object.getView().setTranslateY(y);
		rootGame.getChildren().add(object.getView());
	}

	private void addChicken(GameObject enemy, double x, double y) {
		enemies.add(enemy);
		addGameObject(enemy, x, y);
	}

	private void addFire(GameObject fire, double x, double y) {
		bullets.add(fire);
		addGameObject(fire, x, y);
	}

	private void addEggs(GameObject egg, double x, double y) {
		enemies.add(egg);
		addGameObject(egg, x, y);
	}

	private void addPoops(GameObject poop, double x, double y) {
		enemies.add(poop);
		addGameObject(poop, x, y);
	}

	private void addDiamond(GameObject dimond, double x, double y) {
		diamonds.add(dimond);
		addGameObject(dimond, x, y);
	}

	public boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}

	private class ChickenEnemy extends GameObject {

		ChickenEnemy() {
			super(new ImageView(chickenImage));
			chickenImage = new Image("/pic/chicken.gif", 50, 50, true, true);
			this.getView().setEffect(new Reflection());

		}

	}

	private class BossChicken extends GameObject {
		BossChicken() {
			super(bossChicken);
			bossChicken.setEffect(new Reflection());
		}
	}

	private static class Fire extends GameObject {
		Fire() {
			super(new ImageView(fireImg));
			fireImg = new Image("/pic/fire.gif", 30, 30, true, true);
		}
	}

	private static class Diamond extends GameObject {
		Diamond() {
			super(new ImageView(diamondsImg));
			diamondsImg = new Image("/pic/diamonds.gif", 20, 20, true, true);

		}
	}

	private static class Eggs extends GameObject {
		Eggs() {
			super(new ImageView(eggsImage));
			eggsImage = new Image("/pic/eggs.gif", 30, 30, true, true);
			this.setVelocity(new Point2D(0, 4));
		}
	}

	private static class Poops extends GameObject {
		Poops() {
			super(new ImageView(poopImg));
			poopImg = new Image("/pic/poop.png", 20, 20, true, true);
			this.setVelocity(new Point2D(0, 2));
		}
	}

	private HBox effect_text(String FText) {
		effectBox = new HBox();
		effectBox.setLayoutX(120);
		effectBox.setLayoutY(150);
		for (int i = 0; i < FText.toCharArray().length; i++) {
			char letter = FText.charAt(i);
			Text text = new Text(String.valueOf(letter));
			text.setFont(new Font("Arial", 45));
			text.setOpacity(0);
			text.setFill((Color.DARKRED));
			text.setStroke(Color.BROWN);
			effectBox.getChildren().add(text);
			FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
			ft.setToValue(1);
			ft.setDelay(Duration.seconds(i * 0.15));
			ft.play();
		}
		return effectBox;
	}

	void cleanup() {
		// stop animations reset model etc. //
		player.update();
		boss_Chicken.update();
		TimeLine.stop();
		timer.stop();
		score.set(0);
		eggsScore.set(0);
		SECONDS = START_TIME;
		LIVES = 3;
	}

	void restart(Stage stage) {
		cleanup();
		startGame(stage);
		LEVELS = 1;
	}

	void startGame(Stage GameStage) {
		this.GameStage = GameStage;
		GameScene = new Scene(creatGamesceneContent(), 600, 490);
		GameStage.setScene(GameScene);

		// Handlers in Game //
		GameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
		GameScene.setOnKeyReleased(e -> {
			keys.put(e.getCode(), false);
		});

		// time line actions//
		TimeLine = new Timeline();
		if (TimeLine != null) {
			TimeLine.stop();
		}
		TimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(1000), t -> {
			SECONDS--;
			timeLabel.setText("Eggs : " + eggsScore.getValue() + "\nTime ⏳ : " + SECONDS.toString());
			if (SECONDS <= 0) {
				timer.stop();
				TimeLine.stop();
				timeBox.setVisible(true);
			}
			if (player.isDead() && LIVES > 0) {
				LIVES -= 1;
				player.setalive(true);
			}
			for (GameObject en : enemies) {
				if (en instanceof ChickenEnemy) {
					en.getView().setTranslateY((en.getView().getTranslateY() + 50) % 400);
				}
			}
			for (GameObject diamonds : diamonds) {
				diamonds.setVelocity(new Point2D(-1, 3).multiply(4));
			}
		}));
		TimeLine.setCycleCount(Animation.INDEFINITE);
		TimeLine.playFromStart();
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
				onupdate();

			}
		};
		timer.start();

		// buttons Actions//
		pauseButton.setOnAction(e -> {
			timer.stop();
			TimeLine.stop();
			gamesoundPlayer.pause();

		});
		contButton.setOnAction(e -> {
			timer.start();
			TimeLine.play();
			gamesoundPlayer.play();
		});
		restartButton.setOnAction(e -> {
			restart(GameStage);

		});
		levelButton.setOnAction(e -> {
			LEVELS++;
			cleanup();
			startGame(this.GameStage);
			boxLevelUp.setVisible(false);

		});

	}

	@Override
	public void start(Stage primaryStage) {
		StartScene = new Scene(creatStartsceneContent(), 600, 490);
		HelpScene = new Scene(rootHelp, 600, 490);

		// buttons actions//
		startbutton.setOnAction(e -> {
			startGame(primaryStage);
			LEVELS = 1;
		});

		helpButton.setOnAction(e -> primaryStage.setScene(HelpScene));
		soundbutton.setOnAction(e -> gamesoundPlayer.pause());
		exitButton.setOnAction(e -> primaryStage.close());
		backButton.setOnAction(e -> {
			if (primaryStage.getScene().equals(HelpScene)) {
				primaryStage.setScene(StartScene);
			} else {
				primaryStage.setScene(StartScene);
				cleanup();
			}

		});
		// game sound //
		gamesoundPlayer = new MediaPlayer(gameSoundMedia);
		gamesoundPlayer.setAutoPlay(true);
		gamesoundPlayer.setVolume(1);

		primaryStage.setScene(StartScene);
		primaryStage.setTitle("Hunting Chicken Game");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}