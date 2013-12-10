package com.GC01.BeatYourPace.ArchiveFiles;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> To provide the data model for the data that is stored in the SQL database
 * 
 * 	<dt> Description:
 * 	<dd> This sets the database columns, data types, database name, table name for the database.
 *  <dd>
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author sarahnicholson
 *
 */

public class DataModel {

	//data types
	private int id;
	private int mediaStoreId;
	private String artist;
	private String title;
	private int bpm; 
	private float initialPrefPaceM;
	private float initialPrefPaceKm;
	private float prefPaceM;
	private float prefPaceKm;
	private String fileLoc;
	
	public DataModel() {
		
	}
	
	public DataModel(int mediaStoreId, String artist, String title, int bpm, float initialPrefPace, float preferredPace, String fileLoc) {	
		this.mediaStoreId = mediaStoreId;
		this.artist = artist;
		this.title = title;
		this.bpm = bpm;
		this.initialPrefPaceM = initialPrefPace;
		this.prefPaceM = preferredPace;
		this.fileLoc = fileLoc;
		
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

	public float getPreferredPace(){
		return prefPaceM;
	}

	public void setPreferredPace(float preferredPace) {
		this.prefPaceM = preferredPace;
	}


	public float getInitialPrefPace() {
		return initialPrefPaceM;
	}

	public void setInitialPrefPace(int initialPrefPace) {
		this.initialPrefPaceM = initialPrefPace;
	}

	public String getFileLoc() {
		return fileLoc;
	}

	public void setFileLoc(String fileLoc) {
		this.fileLoc = fileLoc;
	}

	public float getPrefPaceKm() {
		return prefPaceKm;
	}

	public void setPrefPaceKm(float prefPaceKm) {
		this.prefPaceKm = prefPaceKm;
	}

	public float getInitialPrefPaceKm() {
		return initialPrefPaceKm;
	}

	public void setInitialPrefPaceKm(float initialPrefPaceKm) {
		this.initialPrefPaceKm = initialPrefPaceKm;
	}
	public String toString() {
		return "DataModel [id=" + id + ", mediastoreID=" + mediaStoreId + ", artist=" + artist  + ", title=" + title + ", bpm=" + bpm + ", initialPrefPaceM = " + initialPrefPaceM + ", initialPrefPaceKm = " + initialPrefPaceKm + ", prefPaceM=" + prefPaceM+ ", prefPaceKm=" + prefPaceKm+ ", fileLoc=" + fileLoc + "]";
	}
}