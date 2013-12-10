package com.GC01.BeatYourPace.Database;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> A helper class to manage database creation and version management.
 * 
 * 	<dt> Description:
 * 	<dd> This version does not yet have an associated content provider. 
 *  <dd> 
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author snichols
 *
 */

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	//TAG for the class name
	private static final String LOG_TAG = "DatabaseHelper";
	
	/** Field which sets the Database version */
	public static final int DATABASE_VERSION = 1;
	
	// SQL statement to create Tracks table
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " 
			+ DataEntry.TABLE_NAME + " (" 
			+ DataEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ DataEntry.COL_MEDIASTOREID + " INTEGER," 
			+ DataEntry.COL_TITLE + " TEXT," 
			+ DataEntry.COL_ARTIST + " TEXT,"  
			+ DataEntry.COL_BPM + " INTEGER," 
			+ DataEntry.COL_INITIAL_PREF_PACE_M + " FLOAT," 
			+ DataEntry.COL_INITIAL_PREF_PACE_KM + " FLOAT,"
			+ DataEntry.COL_PREF_PACE_M + " FLOAT," 
			+ DataEntry.COL_PREF_PACE_KM + " FLOAT,"
			+ DataEntry.COL_FILE_LOC + " STRING, UNIQUE (" + DataEntry.COL_ARTIST + ", " + DataEntry.COL_TITLE + ") ON CONFLICT ABORT);";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DataEntry.DATABASE_NAME, factory, DATABASE_VERSION);
	}

	/**
	 * Method which creates the database table if it does not already exist
	 * @param db	Database object
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(LOG_TAG,"DatabaseHelper : onCreate");
		// create the table
		db.execSQL(CREATE_TABLE);
	}

	/**
	 * Method to upgrade the database
	 * @param db	database object
	 * @param oldDbVer  integer of the current version number of the db pre upgrade
	 * @param newDbVer  integer of the new version number that is the db once upgraded
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldoldDbVer, int newDbVer) {
		// Log that the database is being upgraded
		Log.w(LOG_TAG, "Upgrading database from version "+ oldoldDbVer + " to " + newDbVer + ", which will destroy all old data");

		// Delete existing tables
		db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME);

		// Create new instance of schema
		onCreate(db);
	}
	
}