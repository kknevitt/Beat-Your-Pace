package com.GC01.BeatYourPace.Database;

/**
 * Extracts contents of the database in JSON format
 * 
 * @author sarahnicholson
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DatabaseJSON extends DatabaseAdapter2 {

	final static String LOG_TAG = "DbToJSON";
	
	public DatabaseJSON(Context ctx) {
		super(ctx);
	}

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
		           	    		rowObject.put( cursor.getColumnName(i) ,  "" ); 
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
