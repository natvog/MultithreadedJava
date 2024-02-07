/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * TrafficLightManager.java
 * 
 * Contains constructor and methods for traffic light object thread
 * 
 * re-used code from bubbles
 * 
 */

import java.util.ArrayList;
import java.util.Random;

public class TrafficLightManager implements Runnable {
	
	static final ArrayList<Traffic> lights = new ArrayList<>();
	private final int width = 1000;
	private final Random randomNumberGenerator = new Random();
	private final long deltaMilliseconds;

	public TrafficLightManager(int width, int height, long deltaMilliseconds) {
		this.deltaMilliseconds = deltaMilliseconds;
	}

	public ArrayList<Traffic> getLights() {
		return lights;
	}

	public void add() {
		add(1 + randomNumberGenerator.nextInt(2));
	}
	
	private void place(Traffic light) {
		int radius = light.getRadius();
		while (true) {
			light.setX(radius + (width - 2 * radius) * randomNumberGenerator.nextFloat());
			light.setY(400);
			boolean clean = true;
			for (Traffic otherVehicle : lights) {
				if (otherVehicle != light && light.getDistance(otherVehicle) <= 0) {
					clean = false;
					break;
				}
			}
			if (clean) {
				break;
			}
		}
	}

	public void add(int adding) {
		int added = 0;
		synchronized (lights) {
			while (added < adding) {
				int radius = 20;
				Traffic light = new Traffic(radius, 0, 0);
				lights.add(light);
				light.setSpeedX(0); // lights don't move
				added += 1;
				place(light);
			}
		}
	}

	private void updateColor() {
		synchronized (lights) {
			for (Traffic light : lights) {
				light.updateColor();
			}	
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				updateColor();
				Thread.sleep(deltaMilliseconds);
			} catch (InterruptedException ignored) {
			}
		}
	}
}
