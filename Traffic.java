/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * Traffic.java
 * 
 * Contains constructor and set/get methods for vehicle attributes (traffic lights for now too)
 * 
 * re-used code from bubbles
 * 
 */

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Traffic {
	private final int radius; // pixels for vehicles
	private float x;
	private float y;
	private float speedX;
	private float speedY;
	private Color color;

	public Traffic(int radius, float x, float y) {
		this.radius = radius;
		this.x = x;
		this.y = y;
		color = pickColor();
	}
	
	public Color updateColor() {
		if (ThreadLocalRandom.current().nextFloat() > .999) {
			color = pickColor();
		}
		return color;
	}
	
	private Color pickColor() {
		switch (ThreadLocalRandom.current().nextInt(2)) {
		case 0:
			return Color.GREEN;
		case 1:
			return Color.RED;
		default:
			return Color.BLACK;
		}
	}

	public Color getColor() {
		return color;
	}

	public float getLeftX() {
		return x - radius;
	}

	public float getTopY() {
		return y - radius;
	}
	
	public float getBottomY() {
		return y + radius;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public float getDistance(Traffic otherTrafficObject) {
		float deltaX = x - otherTrafficObject.x;
		float deltaY = y - otherTrafficObject.y;
		double centerDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return (float) (centerDistance - radius - otherTrafficObject.radius);
	}
	
	public double getPosition() {
		double metersPerSec = speedX / (3.6);
		int position = (int) metersPerSec * TimeClockManager.seconds;
		return position;
	}
}
