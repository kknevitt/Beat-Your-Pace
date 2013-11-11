package com.example.beatyourpace;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateDb extends SQLiteOpenHelper {
	
	//Database version
	private static final int DATABASE_VERSION = 1;
	
	//Database name
	private static final String DATABASE_NAME = "byp";
	
	//Android media database
	
    
	//Database table name
	private static final String TABLE_NAME = "beat_pace";
	
	//Database table column names
    private static final String KEY_ID = "ID";
    private static final String KEY_BPM = "BPM";
    private static final String KEY_PACE = "PACE";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_ARTIST = "ARTIST";

    //Database column names
    private static final String[] COLUMNS = {KEY_ID,KEY_BPM,KEY_PACE,KEY_TITLE,KEY_ARTIST};
	
    //double targetPace to be read from the settings set_target_pace, placeholder of 6.0 for now
    double targetPace = 6.0;
    
	public CreateDb(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase byp) {
		// SQL statement to create beat_pace table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME;
 
        // create byp db
        byp.execSQL(CREATE_TABLE);
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	public void addTracks(DataModel track){

		//get reference to writable DB
		SQLiteDatabase byp = this.getWritableDatabase();

		// create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//values.put(KEY_ID, track.getID());
		//values.put(KEY_TITLE, track.TITLE);
		//values.put(KEY_BPM, track.BPM);
		//values.put(KEY_PACE, track.PACE);
		//values.put(KEY_ARTIST, track.ARTIST);

		//copy data from the mediadb into the byp db
		byp.execSQL(INSERT INTO TABLE_NAME (value1, value2) SELECT value1, value2 FROM TABLE1
		
		//insert the values into the table
		//db.insert(TABLE_NAME, // table
        //null, //nullColumnHack
        //values); // key/value -> keys = column names/ values = column values

		//Close the database after writing to it
		byp.close(); 
}
	/*
	 * Get all tracks for a given pace
	 */
	public List<DataModel> getTracksForPace(int targetPace) {
	       List<DataModel> playlistForPace = new LinkedList<DataModel>();
	 
	       //Build the query
	       String query = "SELECT  * FROM " + TABLE_NAME + "WHERE PACE = " +targetPace;
	 
	       //Get reference to writable DB
	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       //Go over each row, build track and add it to list
	       DataModel track = null;
	       if (cursor.moveToFirst()) {
	           do {
	               track = new DataModel();
	               track.setID(Integer.parseInt(cursor.getString(0)));
	               track.setTITLE(cursor.getString(1));
	               track.setPACE(cursor.getString(2));
	 
	               // Add track to the playlist for the pace
	               playlistForPace.add(track);
	           } while (cursor.moveToNext());
	       }
	 
	       Log.d("getTracksForPace()", playlistForPace.toString());
	 
	       // return playlist
	       return playlistForPace;
	   }
}
