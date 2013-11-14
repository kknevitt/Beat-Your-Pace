package com.GC01.BeatYourPace.Database;

public class DataModel {

	//Database name
	public static final String DATABASE_NAME = "byp.db";

	// column names for the database
	public static final String COL_ID = "_id";
	public static final String COL_MEDIASTOREID = "mediastoreID";
	public static final String COL_ARTIST = "artist";
	public static final String COL_TITLE = "title";
	public static final String COL_BPM = "bpm";
	public static final String COL_PACE = "pace";

	//table names for the database
	public static final String TABLE_NAME = "TrackData";

	//data types
	public int id;
	public int mediastoreid;
	public String artist;
	public String title;
	public int bpm; 
	public double pace;

	public DataModel() {	
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMediastoreid() {
		return mediastoreid;
	}

	public void setMediastoreid(int mediastoreid) {
		this.mediastoreid = mediastoreid;
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

	public double getPace(){
		return pace;
	}

	public void setPace(double pace) {
		this.pace = pace;
	}

	@Override
	//needed for arraylist
	public String toString() {
		return "DataModel [id=" + id + ", mediastoreID=" + mediastoreid + ", artist=" + artist  + ", title=" + title + ", bpm=" + bpm + ", pace=" + pace + "]";
	}
}