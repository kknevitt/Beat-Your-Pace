package com.GC01.BeatYourPace.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.GC01.BeatYourPace.BPM.*;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPace;
import com.echonest.api.v4.EchoNestException;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.util.Log;

@SuppressLint("NewApi")
public class DatabaseAdapter {
	private final Context context;
	private DatabaseHelper DbHelper;
	private SQLiteDatabase db;
	private static final String LOG_TAG = "DatabaseAdapter";

	public DatabaseAdapter (Context ctx){
		this.context = ctx;
		DbHelper = new DatabaseHelper(context, DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
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


	/** Method to read the device's media db and add metadata from it to our database */

	@SuppressLint("NewApi")
	public void addTracks(){

		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};

		//Some audio may be explicitly marked as not being music
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

		//Query the media store database using CursorLoader
		CursorLoader cLoad = new CursorLoader(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
		Cursor cursor = cLoad.loadInBackground();

		//iterate through the contents and then write them to our database
		if (cursor.moveToFirst()) {
			do {

				int id = cursor.getInt(0);
				String title = cursor.getString(1); 
				String artist = cursor.getString(2);
				String data = cursor.getString(3);

				// create ContentValues to add the content from the media store table to the equivalent column in our database
				ContentValues cv = new ContentValues();
				cv.put(DataEntry.COL_MEDIASTOREID, id);
				cv.put(DataEntry.COL_TITLE, title);
				cv.put(DataEntry.COL_ARTIST, artist);
				cv.put(DataEntry.COL_FILE_LOC, data);

				db.insert(DataEntry.TABLE_NAME, null, cv);
			} 
			while (cursor.moveToNext());
		}
		cursor.close();
	}

	/**
	 * Query all tracks
	 * @param cursor  New cursor to read the database
	 */

	public Cursor getAllTracks(){			
		//Get a reference to readable DB
		openDbRead();
		String[] allCol = new String[] {
				DataEntry.COL_ID, 
				DataEntry.COL_MEDIASTOREID, 
				DataEntry.COL_ARTIST, 
				DataEntry.COL_BPM, 
				DataEntry.COL_FILE_LOC,
				DataEntry.COL_INITIAL_PREF_PACE,
				DataEntry.COL_PREF_PACE,
				DataEntry.COL_TITLE,
		};
		return db.query(DataEntry.TABLE_NAME, allCol, null, null, null, null, null);
	}

	/**
	 * Query all tracks to get the artist, title and BPM for the EchoNest API call
	 * @param cursor  New cursor to read the database
	 */
	public Cursor getCols(){			
		//Get a reference to readable DB
		openDbRead();
		String[] echoCols = new String[] {
				DataEntry.COL_ID,
				DataEntry.COL_BPM, 
				DataEntry.COL_ARTIST, 
				DataEntry.COL_TITLE,
				DataEntry.COL_INITIAL_PREF_PACE,
		};
		return db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);
	}

	/** 
	 * Method to add the BPM data for a track to the database
	 * BPM is calculated by the RetrieveBpmService.class
	 * @throws EchoNestException 
	 */
	public void addBpm() throws EchoNestException{
		// Open the database
		openDbWrite();

		//Get the data about all tracks in the database
		Cursor cursor = getCols();

		//Empty object that allows you to call the getTempo function
		RetrieveBpmService bpmr = new RetrieveBpmService();
		
		//iterate through all tracks in cursor
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				int bpmCurrent = cursor.getInt(1);
				String artist = cursor.getString(2);
				String title = cursor.getString(3); 

				if (bpmCurrent > 1) {
					
				} else {
					int bpmNew = bpmr.getTempo(artist, title);

					ContentValues cv = new ContentValues();
					cv.put(DataEntry.COL_BPM, bpmNew);

					String where = DataEntry.COL_ID + " = " + id;

					db.update(DataEntry.TABLE_NAME, cv, where, null);
				}
			}
			while (cursor.moveToNext());
		}
		cursor.close();
		closeDb();
	}

	/** 
	 * Method to add the initial preferred pace data for a track to the database
	 */
	public void addInitialPrefPace() {
		// Open the database
		openDbWrite();

		//Get the data about all tracks in the database
		Cursor cursor = getCols();
		
		//Constructor to be able to call the GetInitPrefPace
		InitialPrefPace gipp = new InitialPrefPace();
		
		//iterate through all tracks in cursor
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				int bpm = cursor.getInt(1);
				float initialPrefPace = cursor.getFloat(4);

				if (initialPrefPace > 0) {
					//do nothing
				} else {
					
					initialPrefPace = gipp.calcInitPrefPace(bpm);

					ContentValues cv = new ContentValues();
					cv.put(DataEntry.COL_INITIAL_PREF_PACE, initialPrefPace);

					String where = DataEntry.COL_ID + " = " + id;

					db.update(DataEntry.TABLE_NAME, cv, where, null);
				}
			}
			while (cursor.moveToNext());
		}
		cursor.close();
		closeDb();
	}
	
	
	/** 
	 * Method to add the PreferredPace data for a track
	 * @param preferredPace	Double that is the preferred pace that this track should be used for
	 * @param trackId	Integer that is the unique reference to the track in the device's media store db
	 */
	public void addPrefPace(float preferredPace, int trackId){
		// Insert the new values for BPM and pace using SQL
		openDbWrite();

		//Sql statement to update preferred pace
		String sql = "UPDATE " + DataEntry.TABLE_NAME + "SET " + DataEntry.COL_PREF_PACE + "=" + preferredPace +"WHERE " + DataEntry.COL_MEDIASTOREID + "=" + trackId;
		db.execSQL(sql);

		closeDb();
	}


	/** 
	 * Get all tracks for a given pace and return ArrayList called appropriateSongs 
	 * This needs to be modified to allow for the contentURI or the filepath of the tracks to be returned
	 * 
	 * @param preferredPace	Float that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public ArrayList<String> getAppropriateSongs(float targetPace) {

		//List that holds just the path name to the track
		ArrayList<String> appropriateSongs = new ArrayList<String>();

		//String that builds the query
		String query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE + " IS NULL OR " + DataEntry.COL_PREF_PACE + " = " + targetPace + " )";

		//Get a reference to readable DB
		openDbRead();
		Cursor cursor = db.rawQuery(query2, null);

		//Go over each row, build track and add it to list
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Create an entry for one track using the path only
			float initPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE));
			float preferredPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_PREF_PACE));
			String fileLoc = cursor.getString(cursor.getColumnIndex(DataEntry.COL_FILE_LOC));
			
			if (preferredPace == 0){
				if (initPrefPace == targetPace) {
					appropriateSongs.add(fileLoc);
				}
			} else if (preferredPace == targetPace){
				appropriateSongs.add(fileLoc);
			} else {
				//do nothing
			}
			cursor.moveToNext();
		}
		cursor.close();
		// return the completed playlist
		return appropriateSongs;
	}

	/**
	 * Update database method yet to be implemented, this needs to look for changes in the audio db
	 * and replicate to this db. Changes must not overwrite existing records.
	 */
	public void updateTable(){

	}


	/**
	 * Method to delete a track
	 * @return 
	 */
	public boolean delTrack(int rowId) {
		return db.delete(DataEntry.TABLE_NAME, DataEntry.COL_ID + "=" + rowId, null) > 0;
	}

}