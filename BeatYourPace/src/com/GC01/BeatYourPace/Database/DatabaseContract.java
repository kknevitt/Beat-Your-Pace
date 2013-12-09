package com.GC01.BeatYourPace.Database;

import android.provider.BaseColumns;

/**
 * 
 * @author sarahnicholson
 *
 */
public final class DatabaseContract {
	
		// Empty constructor to prevent someone from accidentally instantiating the contract class.
	    public DatabaseContract() {
	    	}

	    /* Inner class that defines the table contents */
	    public static abstract class DataEntry implements BaseColumns {
	    	//Database name
	    	public static final String DATABASE_NAME = "byp.db";
	    	
	    	// column names for the database
	    	public static final String COL_ID = "_id";
	    	public static final String COL_MEDIASTOREID = "mediastoreID";
	    	public static final String COL_ARTIST = "artist";
	    	public static final String COL_TITLE = "title";
	    	public static final String COL_BPM = "bpm";
	    	public static final String COL_INITIAL_PREF_PACE_M = "initialPrefPaceM";
	    	public static final String COL_INITIAL_PREF_PACE_KM = "initialPrefPaceKm";
	    	public static final String COL_PREF_PACE_M = "prefPaceM";
	    	public static final String COL_PREF_PACE_KM = "prefPaceKm";
	    	public static final String COL_FILE_LOC = "fileLoc";
	    	
	    	//table name for the database
	    	public static final String TABLE_NAME = "tracks";

	    }
}