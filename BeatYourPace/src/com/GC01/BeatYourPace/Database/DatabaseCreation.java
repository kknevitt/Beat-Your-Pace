package com.GC01.BeatYourPace.Database;

//Work in progress - will be used to break up the large Database Adapter class

import com.GC01.BeatYourPace.BPM.RetrieveBpmService;
import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPace;
import com.GC01.BeatYourPace.PaceCalculator.InitialPrefPace.InitPrefPaceVals;
import com.echonest.api.v4.EchoNestException;
import com.GC01.BeatYourPace.Main.ContextProvider;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

@SuppressLint("NewApi")
public class DatabaseCreation extends DatabaseAbstract {

	private DatabaseHelper DbHelper;
	private SQLiteDatabase db;
	private static final String LOG_TAG = "Database creation";
	public String[] echoCols = new String[] {
			DataEntry.COL_ID,
			DataEntry.COL_BPM, 
			DataEntry.COL_ARTIST, 
			DataEntry.COL_TITLE,
			DataEntry.COL_INITIAL_PREF_PACE_M,
	};
	public String[] allCol = new String[] {
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
	
	public DatabaseCreation(Context ctx) {
		super(ContextProvider.getContext());
		//ctx = ContextProvider.getContext();
		// TODO Auto-generated constructor stub
	}


	/** 
	 * Method to read the device's media db and add metadata from it to our database 
	 * This method is only used when the byp database is empty
	 */
	@SuppressLint("NewApi")
	public void addTracks(){

		openDbWrite();
		
		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DATA};

		//Some audio may be explicitly marked as not being music
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

		//Query the media store database using CursorLoader
		CursorLoader cLoad = new CursorLoader(ContextProvider.getContext(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
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
		closeDb();
	}
	
	/**
	 * Method to add or delete tracks from the byp database to synchronise with media on the device
	 * @return 
	 * @return 
	 */
	@SuppressLint("NewApi")
	public void synchTracks() {
		// Open the byp database and get the data about all tracks in the database
		//openDbWrite();
		//DatabaseAdapter da = new DatabaseAdapter(ContextProvider.getContext());
		//Cursor cursorA = da.getAllTracks();
		
		Cursor cursorA = db.query(DataEntry.TABLE_NAME, allCol, null, null, null, null, orderBy);

		//Open the mediastore database and get data about all the music on the device
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
	 * Method to add the BPM data for a track to the database
	 * BPM is calculated by the RetrieveBpmService.class
	 * @throws EchoNestException 
	 */
	public void addBpm() throws EchoNestException{
		// Open the database
		openDbWrite();

		//Get the data about all tracks in the database
		Cursor cursor = db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);;

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
		Cursor cursor = db.query(DataEntry.TABLE_NAME, echoCols, null, null, null, null, null);	

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
	
	
}
