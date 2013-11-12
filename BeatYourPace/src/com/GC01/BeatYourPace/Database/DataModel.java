package com.GC01.BeatYourPace.Database;

public class DataModel {

	//Database name
	public static final String DATABASE_NAME = "byp.db";
	
	// column names for the database
	public static final String ID = "_id";
    public static final String MEDIASTOREID = "mediastoreID";
    public static final String ARTIST = "artist";
    public static final String TITLE = "title";
    public static final String BPM = "bpm";
    public static final String PACE = "pace";
	    
	 //table name
	 public static final String TABLE_NAME = "TrackData";
	    
	    public DataModel(int ID, int MEDIASTOREID, String ARTIST, String TITLE, int BPM, double PACE) {
	    	super();
	           
	        }
	     
	        
			public static String getId() {
				return ID;
			}
			
			public static String getMediastoreid() {
				return MEDIASTOREID;
			}


			public static String getTitle() {
				return TITLE;
			}
			
			public static String getArtist() {
				return ARTIST;
			}
			
			public static String getBPM(){
				return BPM;
			}
			
			
			public static String getPace(){
				return PACE;
			}

			@Override
			//needed for arraylist
			 public String toString() {
				return "DataModel [id=" + ID + ", mediastoreID=" + MEDIASTOREID + ", artist=" + ARTIST  + ", title=" + TITLE + ", bpm=" + BPM + ", pace=" + PACE + "]";
			  }
	   }