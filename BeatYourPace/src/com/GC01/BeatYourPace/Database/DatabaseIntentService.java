package com.GC01.BeatYourPace.Database;

import java.util.ArrayList;

import com.echonest.api.v4.EchoNestException;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DatabaseIntentService extends IntentService {

	private static final String LOG_TAG = "DatabaseIntentService";

	private DatabaseAdapter db;
	
	public DatabaseIntentService() {
		super("DatabaseIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d(LOG_TAG,"DatabaseIntentService : onCreate");
		this.db = new DatabaseAdapter(getApplicationContext());
		this.db.openDbWrite();
		
		//synch the tracks from the device to the byp database
		this.db.synchTracks();
		Log.d(LOG_TAG,"Tracks updated");
		
		//get the BPM for each track in the byp database
		try {
			this.db.addBpm();
		} catch (EchoNestException e) {
			e.printStackTrace();
		}
		Log.d(LOG_TAG,"Bpm added");
		
		//add the initialPrePace for each track to the database
		this.db.addInitialPrefPace();
		Log.d(LOG_TAG,"Initial preferred pace added");
		
		this.db.closeDb();
		
		/*
		//for testing only, create the array list and synch it
		ArrayList<String> appSong = this.db.getAppropriateSongs((float)10.0);
		for (int i = 0; i < appSong.size(); i++ ) {
			System.out.println(appSong.get(i));
		}
		*/
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG,"DatabaseIntentService : onStartCommand");
		Toast.makeText(this, "Analysing your music", Toast.LENGTH_SHORT).show();
	    return super.onStartCommand(intent,flags,startId);
	}

}
