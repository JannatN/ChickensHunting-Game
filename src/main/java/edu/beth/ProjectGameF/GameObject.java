//package edu.beth.domain;
package edu.beth.ProjectGameF;

import javafx.geometry.Point2D;

import javafx.scene.Node;

public class GameObject {

	private Node view;

	private Point2D velocity = new Point2D(0, 0);
	private boolean alive = true;
	private int direction;

	public GameObject() {
	}

	public GameObject(Node view) {
		this.view = view;

	}

	public Node getView() {
		return view;
	}

	public void update() {
		view.setTranslateX(view.getTranslateX() + velocity.getX());
		view.setTranslateY(view.getTranslateY() + velocity.getY());

	}

	public boolean isalive() {
		return alive;
	}

	public boolean isDead() {
		return !alive;
	}

	public void setalive(boolean alive) {
		this.alive = alive;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public boolean isColliding(GameObject other) {
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}

	public void changeDirection() {
		direction = -direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
