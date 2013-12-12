package com.GC01.BeatYourPace.FileManager;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Sets the skeleton for the export file capability
 * 
 * 	<dt> Description:
 * 	<dd> Functionality to check for available media, create and write a file
 * </dl>
 * 
 * @version $Date: 2013/12/10
 * @author sarahnicholson
 *
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.GC01.BeatYourPace.Database.DatabaseJSON;
import com.GC01.BeatYourPace.Main.ContextProvider;


public class FileExport {

	String fileName;
	private final static String LOG_TAG = "FileExport";
	protected static Context context = ContextProvider.getContext();
	private File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + fileName);

	/**
	 * Constructor which takes in the file name
	 * @param fileName  String that is the name of the file to be created
	 */
	public FileExport (String fileName) {
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

	public void exportToTxt() throws JSONException, IOException {

		Context context = ContextProvider.getContext();

		if (isExternalStorageWritable() == true) {
		
		DatabaseJSON dj = new DatabaseJSON(context);
		JSONArray ja = dj.getJsonArray();
		
		JSONObject jo1 = ja.getJSONObject(0);
		System.out.println(jo1.toString());
	
		try {
			FileWriter file = new FileWriter(getFileStorageDir(context, fileName));
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				file.write(jo.toString());
				file.flush();
				file.close();
				Log.d(LOG_TAG, "File saved to " + getFileStorageDir(context, fileName));
			}
		} catch (IOException e) {
			Log.d(LOG_TAG, "IO exception");
			e.printStackTrace();
		}
		}
	}
}