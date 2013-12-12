package com.GC01.BeatYourPace.Database;

/**
 * Provides common methods for the classes that query the database such as open, close, and query all data
 * 
 * @author sarahnicholson
 */

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.Main.ContextProvider;

public abstract class DatabaseAdapter {

	private final String LOG_TAG = "DbAdapter";
	private final Context context;
	protected DatabaseHelper DbHelper;
	protected SQLiteDatabase db;

	public DatabaseAdapter (Context ctx){
		this.context = ctx;
		DbHelper = new DatabaseHelper(ContextProvider.getContext(), DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
	}

	/**
	 * Method to open the database in writable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAdapter openDbWrite() throws SQLException {
		db = DbHelper.getWritableDatabase();
		return this;
	}

	/**
	 *  Method to open the database in readable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAdapter openDbRead() throws SQLException {
		db = DbHelper.getReadableDatabase();
		return this;
	}

	/**
	 * Method to close the database
	 */
	public void closeDb(){
		DbHelper.close();
	}
	
	/**
	 * Query all tracks and all data fields
	 * @return cursor  New cursor to read the database and give the results
	 */
	public Cursor getAllTracks() {			
		openDbRead();
		String[] allCol = new String[] {
				DataEntry.COL_ID, 
				DataEntry.COL_MEDIASTOREID, 
				DataEntry.COL_ARTIST, 
				DataEntry.COL_TITLE,
				DataEntry.COL_BPM, 
				DataEntry.COL_INITIAL_PREF_PACE_M,
				DataEntry.COL_PREF_PACE_M,
				DataEntry.COL_PREF_PACE_KM,
				DataEntry.COL_FILE_LOC,
		};
		String orderBy = DataEntry.COL_MEDIASTOREID + " ASC";
		Cursor cursor = db.query(DataEntry.TABLE_NAME, allCol, null, null, null, null, orderBy);
		// closeDb();
		return cursor;
	}

	//consider moving this to the settings package
	/**
	 * Check the default unit stored in the shared preferences file
	 * @return int unitType  This returns 1 for miles and 2 for kilometres
	 */
	public int getUnitType() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
		return unitType;
	}

}