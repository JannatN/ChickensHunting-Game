package edu.beth.ProjectGameF;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Player extends GameObject {
	static Image image = new Image("File:///D://1.png");
	static ImageView imgeView = new ImageView(image);

	int count = 3;
	int columns = 3;
	int offsetX = 0;
	int offsetY = 0;
	int width = 32;
	int height = 32;
	SpriteAnimation animation;
	Pane pane = new Pane();

	Player() {
		super(imgeView);
		imgeView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		animation = new SpriteAnimation(imgeView, Duration.millis(200), count, columns, offsetX, offsetY, width,
				height);
		pane.getChildren().add(imgeView);

	}

	public void moveX() {
		this.getView().setTranslateX(this.getView().getTranslateX() + 1 * getDirection());

	}

	
}
