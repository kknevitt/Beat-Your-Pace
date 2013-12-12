package com.GC01.BeatYourPace.Database;

////Work in progress - will be used to break up the large Database Adapter class

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.GC01.BeatYourPace.Database.DatabaseContract.DataEntry;
import com.GC01.BeatYourPace.Main.ContextProvider;

public abstract class DatabaseAbstract {

	private final Context context;
	private DatabaseHelper DbHelper;
	private SQLiteDatabase db;
	private static final String LOG_TAG = "Database abstract";

	public DatabaseAbstract (Context ctx){
		this.context = ctx;
		DbHelper = new DatabaseHelper(ContextProvider.getContext(), DataEntry.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
	}

	/**
	 * Method to open the database in writable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAbstract openDbWrite() throws SQLException {
		db = DbHelper.getWritableDatabase();
		return this;
	}

	/**
	 *  Method to open the database in readable format
	 * @return  db  Returns the database in writable format
	 */
	public DatabaseAbstract openDbRead() throws SQLException {
		db = DbHelper.getReadableDatabase();
		return this;
	}

	/**
	 * Method to close the database
	 */
	public void closeDb(){
		DbHelper.close();
	}

}
