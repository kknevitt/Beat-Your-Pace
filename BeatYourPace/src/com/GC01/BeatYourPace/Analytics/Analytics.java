package com.GC01.BeatYourPace.Analytics;

import java.util.ArrayList;

public class Analytics {

	/** Category of analytics */
	protected String analyticCat;
	/** Name of the analytic */
	protected String analyticName;
	/** Arraylist to hold the details of the analytic */
	protected ArrayList<Analytics> analyticArray;  
	
	/** Creates the common fields for an analytic */
	protected Analytics(String analyticCat, String analyticName) {
		this.analyticCat = analyticCat;
		this.analyticName = analyticName;
		}
	
	/** Method to output the collected analytics ArrayList */
	protected ArrayList<Analytics> getAnalytics() {
		return analyticArray;
	}
	
	/** Method to add an event to the analytic arraylist */
	public void addAnalytics(Analytics newEvent) {
		analyticArray.add(newEvent);
	}
	
	/** Method to start tracking an analytic - not yet implemented */
	public void startTracking() {
	}
	
	/**Method to stop tracking an analytic - not yet implemented */
	public void stopTracking() {
		
	}
	
	public String getAnalyticsCat(){
		return analyticCat;
	}
	
	protected void setAnalyticsCat(){
	}
	
}
