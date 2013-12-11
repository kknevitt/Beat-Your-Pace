package com.GC01.BeatYourPace.FileManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.Database.DatabaseAdapter;

//throws IOException

public class DatabaseExportToCsv {
/*
	private final static String LOG_TAG = "Database export to CSV";
	private DatabaseAdapter db = new DatabaseAdapter(ContextProvider.getContext());
	private static String fileName = "byp.csv";
	
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
	
	
	/*
	public void writeFile() {
		
		String string = "Hello world!";
		FileOutputStream outputStream;
		BufferedOutputStream bufOutputStream = new BufferedOutputStream(outputStream);
		Cursor output = db.getAllTracks();
	
		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
}