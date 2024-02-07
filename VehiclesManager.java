/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * VehiclesManager.java
 * 
 * Contains constructor and methods for vehicle object thread
 * 
 * re-used code from bubbles
 * 
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class VehiclesManager implements Runnable {
	
	private final ArrayList<Traffic> vehicles = new ArrayList<>();
	private final int width = 1000;
	private final int height = 800;
	private final Random randomNumberGenerator = new Random();
	private final long deltaMilliseconds;

	public VehiclesManager(int width, int height, long deltaMilliseconds) {
		this.deltaMilliseconds = deltaMilliseconds;
	}

	public ArrayList<Traffic> getVehicles() {
		return vehicles;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void add() {
		add(1 + randomNumberGenerator.nextInt(3));
	}
	
	private void place(Traffic vehicle) {
		int radius = vehicle.getRadius();
		while (true) {
			vehicle.setX(radius + (width - 2 * radius) * randomNumberGenerator.nextFloat(1));
			vehicle.setY(400);
			boolean clean = true;
			for (Traffic otherVehicle : vehicles) {
				if (otherVehicle != vehicle && vehicle.getDistance(otherVehicle) <= 0) {
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
		synchronized (vehicles) {
			while (added < adding) {
				int radius = 20;
				Traffic vehicle = new Traffic(radius, 0, 0);
				vehicles.add(vehicle);
				vehicle.setSpeedX((50 + randomNumberGenerator.nextInt(20))); // speed between 50 and 70 km/h
				added += 1;
				place(vehicle);
			}
		}
	}

	private void move() {
		synchronized (vehicles) {
			for (Traffic vehicle : vehicles) {
				boolean atRedLight = false;
				for (Traffic light : TrafficLightManager.lights) {
					float distance = vehicle.getX() - light.getLeftX();
					if (distance < 1 && distance > 0) {
						if (light.getColor() == Color.RED) {
							atRedLight = true;
							break;
						}
						else
							continue;
					}
				}
				if (atRedLight == false) {
					float newX = vehicle.getX() + deltaMilliseconds * vehicle.getSpeedX() / 1000;
					float newY = 400;
					vehicle.setX(newX);
					vehicle.setY(newY);
				}
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				move();
				Thread.sleep(deltaMilliseconds);
			} catch (InterruptedException ignored) {
			}
		}
	}
}
