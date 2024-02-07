/* CMSC 335
 * Final Project
 * 10/10/2023
 * @author natalievogel
 * 
 * TimeClockManager.java
 * 
 * Contains method for time clock object thread
 * 
 * Displays current time on Main GUI
 * 
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeClockManager {
	
	static int seconds = 0;
	
	protected static void timeClock() {
		
		Thread timeClock = new Thread() {
			
			public void run() {
			
				try {
					while(true) {
						Calendar cal=new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH)+1;
						int year = cal.get(Calendar.YEAR);
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
                        
						String time = hour +":"+ minute +":"+ second+", "+year +"-"+ month +"-"+ day;
						Main.timeClock.setText("Current time: " + time);
						sleep(1000);
						seconds++;
					}
				} catch (Exception exc) {
				}
			}
		};
		timeClock.start();
	}
}
