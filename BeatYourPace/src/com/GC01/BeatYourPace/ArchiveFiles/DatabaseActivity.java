package com.GC01.BeatYourPace.ArchiveFiles;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> This class uses the database helper and creates the queries etc to manage the music metadata database
 * 
 * 	<dt> Description:
 * 	<dd> This may not need to be an activity class
 *  <dd> Has methods that will be called from the Lets Run and Training Run modes
 *  <dd> The methods may be moved at a later point to those activities
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author sarahnicholson
 *
 */

import java.util.ArrayList;

import com.GC01.BeatYourPace.Database.DataModel;
import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
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
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseActivity extends Activity {
	
	/** Instantiates a subclass of SQLiteOpenHelper to access our database */
	DatabaseHelper dh = new DatabaseHelper(this, DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
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
		values.put(DataEntry.COL_MEDIASTOREID, msId);
		values.put(DataEntry.COL_TITLE, title);
		values.put(DataEntry.COL_ARTIST, artist);

		// Insert values into the database
		db.insert(DataEntry.DATABASE_NAME, null, values);
		
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
		ContentValues cv = new ContentValues();
		cv.put(DataEntry.COL_MEDIASTOREID, MediaStore.Audio.Media._ID);
		cv.put(DataEntry.COL_TITLE, MediaStore.Audio.Media.TITLE);
		cv.put(DataEntry.COL_ARTIST, MediaStore.Audio.Artists.ARTIST);

		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Artists.ARTIST};

		//Query the media store database using CursorLoader
		//the first null should refer to the activity, find out how to do this
		CursorLoader cLoad = new CursorLoader(null, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
		Cursor cursor = cLoad.loadInBackground();
		
		//iterate through the contents and then write them to our database
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			db.insert(DataEntry.DATABASE_NAME, null, cv);
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
	 * @param preferredPace	Double that is the preferred pace that this track should be used for
	 * @param trackId	Integer that is the unique reference to the track in the device's media store db
	 */
	public void addBpmPace(int bpm, float preferredPace, int trackId){
		// Insert the new values for BPM and pace using SQL
		SQLiteDatabase db = dh.getWritableDatabase();
		String sql = "UPDATE " + DataEntry.TABLE_NAME + "SET " + DataEntry.COL_BPM + "=" + bpm + "," + DataEntry.COL_PREF_PACE_M + "=" + preferredPace +"WHERE " + DataEntry.COL_MEDIASTOREID + "=" + trackId;
		db.execSQL(sql);
	}
	
	/** 
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 * 
	 * @param preferredPace	Double that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public ArrayList<DataModel> getAppropriateSongs(double preferredPace) {
		
		/** ArrayList to hold the meta data about the tracks to be played */
		ArrayList<DataModel> appropriateSongs = new ArrayList<DataModel>();

		/** String that builds the query */
		String query = "SELECT  * FROM " + DataEntry.TABLE_NAME + "WHERE COL_PACE = " + preferredPace;

		//Get a reference to readable DB
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		//Go over each row, build track and add it to list
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Create an entry for one track using the data fields from the data model
			DataModel trackModel = buildDataMod(cursor);

			// Add track to the playlist for the pace
			appropriateSongs.add(trackModel);
			cursor.moveToNext();
		}
		cursor.close();
		// return the completed playlist
		return appropriateSongs;
	}
	
	/**
	 * This method is used to read the output from the database and create one entry (used elsewhere to build an ArrayList)
	 * @param cursor   Uses the cursor which read lines from the database
	 * @return track   Holds one record from the database so it later be written to an arrayList
	 */
	  private DataModel buildDataMod(Cursor cursor) {
		    DataModel track = new DataModel();
		    track.setId(cursor.getInt(0));
			track.setMediastoreid(cursor.getInt(1));
			track.setArtist(cursor.getString(2));
			track.setTitle(cursor.getString(3));
			track.setBPM(cursor.getInt(4));
			track.setPreferredPace(cursor.getFloat(5));
		    return track;
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
		float prefPace;

		switch(bpm) {
		case 150:
			if (unitType == 2) {
				prefPace = (float) 16.0;
			} else {
				prefPace = (float) 10.0;
			}
			break;
		case 153:
			if (unitType == 2) {
				prefPace = (float) 14.0;
			} else {
				prefPace = (float) 9.0;
			}
			break;
		case 156:
			if (unitType == 2) {
				prefPace = (float) 12.0;
			} else {
				prefPace = (float) 8.0;
			}
			break;
		case 160:
			if (unitType == 2) {
				prefPace = (float) 10.0;
			} else {
				prefPace = (float) 7.0;
			}
			break;
		case 163:
			if (unitType == 2) {
				prefPace = (float) 9.0;
			} else {
				prefPace = (float) 6.0;
			}
			break;
		case 166:
			if (unitType == 2) {
				prefPace = (float) 8.0;
			} else {
				prefPace = (float) 5.0;
			}
			break;
		case 171:
			if (unitType == 2) {
				prefPace = (float) 6.0;
			} else {
				prefPace = (float) 4.0;
			}
			break;
		default:
			if (unitType == 2) {
				prefPace = (float) 10.0;
			} else {
				prefPace = (float) 7.0;
			}
			break;
		}
		return prefPace;
	}


}
