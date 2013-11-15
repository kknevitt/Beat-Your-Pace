package com.GC01.BeatYourPace.Database;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> This class uses the database helper and creates the queries etc to manage the music metadata database
 * 
 * 	<dt> Description:
 * 	<dd> This may not need to be an activity class
 *  <dd> Has methods to 
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author snichols
 *
 */

import java.util.ArrayList;

import com.GC01.BeatYourPace.Main.SettingsActivity;
import com.example.beatyourpace.R;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseActivity extends Activity {
	
	/** Instantiates a subclass of SQLiteOpenHelper to access our database */
	DatabaseHelper dh = new DatabaseHelper(this, DataModel.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
	//DatabaseActivityDatabaseHelper dh = new DatabaseHelper(getContext());
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		/** Field which calls the defaultTargetPace from settings */
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		double defaultTargetPace = sharedPref.getFloat("set_target_pace", (float) 6.0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.database, menu);
		return true;
	}
	
	/** Method to add a track to the database 
	 * @param msId		Integer of the mediastore db id of the track
	 * @param title 	String of the track title
	 * @param artist	String of the artist name
	 */
	public void addTrack(Integer msId, String title, String artist) {
		//get reference to the database in writable format
		SQLiteDatabase db = dh.getWritableDatabase();

		// create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(DataModel.COL_MEDIASTOREID, msId);
		values.put(DataModel.COL_TITLE, title);
		values.put(DataModel.COL_ARTIST, artist);

		// Insert values into the database
		db.insert(DataModel.DATABASE_NAME, null, values);
		
		//Close the database
		db.close();
	}
	

	/** Method to read the device's media db and add metadata from it to our database */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void addTracks(){
		//open the database in write mode
		SQLiteDatabase db = dh.getWritableDatabase();

		// create ContentValues to add the content from the media store table to the equivalent column in our database
		ContentValues values2 = new ContentValues();
		values2.put(DataModel.COL_MEDIASTOREID, MediaStore.Audio.Media._ID);
		values2.put(DataModel.COL_TITLE, MediaStore.Audio.Media.TITLE);
		values2.put(DataModel.COL_ARTIST, MediaStore.Audio.Artists.ARTIST);

		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Artists.ARTIST};

		//Query the media store database using CursorLoader
		//the first null should refer to the activity, find out how to do this
		CursorLoader cLoad = new CursorLoader(null, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
		Cursor cursor = cLoad.loadInBackground();
		
		//iterate through the contents and then write them to our database
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			db.insert(DataModel.DATABASE_NAME, null, values2);
			cursor.moveToNext();
		}
		cursor.close();

		//Close the database
		db.close();

	}
	
	/** 
	 * Method to add the BPM and initial PreferredPace data for a track
	 * BPM is calculated in a separate class
	 * 
	 * @param bpm		Integer that is the autocalculated BPM for a track
	 * @param prefPace	Double that is the preferred pace that this track should be used for
	 * @param trackId	Integer that is the unique reference to the track in the device's media store db
	 */
	public void addBpmPace(int bpm, double prefPace, int trackId){
		// Insert the new values for BPM and pace using SQL
		SQLiteDatabase db = dh.getWritableDatabase();
		String sql = "UPDATE " + DataModel.TABLE_NAME + "SET " + DataModel.COL_BPM + "=" + bpm + "," + DataModel.COL_PREF_PACE + "=" + prefPace +"WHERE " + DataModel.COL_MEDIASTOREID + "=" + trackId;
		db.execSQL(sql);
	}
	
	/** 
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 * 
	 * @param prefPace	Double that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public ArrayList<DataModel> getAppropriateSongs(double prefPace) {
		
		/** ArrayList to hold the meta data about the tracks to be played */
		ArrayList<DataModel> appropriateSongs = new ArrayList<DataModel>();

		//Build the query
		String query = "SELECT  * FROM " + DataModel.TABLE_NAME + "WHERE COL_PACE = " + prefPace;

		//Get reference to readable DB
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		//Go over each row, build track and add it to list
		//int numRows = cursor.getCount();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DataModel track = new DataModel();
			track.id = cursor.getInt(0);
			track.mediaStoreId = cursor.getInt(1);
			track.artist = cursor.getString(2);
			track.title = cursor.getString(3);
			track.bpm = cursor.getInt(4);
			track.preferredPace = cursor.getDouble(5);

			// Add track to the playlist for the pace
			appropriateSongs.add(track);
			cursor.moveToNext();
		}
		cursor.close();
		// return playlist
		return appropriateSongs;
	}
	
	/**
	 * Update database method yet to be implemented, this needs to look for changes in the audio db
	 * and replicate to this db. Changes must not overwrite existing records.
	 */
	public void updateTable(){

	}

	
	/**
	 * Method to delete records method yet to be implemented
	 */
	public void deleteTrack() {

	}

	
	/**
	 * This method increases the preferred pace by 0.5
	 * Check if the user default is km or m, default database value is in m
	 * Convert from km to m if needed
	 * Identify which track object is current
	 * Then it changes the defaultPace value for that track
	 * The new value then needs to be written back to the database
	 * NOT YET WRITTEN
	 */
	public static void decPrefPace() {
		
	}
	
	/**
	 * This method decreases the preferred pace by 0.5
	 * It needs to identify which track object is current
	 * Then it changes the defaultPace value for that track
	 * The new value then needs to be written back to the database
	 * NOT YET WRITTEN
	 */
	public static void incPrefPace() {
		
	}
	
	/**
	 * This method is used to add the value for the initial pace based on bpm
	 * The method needs additional work to allow for values in a range rather than specific values
	 * @param bpm  Integer that is the autocalculated BPM for a track
	 * @return prefPace	 	Double that is the preferred pace that this track should be used for
	 */
	public double calcPaceForBpm(int bpm){

		SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
		int unitType = sPref.getInt("unitType", 1);
		double pace;

		switch(bpm) {
		case 150:
			if (unitType == 2) {
				pace = 16.0;
			} else {
				pace = 10.0;
			}
			break;
		case 153:
			if (unitType == 2) {
				pace = 14.0;
			} else {
				pace = 9.0;
			}
			break;
		case 156:
			if (unitType == 2) {
				pace = 12.0;
			} else {
				pace = 8.0;
			}
			break;
		case 160:
			if (unitType == 2) {
				pace = 10.0;
			} else {
				pace = 7.0;
			}
			break;
		case 163:
			if (unitType == 2) {
				pace = 9.0;
			} else {
				pace = 6.0;
			}
			break;
		case 166:
			if (unitType == 2) {
				pace = 8.0;
			} else {
				pace = 5.0;
			}
			break;
		case 171:
			if (unitType == 2) {
				pace = 6.0;
			} else {
				pace = 4.0;
			}
			break;
		default:
			if (unitType == 2) {
				pace = 10.0;
			} else {
				pace = 7.0;
			}
			break;
		}
		return pace;
	}


}
