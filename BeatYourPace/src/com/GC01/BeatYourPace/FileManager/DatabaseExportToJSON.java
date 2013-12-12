package com.GC01.BeatYourPace.FileManager;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Create the content for an export of the database of JSON data to txt file 
 * 
 * 	<dt> Description:
 * 	<dd> Create the content for an export of the database to JSON data to txt file 
 * </dl>
 * 
 * @version $Date: 2013/12/10
 * @author sarahnicholson
 *
 */


import org.json.JSONArray;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.Database.DatabaseAdapter;

public class DatabaseExportToJSON {
	
	private final String LOG_TAG = "Export database to JSON";
	private static String fileName = "byp.txt";
	
	public JSONArray getJSON() {
	 
		DatabaseAdapter db = new DatabaseAdapter(ContextProvider.getContext());
		
		Cursor cursor = db.getAllTracks();
	 
		JSONArray allTracksJson = new JSONArray();
	 
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
	 
	       	    int totalColumn = cursor.getColumnCount();
	       	    JSONObject rowObject = new JSONObject();
	 
	       	    for (int i=0 ;  i< totalColumn ; i++) {
	       	    	if(cursor.getColumnName(i) != null) {
	       	    		try {
		       	    		if (cursor.getString(i) != null) {
		           	    		Log.d(LOG_TAG, cursor.getString(i));
		           	    		rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
		           	    	} else {
		           	    		rowObject.put( cursor.getColumnName(i) ,  "" ); 
		           	    	}
	       	    		}
	       	    		catch (Exception e) {
	       	    			Log.d(LOG_TAG, e.getMessage());
	       	    		}
	       	    	}
	       	    }
	       	    allTracksJson.put(rowObject);
	       	    cursor.moveToNext();
	        }
			cursor.close(); 
			Log.d(LOG_TAG, allTracksJson.toString() );
			return allTracksJson; 
	}
	
}
