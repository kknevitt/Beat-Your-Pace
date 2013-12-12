package com.GC01.BeatYourPace.FileManager;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Sets the skeleton for the export file capability.
 *  <dd> It currently is used to export the database contents in JSON format to a txt file
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
	String dirName;
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
	 * @throws IOException 
	 */
	public File getFile(Context context, String fileName) throws IOException {
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
		//the file should be overwritten each time it is created
		file.createNewFile();
		return file;
	}

	/**
	 * Exports the JSON array to a text file on the phone's storage
	 * @throws JSONException
	 * @throws IOException
	 */
	public void exportJsonToTxt() throws JSONException, IOException {

		Context context = ContextProvider.getContext();

		if (isExternalStorageWritable() == true) {

			DatabaseJSON dj = new DatabaseJSON(context);
			JSONArray ja = dj.getJsonArray();

			JSONObject jo1 = ja.getJSONObject(0);
			System.out.println(jo1.toString());

			try {
				FileWriter file = new FileWriter(getFile(context, fileName));
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					file.write(jo.toString());
					file.flush();
				}
				file.close();
				Log.d(LOG_TAG, "File saved to " + getFile(context, fileName));
			} catch (IOException e) {
				Log.d(LOG_TAG, "IO exception");
				e.printStackTrace();
			}
		}
	}
}