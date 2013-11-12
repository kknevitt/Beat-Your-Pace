package com.GC01.BeatYourPace.Database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.content.ContentProvider;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//Database version
	private static final int DATABASE_VERSION = 1;
	
    //double targetPace to be read from the settings set_target_pace, placeholder of 6.0 for now
    double targetPace = 6.0;
    
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DataModel.DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 
		// SQL statement to create TrackData table
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + DataModel.TABLE_NAME + "(" + DataModel.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DataModel.MEDIASTOREID + " INTEGER,"+ DataModel.TITLE + " TEXT," + DataModel.ARTIST + " TEXT,"  + DataModel.BPM + " INTEGER," + DataModel.PACE + " DOUBLE)"; 
        
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
		//get reference to writable DB
		SQLiteDatabase db = getWritableDatabase();

		// create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(DataModel.MEDIASTOREID, msId);
		values.put(DataModel.TITLE, title);
		values.put(DataModel.ARTIST, artist);
		
		// Insert values into db
        //db.insert(DataModel.TABLE_NAME, DataModel.MEDIASTOREID, msId, DataModel.TITLE, title, DataModel.ARTIST, artist);
        db.insert(DataModel.DATABASE_NAME, null, values);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void addTracks(){
		//open the database in write mode
		SQLiteDatabase db2 = this.getWritableDatabase();
		
		// create ContentValues to add the content from the media store table to the equivalent column in our database
		ContentValues values2 = new ContentValues();
		values2.put(DataModel.MEDIASTOREID, MediaStore.Audio.Media._ID);
		values2.put(DataModel.TITLE, MediaStore.Audio.Media.TITLE);
		values2.put(DataModel.ARTIST, MediaStore.Audio.Artists.ARTIST);
		
		//columns needed from the content provider media store
		String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,MediaStore.Audio.Artists.ARTIST};
		
		//Query the media store database using CursorLoader
		//the first null should refer to the activity, find out how to do this
		CursorLoader cLoad = new CursorLoader(null, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
		Cursor cursor = cLoad.loadInBackground();
		
		//iterate through the contents and then write them to our database
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	db2.insert(DataModel.DATABASE_NAME, null, values2);
	    	cursor.moveToNext();
	    }
		db2.close();
		cursor.close();
}
	
	/*
	 * Update database method yet to be implemented, needs to add the BPM and the Pace
	 */
	public void updateTable(){
		
	}
	
	/*
	 * delete records method yet to be implemented
	 */
	public void deleteTrack() {
		
	}
	
	/*
	 * Get all tracks for a given pace
	 * This needs to be modified to allow for the contentURI of the tracks to be returned as an ArrayList
	 * It needs to return ArrayList called appropriateSongs and in this just list the filepath to each track
	 */
	public List<DataModel> getAppropriateSongs(int targetPace) {

		List<DataModel> appropriateSongs = new LinkedList<DataModel>();
	 
	       //Build the query
	       String query = "SELECT  * FROM " + DataModel.TABLE_NAME + "WHERE PACE = " +targetPace;
	 
	       //Get reference to writable DB
	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       //Go over each row, build track and add it to list
	       DataModel track = null;
	       if (cursor.moveToFirst()) {
	           do {
	               //DataModel track = new DataModel();
//	               track.setID(Integer.parseInt(cursor.getString(0)));
	 
	               // Add track to the playlist for the pace
	               appropriateSongs.add(track);
	           } while (cursor.moveToNext());
	       }
	 
	       Log.d("getAppropriateSongs()", appropriateSongs.toString());
	 
	       // return playlist
	       return appropriateSongs;
	       //cursor.close();
	   }
}