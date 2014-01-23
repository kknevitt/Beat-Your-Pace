package com.GC01.BeatYourPace.Database;

import android.provider.BaseColumns;

/**
 * This class provides the information to construct the database: database name, table name and column names. 
 * @author sarahnicholson
 * @version 22/01/2014
 *
 */
public final class DatabaseContract {
	
		public DatabaseContract() {
	    	}

	    public static abstract class DataEntry implements BaseColumns {
	    	
	    	public static final String DATABASE_NAME = "byp.db";
	    	
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
	    	
	    	
	    	public static final String TABLE_NAME = "tracks";

	    }
}