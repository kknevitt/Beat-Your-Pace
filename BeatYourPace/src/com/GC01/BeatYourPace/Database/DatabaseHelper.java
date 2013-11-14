package com.GC01.BeatYourPace.Database;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> A helper class to manage database creation and version management.
 * 
 * 	<dt> Description:
 * 	<dd> This version does not yet have an associated content provider so there is code here that needs to be 
 *  <dd> moved to another class.
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author snichols
 *
 */

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	//Database version
	public static final int DATABASE_VERSION = 1;
	
	//Use the defaultTargetPace
	static double defaultTargetPace = DataModel.getDefaultTargetPace();

	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DataModel.DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// SQL statement to create TrackData table
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + DataModel.TABLE_NAME + "(" + DataModel.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DataModel.COL_MEDIASTOREID + " INTEGER,"+ DataModel.COL_TITLE + " TEXT," + DataModel.COL_ARTIST + " TEXT,"  + DataModel.COL_BPM + " INTEGER," + DataModel.COL_PREF_PACE + " DOUBLE)"; 

		// create the table
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// REFERENCE DB PROGRAMMING BOOK CH2 
		Log.w("LOG_TAG", "Upgrading database from version "+ oldVersion + " to " + newVersion + ", which will destroy all old data");

		// Delete existing tables
		db.execSQL("DROP TABLE IF EXISTS " + DataModel.TABLE_NAME);

		// Create new instance of schema
		onCreate(db);
	}

	public void addTrack(Integer msId, String title, String artist) {
		//get reference to a writable database
		SQLiteDatabase db = this.getWritableDatabase();

		// create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(DataModel.COL_MEDIASTOREID, msId);
		values.put(DataModel.COL_TITLE, title);
		values.put(DataModel.COL_ARTIST, artist);

		// Insert values into the database
		db.insert(DataModel.DATABASE_NAME, null, values);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void addTracks(){
		//open the database in write mode
		SQLiteDatabase db = this.getWritableDatabase();

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

	/*
	 * Update database method yet to be implemented, this needs to look for changes in the audio db
	 * and replicate to this db. Changes must not overwrite existing records.
	 */
	public void updateTable(){

	}

	/*
	 * Method to add the BPM and initial Pace data for a track
	 * BPM is calculated in a separate class
	 */
	public void addBpmPace(int bpm, double pace, int trackId){
		// Insert the new values for BPM and pace using SQL
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "UPDATE " + DataModel.TABLE_NAME + "SET " + DataModel.COL_BPM + "=" + bpm + "," + DataModel.COL_PREF_PACE + "=" + pace +"WHERE " + DataModel.COL_MEDIASTOREID + "=" + trackId;
		db.execSQL(sql);
	}

	/*
	 * Method to delete records method yet to be implemented
	 */
	public void deleteTrack() {

	}

	/*
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 */
	public ArrayList<DataModel> getAppropriateSongs(double targetPace) {

		ArrayList<DataModel> appropriateSongs = new ArrayList<DataModel>();

		//Build the query
		String query = "SELECT  * FROM " + DataModel.TABLE_NAME + "WHERE COL_PACE = " + targetPace;

		//Get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
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
	
	
	/*
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
	
	/*
	 * This method decreases the preferred pace by 0.5
	 * It needs to identify which track object is current
	 * Then it changes the defaultPace value for that track
	 * The new value then needs to be written back to the database
	 * NOT YET WRITTEN
	 */
	public static void incPrefPace() {
		
	}
	
}