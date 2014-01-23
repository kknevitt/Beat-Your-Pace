package com.GC01.BeatYourPace.Database;

import com.echonest.api.v4.EchoNestException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Class to allow the database to be launched and run as a background service.
 * 
 * @version 2013/11/14
 * @author sarah nicholson
 *
 */

public class DatabaseIntentService extends IntentService {

	private static final String LOG_TAG = "DatabaseIntentService";

	private DatabaseAddInitialData db;
	
	public DatabaseIntentService() {
		super("DatabaseIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d(LOG_TAG,"DatabaseIntentService : onCreate");
		this.db = new DatabaseAddInitialData(getApplicationContext());
		
		this.db.synchTracks();
		
		try {
			this.db.addBpm();
		} catch (EchoNestException e) {
			e.printStackTrace();
		}
		
		this.db.addInitialPrefPace();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG,"DatabaseIntentService : onStartCommand");
		Toast.makeText(this, "Analysing your music", Toast.LENGTH_SHORT).show();
	    return super.onStartCommand(intent,flags,startId);
	}

}
