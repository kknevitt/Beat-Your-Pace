package com.GC01.BeatYourPace.Analytics;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> To capture event data for use in analytics.
 * 
 * 	<dt> Description:
 * 	<dd> This class extends Analytics
 *  <dd> It has methods to find out the event that occurred 
 *  <dd> 
 * </dl>
 * 
 * @version $Date: 18/11/2013
 * @author sarahnicholson, laurabarbosa
 *
 */

public class EventAnalytics extends Analytics{

	/** Category name */
	private String eventCat;
	/** Event action */
	private String eventAction;
	/** The name of the event */
	private String eventName;	
	
	 protected EventAnalytics(String analyticCat1, String analyticName1,
			String analyticVal1) {
		super(analyticCat1, analyticName1);
		this.analyticCat = eventCat;
		this.analyticName = getEventName();
		eventAction = getEventAction();
	}
		
		private String getEventName() {
			/** Add code to work out the name of the event or activity being tracked */
			return eventName;
		}
		
		private String getEventAction() {
			/** add code to read the name of the event action that was performed */
			return eventAction;
		}
}