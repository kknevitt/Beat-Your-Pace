package com.GC01.BeatYourPace.Database;

//This class should be ultimately replaced by the DatabaseAdapter & DatabaseMusicPlayer classes which is a more efficient structure

/**
 * Provides common methods for the classes that query the database such as open, close, and query all data
 * Additionally contains methods to search the database for the MusicPlayer.
 * 
 * @author sarahnicholson
 * @version 22/01/2014
 */


import java.util.ArrayList;

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.Main.ContextProvider;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

@SuppressLint("NewApi")
public class DatabaseAdapter1 {
	private final Context context;
	private DatabaseHelper DbHelper;
	private SQLiteDatabase db;
	private static final String LOG_TAG = "DatabaseAdapter";

	public DatabaseAdapter1 (Context ctx){
		this.context = ctx;
		DbHelper = new DatabaseHelper(context, DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
	}

	/**
	 * Method to open the database in writable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAdapter1 openDbWrite() throws SQLException {
		db = DbHelper.getWritableDatabase();
		return this;
	}

	/**
	 *  Method to open the database in readable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAdapter1 openDbRead() throws SQLException {
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
	public Cursor getAllTracks(){			
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
		return db.query(DataEntry.TABLE_NAME, allCol, null, null, null, null, orderBy);
	}

	/**
	 * Query all tracks to get the artist, title and BPM for the EchoNest API call
	 * @param cursor  New cursor to read the database
	 */
	public Cursor getCols(){			
		openDbRead();
		String[] echoCols = new String[] {
				DataEntry.COL_ID,
				DataEntry.COL_BPM, 
				DataEntry.COL_ARTIST, 
				DataEntry.COL_TITLE,
				DataEntry.COL_INITIAL_PREF_PACE_M,
		};
		return db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);
	}

