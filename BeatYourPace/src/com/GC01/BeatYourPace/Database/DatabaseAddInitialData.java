package com.GC01.BeatYourPace.Database;

/**
 * Provides methods to populate or update the data in the database when the app starts
 * 
 * @author sarahnicholson
 * @version 22/01/2014
 */

import com.GC01.BeatYourPace.BPM.RetrieveBpmService;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPaceNavMap;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPaceNavMap.InitPrefPaceVals;
import com.echonest.api.v4.EchoNestException;
import com.GC01.BeatYourPace.Main.ContextProvider;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.provider.MediaStore;
import android.util.Log;

public class DatabaseAddInitialData extends DatabaseAdapter {

	private static final String LOG_TAG = "DatabaseCreation";

	public String[] echoCols = new String[] {
			DataEntry.COL_ID,
			DataEntry.COL_BPM, 
			DataEntry.COL_ARTIST, 
			DataEntry.COL_TITLE,
			DataEntry.COL_INITIAL_PREF_PACE_M,
	};
	public String[] allCols = new String[] {
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
	public String orderBy = DataEntry.COL_MEDIASTOREID + " ASC";

	public DatabaseAddInitialData(Context ctx) {
		super(ContextProvider.getContext());
	}


	/** 
	 * Method to read the device's media db and add metadata from it to our database 
	 * This method is only used when the byp database is empty
	 */
	@SuppressLint("NewApi")
	public void addTracks(){

		openDbWrite();

		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};

		//Some audio may be explicitly marked as not being music, the following excludes this
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

		CursorLoader cLoad = new CursorLoader(ContextProvider.getContext(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
		Cursor cursor = cLoad.loadInBackground();

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String title = cursor.getString(1); 
				String artist = cursor.getString(2);
				String fileLoc = cursor.getString(3);

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
		closeDb();
		Log.d(LOG_TAG, "Tracks added");
	}

	/**
	 * Method to add or delete tracks from the byp database to synchronise with media on the device 
	 */
	@SuppressLint("NewApi")
	public void synchTracks() {
		openDbWrite();

		Cursor cursorA = db.query(DataEntry.TABLE_NAME, allCols, null, null, null, null, orderBy);

		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		CursorLoader cload = new CursorLoader(ContextProvider.getContext(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media._ID + " ASC");
		Cursor cursorB = cload.loadInBackground();

		//Join the two cursors and compare to see which tracks are not in both
		//the format of the switch is set by the CursorJoiner method in Android, no Default case
		//if cursorA(byp) is null then the code would fail so skip and go to the addTracks method instead
		if (cursorA != null && (cursorA.getCount() > 0)) {
			CursorJoiner cj = new CursorJoiner(cursorA, new String[]{DataEntry.COL_MEDIASTOREID}, cursorB, new String[]{MediaStore.Audio.Media._ID});
			for (CursorJoiner.Result joinRes : cj) {
				switch (joinRes) {
				case LEFT:
					int rowid = cursorA.getInt(cursorA.getColumnIndex(DataEntry.COL_ID));
					db.delete(DataEntry.TABLE_NAME, DataEntry.COL_ID + " = " + rowid, null);
					break;
				case RIGHT: 
					int msid = cursorB.getInt(0);
					String title = cursorB.getString(1); 
					String artist = cursorB.getString(2);
					String fileLoc = cursorB.getString(3);

					ContentValues cv = new ContentValues();
					cv.put(DataEntry.COL_MEDIASTOREID, msid);
					cv.put(DataEntry.COL_TITLE, title);
					cv.put(DataEntry.COL_ARTIST, artist);
					cv.put(DataEntry.COL_FILE_LOC, fileLoc);
					db.insert(DataEntry.TABLE_NAME, null, cv);
					break;
				case BOTH:
					break;
				}
			}
		} else {
			addTracks();
		}
		cursorA.close();
		cursorB.close();
		closeDb();
		Log.d(LOG_TAG, "Track data synchronised");
	}

	/** 
	 * Method to add the BPM data for a track to the database
	 * BPM is calculated by the RetrieveBpmService.class
	 * @throws EchoNestException 
	 */
	public void addBpm() throws EchoNestException{

		openDbWrite();

		Cursor cursor = db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);;

		RetrieveBpmService bpmr = new RetrieveBpmService();

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
		Log.d(LOG_TAG, "BPM data added");
	}



	/** 
	 * Method to add the initial preferred pace data for a track to the database
	 */
	public void addInitialPrefPace() {

		openDbWrite();

		Cursor cursor = db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);	

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex(DataEntry.COL_ID));
				int bpm = cursor.getInt(cursor.getColumnIndex(DataEntry.COL_BPM));
				float initialPrefPaceM = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_M));
				// float initialPrefPaceKm = cursor.getFloat(cursor.getColumnIndex(DataEntry.COL_INITIAL_PREF_PACE_KM));
				float initialPrefPaceKm;
				
				if (initialPrefPaceM > 0) {
					
				} else {
					InitialPrefPaceNavMap ipp = new InitialPrefPaceNavMap();
					InitPrefPaceVals ippv = ipp.calcInitPrefPace(bpm);
					initialPrefPaceM = ippv.getIPPM();
					initialPrefPaceKm = ippv.getIPPKM();

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
}
