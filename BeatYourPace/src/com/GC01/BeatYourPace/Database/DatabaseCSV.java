package com.GC01.BeatYourPace.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.database.Cursor;

public class DatabaseCSV extends DatabaseAdapter  {
	
	final static String LOG_TAG = "DbToCSV";

	public DatabaseCSV(Context ctx) {
		super(ctx);
	}

	public File databaseToCSV(String fname) throws IOException {
		String csvValues = "";
		openDbRead();
		File outFile = new File(fname);
		FileWriter fileWriter = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fileWriter);
		try {
			Cursor cursor = getAllTracks();
			if (cursor != null) {
				while (cursor.moveToNext()) {
					csvValues = Long.toString(cursor.getLong(0))
							+ ",";
					csvValues += Long.toString(cursor.getLong(1))
							+ ",";
					csvValues += cursor.getString(2)
							+ ",";
					csvValues += cursor.getString(3) 
							+ ",";
					csvValues += cursor.getString(4)
							+ ",";
					csvValues += Integer.toString(cursor.getInt(5))
							+ ",";
					csvValues += Float.toString(cursor.getFloat(6))
							+ ",";
					csvValues += Float.toString(cursor.getFloat(7))
							+ ",";
					csvValues += Float.toString(cursor.getFloat(8))
							+ ",";
					csvValues += Float.toString(cursor.getFloat(9))
							+ ",";
					csvValues += cursor.getString(10)
							+ "\n";
					out.write(csvValues);
				}
				/*
				1DataEntry.COL_ID, 
				2DataEntry.COL_MEDIASTOREID, 
				3DataEntry.COL_ARTIST, 
				4DataEntry.COL_TITLE,
				5DataEntry.COL_BPM, 
				6DataEntry.COL_INITIAL_PREF_PACE_M,
				7DataEntry.COL_PREF_PACE_M,
				8DataEntry.COL_INITIAL_PREF_PACE_KM,
				9DataEntry.COL_PREF_PACE_KM,
				10DataEntry.COL_FILE_LOC,
				*/
				cursor.close();
			}
			out.close();
		} catch (IOException e) {
		}
		closeDb();
		return outFile;
	}
}
