package com.GC01.BeatYourPace.Database;

import com.example.beatyourpace.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.view.Menu;
import android.content.SharedPreferences;


public class DatabaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHelper dh = new DatabaseHelper(this, DataModel.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
		
		//add method calls to create the database
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.database, menu);
		return true;
	}

	
	
	/*
	 * This method is used to add the value for the initial pace based on bpm
	 * The method needs additional work to allow for values in a range rather than specific values
	 */
	public double calcPaceForBpm(int bpm){

		SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
		int unitType = sPref.getInt("unitType", 1);
		double pace;

		switch(bpm) {
		case 150:
			if (unitType == 2) {
				pace = 16.0;
			} else {
				pace = 10.0;
			}
			break;
		case 153:
			if (unitType == 2) {
				pace = 14.0;
			} else {
				pace = 9.0;
			}
			break;
		case 156:
			if (unitType == 2) {
				pace = 12.0;
			} else {
				pace = 8.0;
			}
			break;
		case 160:
			if (unitType == 2) {
				pace = 10.0;
			} else {
				pace = 7.0;
			}
			break;
		case 163:
			if (unitType == 2) {
				pace = 9.0;
			} else {
				pace = 6.0;
			}
			break;
		case 166:
			if (unitType == 2) {
				pace = 8.0;
			} else {
				pace = 5.0;
			}
			break;
		case 171:
			if (unitType == 2) {
				pace = 6.0;
			} else {
				pace = 4.0;
			}
			break;
		default:
			if (unitType == 2) {
				pace = 10.0;
			} else {
				pace = 7.0;
			}
			break;
		}
		return pace;
	}


}
