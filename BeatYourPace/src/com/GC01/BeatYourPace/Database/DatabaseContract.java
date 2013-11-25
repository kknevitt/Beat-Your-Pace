package com.GC01.BeatYourPace.Database;

import android.provider.BaseColumns;

/**
 * A contract class is a container for constants that define names for URIs, tables, and columns
 * 
 * @author sarahnicholson
 *
 */
public class DatabaseContract {
	
	/** Empty constructor to prevent someone from accidentally instantiating the contract class */
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class DataEntry implements BaseColumns {

    	/** column names for the database */
    	public static final String COL_ID = "_id";
    	public static final String COL_MEDIASTOREID = "mediastoreID";
    	public static final String COL_ARTIST = "artist";
    	public static final String COL_TITLE = "title";
    	public static final String COL_BPM = "bpm";
    	/** this variable is held in miles, the value in km will be calculated */
    	public static final String COL_PREF_PACE = "prefpace";

    	/** table name for the database */
    	public static final String TABLE_NAME = "TrackData";

    }
	
}
