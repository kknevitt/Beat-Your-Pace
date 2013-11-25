package com.GC01.BeatYourPace.Database;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> To provide the data model for the data that is stored in the SQL database
 * 
 * 	<dt> Description:
 * 	<dd> This sets the data types and allows an object to be created that has all the fields for
 *  <dd> one record in the database.
 * </dl>
 * 
 * @version $Date: 2013/11/25
 * @author sarahnicholson
 *
 */

public class DataModel {

	/** Data types for the data to go into the database */
	private int id;
	private int mediaStoreId;
	private String artist;
	private String title;
	private int bpm; 
	private double preferredPace;
	
	public DataModel() {
		
	}
	
	public DataModel(int id, int mediaStoreId, String artist, String title, int bpm, double preferredPace) {	
		this.id = id;
		this.mediaStoreId = mediaStoreId;
		this.artist = artist;
		this.title = title;
		this.bpm = bpm;
		this.preferredPace = preferredPace;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMediaStoreId() {
		return mediaStoreId;
	}

	public void setMediastoreid(int mediastoreid) {
		this.mediaStoreId = mediastoreid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist){
		this.artist = artist;
	}

	public int getBPM(){
		return bpm;
	}

	public void setBPM(int bpm){
		this.bpm = bpm;
	}

	public double getPreferredPace(){
		return preferredPace;
	}

	public void setPreferredPace(double preferredPace) {
		this.preferredPace = preferredPace;
	}

	@Override
	//needed for arraylist
	public String toString() {
		return "DataModel [id=" + id + ", mediastoreID=" + mediaStoreId + ", artist=" + artist  + ", title=" + title + ", bpm=" + bpm + ", pace=" + preferredPace + "]";
	}

}