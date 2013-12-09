package com.GC01.BeatYourPace.Analytics;

import java.util.Date;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> To capture time based data for use in analytics.
 * 
 * 	<dt> Description:
 * 	<dd> This class extends Analytics
 *  <dd> It has methods to set and get time based analytics
 *  <dd> 
 * </dl>
 * 
 * @version $Date: 18/11/2013
 * @author sarahnicholson, laurabarbosa
 *
 */

public class TimeAnalytics extends Analytics {

	/** The category of the event */
	private static String timeCat;
	/** The timing measurement in milliseconds */
	private static long time;
	/** The name of the timed event */
	private static String eventName;	
	
	protected TimeAnalytics(String analyticCat1, String analyticName1, long time) {
		super(analyticCat1, analyticName1);
			this.analyticCat = timeCat;
			this.analyticName = getEventName();
			time = getTime();
	}
	
	private String getEventName() {
		/** Add code to work out the name of the event or activity being tracked */
		return eventName;
	}

	public long getTime() {
		/** Need to add the code which indicates the activity started */
		Date startTime = new Date();
		/** Need to add the code which indicates the activity stopped */
		Date endTime = new Date();
		time = endTime.getTime() - startTime.getTime();
		return time;
	}

}
