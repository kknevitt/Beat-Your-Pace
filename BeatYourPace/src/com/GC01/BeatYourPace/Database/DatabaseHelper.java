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
 * @version $Date: 2013/11/22
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


	/** Field which sets the Database version */
	private static final int DATABASE_VERSION = 1;

	/** Database name */
	private static final String DATABASE_NAME = "byp.db";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method which creates the database table if it does not already exist
	 * @param db	Database object
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// SQL statement to create TrackData table
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + DataEntry.TABLE_NAME + "(" + DataEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DataEntry.COL_MEDIASTOREID + " INTEGER,"+ DataEntry.COL_TITLE + " TEXT," + DataEntry.COL_ARTIST + " TEXT,"  + DataEntry.COL_BPM + " INTEGER," + DataEntry.COL_PREF_PACE + " DOUBLE)"; 

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
		// REFERENCE DB PROGRAMMING BOOK CH2 
		Log.w("LOG_TAG", "Upgrading database from version "+ oldoldDbVer + " to " + newDbVer + ", which will destroy all old data");

		// Delete existing tables
		db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME);

		// Create new instance of schema
		onCreate(db);
	}
	
	public static String getDbName(){
		return DATABASE_NAME;
	}
	
	public static int getDbVer() {
		return DATABASE_VERSION;
	}
}