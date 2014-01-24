package com.GC01.BeatYourPace.Database;

/**
 * A helper class to manage database creation and version management.
 * 
 * @version 2013/11/14
 * @author sarah nicholson
 *
 */

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String LOG_TAG = "DatabaseHelper";
	
	public static final int DATABASE_VERSION = 2;
	
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
			+ DataEntry.COL_FILE_LOC + " STRING);";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DataEntry.DATABASE_NAME, factory, DATABASE_VERSION);
	}

	/**
	 * Method which creates the database table if it does not already exist
	 * @param db	Database object
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		Log.d(LOG_TAG, "Tracks table created");
	}

	/**
	 * Method to upgrade the database
	 * @param db	database object
	 * @param oldDbVer  integer of the current version number of the db pre upgrade
	 * @param newDbVer  integer of the new version number that is the db once upgraded
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldoldDbVer, int newDbVer) {
		Log.w(LOG_TAG, "Upgrading database from version "+ oldoldDbVer + " to " + newDbVer + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME);

		onCreate(db);
		Log.d(LOG_TAG, "Database upgrade complete");
	}
	
}