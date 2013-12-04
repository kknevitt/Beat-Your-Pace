package com.GC01.BeatYourPace.Database;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DatabaseIntentService extends IntentService {

	private static final String LOG_TAG = "DatabaseIntentService";

	private DatabaseAdapter db;
	
	public DatabaseIntentService() {
		super("DatabaseIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG,"DatabaseIntentService : onCreate");
		this.db = new DatabaseAdapter(getApplicationContext());
		this.db.openDbWrite();
		this.db.addTracks();
		Log.d(LOG_TAG,"Tracks added");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG,"DatabaseIntentService : onStartCommand");
		Toast.makeText(this, "Database service starting", Toast.LENGTH_SHORT).show();
	    return super.onStartCommand(intent,flags,startId);
	}

}
