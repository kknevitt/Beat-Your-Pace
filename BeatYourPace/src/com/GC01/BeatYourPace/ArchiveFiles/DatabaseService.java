package com.GC01.BeatYourPace.ArchiveFiles;

import com.GC01.BeatYourPace.Database.DatabaseAdapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DatabaseService extends Service {

	private static final String LOG_TAG = "DatabaseService";

	private DatabaseAdapter db;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(LOG_TAG,"DatabaseService : onCreate");
		this.db = new DatabaseAdapter(getApplicationContext());
		Log.d(LOG_TAG,"DB created");
		this.db.openDbWrite();
		this.db.addTracks();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG,"DatabaseService : onStartCommand");
		Toast.makeText(this, "Database service starting", Toast.LENGTH_SHORT).show();
		return Service.START_STICKY;
		//return Service.START_FLAG_REDELIVERY;
		//should this be START_STICKY?
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(LOG_TAG,"DatabaseService : onBind");
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG,"DatabaseService : onDestroy");
		db.closeDb();
	}
}
