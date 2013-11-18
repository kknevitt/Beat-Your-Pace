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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	/** Field which sets the Database version */
	public static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DataModel.DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method which creates the database table if it does not already exist
	 * @param db	Database object
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// SQL statement to create TrackData table
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + DataModel.TABLE_NAME + "(" + DataModel.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DataModel.COL_MEDIASTOREID + " INTEGER,"+ DataModel.COL_TITLE + " TEXT," + DataModel.COL_ARTIST + " TEXT,"  + DataModel.COL_BPM + " INTEGER," + DataModel.COL_PREF_PACE + " DOUBLE)"; 

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
		db.execSQL("DROP TABLE IF EXISTS " + DataModel.TABLE_NAME);

		// Create new instance of schema
		onCreate(db);
	}
	
}