package com.GC01.BeatYourPace.Database;

import java.io.File;
import java.io.FileOutputStream;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.GC01.BeatYourPace.Database.DatabaseAdapter;

public class DatabaseExportToCsv {

	private final String LOG_TAG = "Database export to CSV file";
	private DatabaseAdapter db;
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

	public File getFileStorageDir(Context context, String fileName) {
	    // Get the directory for the user's public downloads directory in which to store the CSV file 
	    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
	    if (!file.mkdirs()) {
	        Log.e(LOG_TAG, "Directory not created");
	    }
	    return file;
	}
	
	/*
	public void writeFile() {
		String filename = "bypdata";
		String string = "Hello world!";
		FileOutputStream outputStream;
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