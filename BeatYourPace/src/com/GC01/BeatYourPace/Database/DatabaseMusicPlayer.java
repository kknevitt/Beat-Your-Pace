package com.GC01.BeatYourPace.Database;

//Not yet in use as Music Player currently hangs with this code.

/**
 * Provides methods to allow the music player to get data from and write to the database
 * Allows the music player to identify appropriate tracks to play, add preferred pace and get details 
 * about the artist and song title.
 * @author sarahnicholson
 */

import java.util.ArrayList;

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.Settings.GetSettings;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DatabaseMusicPlayer extends DatabaseAdapter {

	private final String LOG_TAG = "DatabaseMusicPlayer";

	public DatabaseMusicPlayer(Context ctx) {
		super(ctx);
	}

	/** 
	 * Method to add the preferred pace data for a track
	 * @param incremenet	Float that is value of the change to the preferred pace (either 0.5 or - 0.5)
	 * @param fileLoc	String that is the file location reference to the track in the device's media store db
	 * as this is the only info that is available in the TrackList array
	 */
	public void addPrefPace(Float increment, String fileLoc){
	
		GetSettings gs = new GetSettings();
		int unitType = gs.getUnitType();
		
		float prefPace;
		float initPrefPace;

		String[] cols = new String[] {
				DataEntry.COL_INITIAL_PREF_PACE_M,
				DataEntry.COL_INITIAL_PREF_PACE_KM,
				DataEntry.COL_PREF_PACE_M,
				DataEntry.COL_PREF_PACE_KM,
				DataEntry.COL_FILE_LOC
		};

		String selection = DataEntry.COL_FILE_LOC + " = " + "\"" + fileLoc + "\"";

		openDbWrite();
		Cursor cursor = db.query(DataEntry.TABLE_NAME, cols, selection, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if (unitType == 1) {
				if (cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M)) == (float) 0.0) {
					initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
					prefPace = initPrefPace + increment;
				} else {
					prefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M));
					prefPace += increment;
				}
				String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_M + " = " + "\"" + prefPace + "\"" + " WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
				db.execSQL(sql);
			} else {
				if (cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM)) == (float) 0.0) {
					initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_KM));
					prefPace = initPrefPace + increment;
				} else {
					prefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM));
					prefPace += increment;
				}
				String sql = "UPDATE " + DataEntry.TABLE_NAME + " SET " + DataEntry.COL_PREF_PACE_KM + " = " + "\""+ prefPace + "\"" + " WHERE " + DataEntry.COL_FILE_LOC + "= " + "\"" + fileLoc + "\"";
				db.execSQL(sql);
			}
			cursor.moveToNext();
		}
		cursor.close();
		closeDb();
		Log.d(LOG_TAG, "Preferred pace added");
	}


	/** 
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 * 
	 * @param preferredPace	Float that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public ArrayList<String> getAppropriateSongs(float targetPace) {

		//List that holds just the path name to the track as this is what the music player needs
		ArrayList<String> appropriateSongs = new ArrayList<String>();

		GetSettings gs = new GetSettings();
		int unitType = gs.getUnitType();

		String query2;
		
		if (unitType == 1) {
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_M + " = " + targetPace + " )";
		} else {
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_KM + " = " + targetPace + " )";
		}

		openDbRead();
		Cursor cursor = db.rawQuery(query2, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			float initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
			float preferredPace;
			if (unitType == 1) {
				preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_M));
			} else {
				preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE_KM));
			}
			String fileLoc = cursor.getString(cursor.getColumnIndex(DataEntry.COL_FILE_LOC));
			//initially there won't be a user defined pace so the initial value will be the auto-calculated pace 
			if (preferredPace == 0){
				if (initPrefPace == targetPace) {
					appropriateSongs.add(fileLoc);
					cursor.moveToNext();
				}
			//to provide the runner with a wider selection otherwise the number of songs at each pace may be limited
			} else if (preferredPace >= targetPace - 0.5 || preferredPace <= targetPace + 0.5){
				appropriateSongs.add(fileLoc);
				cursor.moveToNext();
			} else {
				cursor.moveToNext();
			}
		}
		Log.d(LOG_TAG, "Track list created");
		cursor.close();
		closeDb();
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
		Log.d(LOG_TAG, "Track detail provided");
		return trackInfo;
	}
}