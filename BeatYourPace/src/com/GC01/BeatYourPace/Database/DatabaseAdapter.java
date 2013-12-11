package com.GC01.BeatYourPace.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.GC01.BeatYourPace.BPM.*;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPace;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPace.InitPrefPaceVals;
import com.GC01.BeatYourPace.Settings.SettingsActivity;
import com.echonest.api.v4.EchoNestException;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

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


	/** 
	 * Method to read the device's media db and add metadata from it to our database 
	 * This method is only used when the byp database is empty
	 */
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
				//addData(cursor);
				int id = cursor.getInt(0);
				String title = cursor.getString(1); 
				String artist = cursor.getString(2);
				String fileLoc = cursor.getString(3);

				// create ContentValues to add the content from the media store table to the equivalent column in our database
				ContentValues cv = new ContentValues();
				cv.put(DataEntry.COL_MEDIASTOREID, id);
				cv.put(DataEntry.COL_TITLE, title);
				cv.put(DataEntry.COL_ARTIST, artist);
				cv.put(DataEntry.COL_FILE_LOC, fileLoc);

				db.insert(DataEntry.TABLE_NAME, null, cv);
			} 
			while (cursor.moveToNext());
		}
		cursor.close();
	}

	/**
	 * Method is used by addTracks and synchTracks to write data to byp
	 */
	private void addData(Cursor cursor){
		int id = cursor.getInt(cursor.getColumnIndex(DataEntry.COL_ID));
		String title = cursor.getString(cursor.getColumnIndex(DataEntry.COL_TITLE)); 
		String artist = cursor.getString(cursor.getColumnIndex(DataEntry.COL_ARTIST));
		String fileLoc = cursor.getString(cursor.getColumnIndex(DataEntry.COL_FILE_LOC));

		// create ContentValues to add the content from the media store table to the equivalent column in our database
		ContentValues cv = new ContentValues();
		cv.put(DataEntry.COL_MEDIASTOREID, id);
		cv.put(DataEntry.COL_TITLE, title);
		cv.put(DataEntry.COL_ARTIST, artist);
		cv.put(DataEntry.COL_FILE_LOC, fileLoc);

		db.insert(DataEntry.TABLE_NAME, null, cv);
	}
	
	
	/**
	 * Method to add or delete tracks from the byp database to synchronise with media on the device
	 * @return 
	 * @return 
	 */
	public void synchTracks() {
		// Open the byp database and get the data about all tracks in the database
		openDbWrite();
		Cursor cursorA = getAllTracks();

		//Open the mediastore database and get data about all the music on the device
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		CursorLoader cload = new CursorLoader(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media._ID + " ASC");
		Cursor cursorB = cload.loadInBackground();

		//Join the two cursors and compare to see which tracks are not in both
		//the format of the switch is set by the CursorJoiner method in Android, no Default case
		//if cursorA(byp) is null then the code would fail so skip and go to the addTracks method instead
		if (cursorA != null && (cursorA.getCount() > 0)) {
		CursorJoiner cj = new CursorJoiner(cursorA, new String[]{DataEntry.COL_MEDIASTOREID}, cursorB, new String[]{MediaStore.Audio.Media._ID});
		for (CursorJoiner.Result joinRes : cj) {
			switch (joinRes) {
			case LEFT:
				// where a row in cursorA(byp) is unique delete tracks from byp
				int rowid = cursorA.getInt(cursorA.getColumnIndex(DataEntry.COL_ID));
				db.delete(DataEntry.TABLE_NAME, DataEntry.COL_ID + " = " + rowid, null);
				break;
			case RIGHT: 
				// where a row in cursorB(mediastore) is unique add tracks to byp
				//addData(cursorB);  this fails
				int msid = cursorB.getInt(0);
				String title = cursorB.getString(1); 
				String artist = cursorB.getString(2);
				String fileLoc = cursorB.getString(3);

				// create ContentValues to add the content from the media store table to the equivalent column in our database
				ContentValues cv = new ContentValues();
				cv.put(DataEntry.COL_MEDIASTOREID, msid);
				cv.put(DataEntry.COL_TITLE, title);
				cv.put(DataEntry.COL_ARTIST, artist);
				cv.put(DataEntry.COL_FILE_LOC, fileLoc);

				db.insert(DataEntry.TABLE_NAME, null, cv);
				break;
			case BOTH:
				// where a row with the same key is in both cursors do nothing
				break;
			}
		}
		} else {
			addTracks();
		}
		cursorA.close();
		cursorB.close();
	}
	
	/**
	 * Query all tracks and all data fields
	 * @return cursor  New cursor to read the database and give the results
	 */
	public Cursor getAllTracks(){			
		//Get a reference to readable DB
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
		//Get a reference to readable DB
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
					//do nothing
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

		

		//iterate through all tracks in cursor
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex(DataEntry.COL_ID));
				int bpm = cursor.getInt(cursor.getColumnIndex(DataEntry.COL_BPM));
				float initialPrefPace = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));

				if (initialPrefPace > 0) {
					//do nothing
				} else {
					//Calculate the initial pref pace values for M and Km
					InitialPrefPace gipp = new InitialPrefPace();
					InitPrefPaceVals ippv = gipp.calcInitPrefPace(bpm);
					float initialPrefPaceM = ippv.getIPPM();
					float initialPrefPaceKm = ippv.getIPPKM();

					ContentValues cv = new ContentValues();
					cv.put(DataEntry.COL_INITIAL_PREF_PACE_M, initialPrefPaceM);
					cv.put(DataEntry.COL_INITIAL_PREF_PACE_KM, initialPrefPaceKm);

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
	 * Method to add the preferred pace data for a track
	 * @param prefPace	Float that is the preferred pace that this track should be used for
	 * @param fileLoc	String that is the file loc reference to the track in the device's media store db
	 * as this is the only info that is available in the TrackList array
	 */
	public void addPrefPace(float preferredPace, String fileLoc){
		//Find out if they are using Miles (1) or Km (2)
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(preferences.getString("unitType", "1"));

	
		// Open the database in write mode
		openDbWrite();

		//Sql statement to update preferred pace
		if (unitType == 1) {
			String sql = "UPDATE " + DataEntry.TABLE_NAME + "SET " + DataEntry.COL_PREF_PACE_M + "= " + preferredPace +"WHERE " + DataEntry.COL_FILE_LOC + "= " + fileLoc;
			db.execSQL(sql);
		} else {
			String sql = "UPDATE " + DataEntry.TABLE_NAME + "SET " + DataEntry.COL_PREF_PACE_KM + "= " + preferredPace +"WHERE " + DataEntry.COL_FILE_LOC + "= " + fileLoc;
			db.execSQL(sql);
		}
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
		
		//Get the user preference for miles(1) or kilometres(2)
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
		
		
		String query2;
		if (unitType == 1) {
			//String that builds the query for miles
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_M + " = " + targetPace + " )";
		} else {
			//String that builds the query for km
			query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_KM + " = " + targetPace + " )";
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
			} else if (preferredPace >= targetPace - 0.5 || preferredPace <= targetPace + 0.5){
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
	 * This method returns the artist and title for a track so that the media player can display the info
	 * 
	 * @param fileLoc  String with the location of the file for which the artist and title is needed
	 * @return  trackInfo  ArrayList with the artist and title
	 */
	public String getTrackInfo(String fileLoc){
		
		String query = "SELECT " + DataEntry.COL_ARTIST + ", " + DataEntry.COL_TITLE + " FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_FILE_LOC + " = " + "\"" + fileLoc + " \""+ " )";
		//String selection = "DataEntry.COL_FILE_LOC = " + fileLoc;
		
	
		String[] cols = new String[] {DataEntry.COL_ARTIST, DataEntry.COL_TITLE};
		
		
		//Open the database, read to a cursor, go over each row, build track and add it to list
		openDbRead();
		//Cursor cursor = db.rawQuery(query, null);
		Cursor cursor = db.query(DataEntry.TABLE_NAME, cols, null, null, null, null, null);
		
		String artist = cursor.getString(0);
		String title = cursor.getString(1);
		String space = " ";
		
		System.out.println(artist);
		System.out.println(title);
		
		//String trackInfo = artist + " " + title
		String trackInfo = artist.concat(space.concat(title));
		System.out.println(trackInfo);
		
		return trackInfo;
	}

	/**
	 * Returns the file location, artist and title in a single array list for the music player
	 * @param targetPace  Float of the target pace the user wants music for
	 * @return appropriateSongs  Array list with the file location, artist and title of tracks matching the pace
	 */
	public ArrayList<String> getTrackInfoFull(float targetPace) {
	//List that holds just the path name to the track
			ArrayList<String> appropriateSongs = new ArrayList<String>();
			
			//Get the user preference for miles(1) or kilometres(2)
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
			int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
			
			String query2;
			if (unitType == 1) {
				//String that builds the query for miles
				query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_M + " = " + targetPace + " )";
			} else {
				//String that builds the query for km
				query2 = "SELECT * FROM " + DataEntry.TABLE_NAME + " WHERE (" + DataEntry.COL_PREF_PACE_M + " IS NULL OR " + DataEntry.COL_PREF_PACE_KM + " = " + targetPace + " )";
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
			// return the completed playlist
			Toast toast = Toast.makeText(ContextProvider.getContext(), "No music for this pace. Try increasing or decreasing the pace.", Toast.LENGTH_LONG);
			toast.show();
			return appropriateSongs;
	}
}

