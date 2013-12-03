package com.GC01.BeatYourPace.Database;

import java.util.ArrayList;
import java.util.List;

import com.GC01.BeatYourPace.BPM.*;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

@SuppressLint("NewApi")
public class DatabaseAdapter {
	private final Context context;
	private DatabaseHelper DbHelper;
	private SQLiteDatabase db;
	
	public DatabaseAdapter (Context ctx){
		this.context = ctx;
		DbHelper = new DatabaseHelper(context, null, null, 0);
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
		// create ContentValues to add the content from the media store table to the equivalent column in our database
		ContentValues cv = new ContentValues();
		cv.put(DataEntry.COL_MEDIASTOREID, MediaStore.Audio.Media._ID);
		cv.put(DataEntry.COL_TITLE, MediaStore.Audio.Media.TITLE);
		cv.put(DataEntry.COL_ARTIST, MediaStore.Audio.Artists.ARTIST);
		cv.put(DataEntry.COL_FILE_LOC, MediaStore.Audio.Media.DATA);

		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};

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
	}
	
	/**
	 * Query all tracks
	 * @param cursor  New cursor to read the database
	 * 
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
	 * Method to add the BPM data for a track
	 * BPM is calculated in a separate class
	 */
	public void addBpm(){
		// Insert the new values for BPM and pace using SQL
		openDbWrite();

		//Get all the track data
		Cursor cursor = getAllTracks();

		//iterate through all tracks in cursor
		//use the file loc to call get getBPM
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
		/*
			int bpm = getBPM(File file);
			ContentValues cv = new ContentValues();
			​​​​​​​​cv.put(DataEntry.COL_BPM, bpm);
			​​*/
		}
		cursor.close();
		closeDb();
	}
	
	/** 
	 * Method to add the initial PreferredPace data for a track
	 * BPM is calculated in a separate class
	 * 
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
	 * @param preferredPace	Double that is the preferred pace that this track should be used for
	 * @return appropriateSongs	An array list of the meta data about the tracks to be played
	 */

	public List<String> getAppropriateSongs(float preferredPace) {

		//List that holds just the path name to the track
		List<String> appropriateSongs = new ArrayList<String>();
		
		//String that builds the query
		String query2 = "SELECT COL_FILE_LOC FROM " + DataEntry.TABLE_NAME + "WHERE COL_PACE = " + preferredPace;

		//Get a reference to readable DB
		openDbRead();
		Cursor cursor = db.rawQuery(query2, null);

		//Go over each row, build track and add it to list
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Create an entry for one track using the path only
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

			// Add track to the playlist for the pace
			appropriateSongs.add(path);
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