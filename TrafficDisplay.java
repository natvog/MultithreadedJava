/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * TrafficDisplay.java
 * 
 * Contains drawing components for traffic display panel
 * 
 * re-used code from bubbles
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TrafficDisplay extends JPanel {

	private final List<Traffic> vehicles;
	private final List<Traffic> lights;
	private final int displayAreaWidth;
	private final int displayAreaHeight;

	public TrafficDisplay(VehiclesManager vehicleManager, TrafficLightManager lightManager) {
		super();
		vehicles = vehicleManager.getVehicles();
		lights = lightManager.getLights();
		displayAreaWidth = vehicleManager.getWidth();
		displayAreaHeight = vehicleManager.getHeight();
		setMinimumSize(new Dimension(displayAreaWidth, displayAreaHeight));
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		float widthOffset = (getWidth() - displayAreaWidth) / 2f;
		float heightOffset = (getHeight() - displayAreaHeight) / 2f;
		graphics.setColor(Color.lightGray);
		graphics.fillRect((int) widthOffset, (int) heightOffset, displayAreaWidth, displayAreaHeight);
		synchronized (vehicles) {
			vehicles.forEach((vehicle) -> {
				drawVehicles(vehicle, graphics, widthOffset, heightOffset);
			});
		}
		synchronized (lights) {
			lights.forEach((light) -> {
				drawLight(light, graphics, widthOffset, heightOffset);
			});
		}
	}

	private void drawVehicles(Traffic vehicle, Graphics graphics, float widthOffset, float heightOffset) {
		int radius = vehicle.getRadius();
		graphics.setColor(Color.black);
		graphics.fillRect((int) (widthOffset + vehicle.getLeftX() + radius / 2),
				(int) (heightOffset + vehicle.getTopY() + radius / 2), radius, radius);
		float speed = vehicle.getSpeedX();
		String speedStr = String.valueOf(speed);
		graphics.drawString(speedStr, (int) ((int) widthOffset + vehicle.getLeftX() + radius / 2), 
				(int) (heightOffset + vehicle.getTopY()));
		float position = vehicle.getX();
		//double position = vehicle.getPosition();
		String positionStr = String.valueOf(position);
		graphics.drawString(positionStr, (int) ((int) widthOffset + vehicle.getLeftX() + radius / 2), 
				(int) (heightOffset + vehicle.getBottomY()));
	}
	
	private void drawLight(Traffic light, Graphics graphics, float widthOffset, float heightOffset) {
		int width = 4;
		int height = 30;
		graphics.setColor(light.getColor());
		graphics.fillRect((int) (widthOffset + light.getLeftX() + width / 2),
				(int) ((heightOffset + light.getTopY() + height / 2) - 10), width, height);
	}		
}
