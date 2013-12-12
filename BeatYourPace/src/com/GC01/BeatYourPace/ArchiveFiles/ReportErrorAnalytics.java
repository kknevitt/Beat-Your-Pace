package com.GC01.BeatYourPace.ArchiveFiles;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> To capture error data for use in analytics.
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

public class ReportErrorAnalytics extends Analytics {
	
	/** A description of the exception */
	private String exDesc;
	
	protected ReportErrorAnalytics(String analyticCat, String analyticName) {
		super(analyticCat, analyticName);
		exDesc = getException();
	}
	
	private String getException(){
	  /** Implement code to get the log of the exception that occured */
		return exDesc;
	}
}
