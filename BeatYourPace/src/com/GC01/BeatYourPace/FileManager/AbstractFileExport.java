package com.GC01.BeatYourPace.FileManager;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.GC01.BeatYourPace.Database.DatabaseAdapter;
import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.FileManager.FileOutput;

public abstract class AbstractFileExport {
	
	String fileName;
	private final static String LOG_TAG = "File Export";
	protected static Context context = ContextProvider.getContext();
	private File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + fileName);
	// private File folder = new File(Environment.getExternalStorageDirectory() + "/BYP");

	/**
	 * Constructor which takes in the file name
	 * @param fileName  String that is the name of the file to be created
	 */
	public AbstractFileExport (String fileName) {
		this.fileName = fileName;
		}

	
	/**
	 * Checks if external storage is available for read and write
	 * @return  True or false
	 */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/**
	 * Checks if external storage is available to at least read
	 * @return
	 */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/**
	 * Returns the file that is to be created in the appropriate folder
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static File getFileStorageDir(Context context, String fileName) {
	    // Get the directory for the user's public downloads directory in which to store the CSV file 
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
	    if (!file.mkdirs()) {
	        Log.e(LOG_TAG, "Directory not created");
	    }
	    return file;
	}
	
	public void fileOutput(String fileName) {
		this.fileName = fileName;
		
		FileOutput fo = new FileOutput(fileName);
		
		
		
	}
	
}