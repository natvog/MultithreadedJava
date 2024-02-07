/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * Main.java
 * 
 * Contains Main method and GUI frame from simulation
 * 
 * re-used code from bubbles
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Main extends JFrame {
	
	static JLabel timeClock = new JLabel(" ");
	static boolean paused = false;

	Main() {
		super("Traffic Light Simulator");
		TrafficLightManager lightManager = new TrafficLightManager(1000, 1200, 1L);
		lightManager.add(3);
		VehiclesManager vehicleManager = new VehiclesManager(1000, 800, 1L);
		for (int index = 0; index < 3; ++index) {
			vehicleManager.add();
		}
		TrafficDisplay trafficDisplay = new TrafficDisplay(vehicleManager, lightManager);
		setLayout(new BorderLayout());
		JPanel timeDisplay = new JPanel(new FlowLayout());
		timeDisplay.add(timeClock);
		JPanel controlPanel = new JPanel(new FlowLayout());
		JButton addTraffic = new JButton("Add Traffic");
		JButton continueTraffic = new JButton("Continue");
		JButton pauseTraffic = new JButton("Pause");
		JButton stopTraffic = new JButton("Stop");
		addTraffic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vehicleManager.add();
				lightManager.add();
				trafficDisplay.repaint();
			}
		});
		// not functional -- couldn't figure out correct placement for while(!paused) loop
		pauseTraffic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread timeThread = getThreadByName("Thread-0");
				Thread lightThread = getThreadByName("Thread-1");
				Thread vehicleThread = getThreadByName("Thread-2");
				synchronized (timeThread) {
					paused = true;
					timeThread.notify();
				}
				synchronized (lightThread) {
					paused = true;
					lightThread.notify();
				}
				synchronized (vehicleThread) {
					paused = true;
					vehicleThread.notify();
				}
			}
		});
		continueTraffic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread timeThread = getThreadByName("Thread-0");
				Thread lightThread = getThreadByName("Thread-1");
				Thread vehicleThread = getThreadByName("Thread-2");
				synchronized (timeThread) {
					paused = false;
					timeThread.notify();
				}
				synchronized (lightThread) {
					paused = false;
					lightThread.notify();
				}
				synchronized (vehicleThread) {
					paused = false;
					vehicleThread.notify();
				}
			}
		});
		stopTraffic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		controlPanel.add(addTraffic);
		controlPanel.add(pauseTraffic);
		controlPanel.add(continueTraffic);
		controlPanel.add(stopTraffic);
		add(timeDisplay, BorderLayout.NORTH);
		add(controlPanel, BorderLayout.SOUTH);
		add(trafficDisplay, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 500));
		new Thread(lightManager).start();
		new Thread(vehicleManager).start();
		new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ignored) {
				trafficDisplay.repaint();				
			}
		}).start();
	}
	
	public static Thread getThreadByName(String threadName) {
	    for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals(threadName)) return t;
	    }
	    return null;
	}

	public static void main(String[] args) {
		TimeClockManager.timeClock();
		new Main().setVisible(true);	
	}

}
