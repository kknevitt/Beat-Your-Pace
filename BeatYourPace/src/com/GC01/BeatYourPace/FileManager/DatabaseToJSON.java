package com.GC01.BeatYourPace.FileManager;

/**
 * Extracts contents of the database in JSON format
 * 
 * @author sarah nicholson 
 * @version 2013/12/12
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.GC01.BeatYourPace.Database.DatabaseAdapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DatabaseToJSON extends DatabaseAdapter {

	private final static String LOG_TAG = "DbToJSON";
	
	public DatabaseToJSON(Context ctx) {
		super(ctx);
	}

	/**
	 * Method to create an array to hold the data from the database in JSON format
	 * @return  allTracksJson  Returns a JSONArray with all the data for each track from the database
	 * @throws JSONException
	 */
	public JSONArray getJsonArray() throws JSONException {
		Cursor cursor = getAllTracks();
		 
		JSONArray allTracksJson = new JSONArray();
	 
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
	 
	       	    int totalColumn = cursor.getColumnCount();
	       	    JSONObject rowObject = new JSONObject();
	 
	       	    for (int i=0;  i< totalColumn; i++) {
	       	    	if(cursor.getColumnName(i) != null) {
	       	    		try {
		       	    		if (cursor.getString(i) != null) {
		           	    		rowObject.put(cursor.getColumnName(i), cursor.getString(i) );
		           	    	} else {
		           	    		rowObject.put(cursor.getColumnName(i), ""); 
		           	    	}
	       	    		}
	       	    		catch (Exception e) {
	       	    			Log.d(LOG_TAG, "Couldn't write JSON object");
	       	    		}
	       	    	}
	       	    }
	       	    allTracksJson.put(rowObject);
	       	    cursor.moveToNext();
	        }
			cursor.close(); 
			closeDb();
			Log.d(LOG_TAG, "JSON data extracted");
			return allTracksJson; 
	}
}