	/** 
	 * Method to add the preferred pace data for a track
	 * @param incremenet	Float that is value of the change to the preferred pace (either 0.5 or - 0.5)
	 * @param fileLoc	String that is the file location reference to the track in the device's media store db
	 * as this is the only info that is available in the TrackList array
	 */
	public void addPrefPace(Float increment, String fileLoc){
		//Find out if they are using Miles (1) or Km (2)
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
		float prefPace;
		float initPrefPace;

		closeDb();
		
		
		DbHelper = new DatabaseHelper(ContextProvider.getContext(), DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
		db = DbHelper.getWritableDatabase();
		
		String[] cols = new String[] {
				DataEntry.COL_INITIAL_PREF_PACE_M,
				DataEntry.COL_INITIAL_PREF_PACE_KM,
				DataEntry.COL_PREF_PACE_M,
				DataEntry.COL_PREF_PACE_KM,
				DataEntry.COL_FILE_LOC
		};
		
		String selection = DataEntry.COL_FILE_LOC + " = " + "\"" + fileLoc + "\"";
		
		Cursor cursor = db.query(DataEntry.TABLE_NAME, cols, selection, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if (unitType == 1) {
				if (cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M)) == (float) 0.0) {
					initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
					prefPace = initPrefPace + increment;
					String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_M + " = " + "\"" + prefPace + "\"" +" WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
					db.execSQL(sql);
				} else {
					prefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M));
					prefPace += increment;
					String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_M + " = " + "\"" + prefPace + "\"" + " WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
					db.execSQL(sql);
				}
			} else {
				if (cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM)) == 0) {
					initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_KM));
					prefPace = initPrefPace + increment;
					String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_KM + " = " + "\"" + prefPace + "\"" +" WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
					db.execSQL(sql);
				} else {
					prefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM));
					prefPace += increment;
					String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_KM + " = " + "\""+ prefPace + "\"" + " WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
					db.execSQL(sql);
				}
			}
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
	}


	/** 
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 * 
	 * @param preferredPace	Float that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public ArrayList<String> getAppropriateSongs(float targetPace) {

		ArrayList<String> appropriateSongs = new ArrayList<String>();
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(sp.getString("unitType", "1"));
		
		String query2;
		
		if (unitType == 1) {
			//String that builds the query for miles
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_M + " = " + targetPace + " OR " + "(" + DataEntry.COL_PREF_PACE_M + " * 2)" +  " = " + targetPace + " OR " + "(" + DataEntry.COL_PREF_PACE_M + " / 2)" +  " = " + targetPace + ")";
		} else {
			//String that builds the query for km
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_KM + " = " + targetPace + "  OR " + "(" + DataEntry.COL_PREF_PACE_KM + " * 2)" +  " = " + targetPace + "  OR " + "(" + DataEntry.COL_PREF_PACE_KM + " / 2)" +  " = " + targetPace+ ")";
		}
		
		//Open the database, read to a cursor, go over each row, build track and add it to list
		openDbRead();
		Cursor cursor = db.rawQuery(query2, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			float initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
			//get the preferred pace in miles or km
			float preferredPace;
			if (unitType == 1) {
				preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M));
			} else {
				preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM));
			}
			String fileLoc = cursor.getString(cursor.getColumnIndex(DataEntry.COL_FILE_LOC));
			//add the tracks file location to the array, check the default column if there is no 
			if (preferredPace == 0){
				if (initPrefPace == targetPace) {
					appropriateSongs.add(fileLoc);
				}
			} else if (preferredPace >= targetPace - 0.5 && preferredPace <= targetPace + 0.5){
				appropriateSongs.add(fileLoc);
			} else {
				//do nothing
			}
			cursor.moveToNext();
		}
		cursor.close();
		closeDb();
		// return the completed playlist
		return appropriateSongs;
		
	}

	/**
	 * This method returns the artist and title for a track so that the media player can display the info
	 * 
	 * @param fileLoc  String with the location of the file for which the artist and title is needed
	 * @return  trackInfo  String with the artist and title
	 */
	public String getTrackInfo(String fileLoc){

		String[] cols = new String[] {DataEntry.COL_ARTIST, DataEntry.COL_TITLE};

		//Open the database, read to a cursor, go over each row, build track and add it to list
		openDbRead();
		Cursor cursor = db.query(DataEntry.TABLE_NAME, cols, "fileLoc = " + "\"" + fileLoc + "\"" , null, null, null, null);
		if (cursor == null) {
			Log.d(LOG_TAG, "No artist and title data for the track");
		}
		String trackInfo = "";
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			String artist = cursor.getString(cursor.getColumnIndex(DataEntry.COL_ARTIST));
			String title = cursor.getString(cursor.getColumnIndex(DataEntry.COL_TITLE));
			trackInfo = artist + " - " + title;
			cursor.moveToNext();
		}
		cursor.close();
		closeDb();
		return trackInfo;
	}

	/**
	 * Returns the file location, artist and title in a single array list for the music player
	 * @param targetPace  Float of the target pace the user wants music for
	 * @return appropriateSongs  Array list with the file location, artist and title of tracks matching the pace
	 */
	public ArrayList<String> getAppropriateSongsTrackData(float targetPace) {
	//List that holds just the path name to the track
			ArrayList<String> appropriateSongs = new ArrayList<String>();
			
			//Get the user preference for miles(1) or kilometres(2)
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
			int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
			
			String query2;
			if (unitType == 1) {
				//String that builds the query for miles
				query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_M + " = " + targetPace + " OR " + "(" + DataEntry.COL_PREF_PACE_M + " * 2)" +  " = " + targetPace + " OR " + "(" + DataEntry.COL_PREF_PACE_M + " / 2)" +  " = " + targetPace + ")";
			} else {
				//String that builds the query for km
				query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_KM + " = " + targetPace + "  OR " + "(" + DataEntry.COL_PREF_PACE_KM + " * 2)" +  " = " + targetPace + "  OR " + "(" + DataEntry.COL_PREF_PACE_KM + " / 2)" +  " = " + targetPace+ ")";
			}
			
			//Open the database, read to a cursor, go over each row, build track and add it to list
			openDbRead();
			Cursor cursor = db.rawQuery(query2, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				float initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
				//get the preferred pace in miles or km
				float preferredPace;
				if (unitType == 1) {
					preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M));
				} else {
					preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM));
				}
				String fileLoc = cursor.getString(cursor.getColumnIndex(DataEntry.COL_FILE_LOC));
				String artist = cursor.getString(cursor.getColumnIndex(DataEntry.COL_ARTIST));
				String title = cursor.getString(cursor.getColumnIndex(DataEntry.COL_TITLE));
				//add the tracks file location to the array, check the default column if there is no 
				if (preferredPace == 0){
					if (initPrefPace == targetPace) {
						appropriateSongs.add(fileLoc);
						appropriateSongs.add(artist);
						appropriateSongs.add(title);
					}
				} else if (preferredPace >= targetPace - 0.5 || preferredPace <= targetPace + 0.5){
					appropriateSongs.add(fileLoc);
					appropriateSongs.add(artist);
					appropriateSongs.add(title);
				} else {
					//do nothing
				}
				cursor.moveToNext();
			}
			cursor.close();
			// return the completed play list
			return appropriateSongs;
	}
}