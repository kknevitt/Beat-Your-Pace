package com.GC01.BeatYourPace.Database;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

	//Database name
	public static final String DATABASE_NAME = "byp.db";

	// column names for the database
	public static final String COL_ID = "_id";
	public static final String COL_MEDIASTOREID = "mediastoreID";
	public static final String COL_ARTIST = "artist";
	public static final String COL_TITLE = "title";
	public static final String COL_BPM = "bpm";
	//this variable is held in miles, the value in km will be calculated
	public static final String COL_PREF_PACE = "prefpace";

	//table names for the database
	public static final String TABLE_NAME = "TrackData";

	//data types
	public int id;
	public int mediaStoreId;
	public String artist;
	public String title;
	public int bpm; 
	public double preferredPace;
	
	public DataModel() {	
		super();
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

	public void setPreferredPace(double prefPace) {
		this.preferredPace = prefPace;
	}

	@Override
	//needed for arraylist
	public String toString() {
		return "DataModel [id=" + id + ", mediastoreID=" + mediaStoreId + ", artist=" + artist  + ", title=" + title + ", bpm=" + bpm + ", pace=" + preferredPace + "]";
	}

}