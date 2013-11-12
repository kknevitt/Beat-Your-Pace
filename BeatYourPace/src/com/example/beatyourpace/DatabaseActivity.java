package com.example.beatyourpace;

import com.GC01.BeatYourPace.Database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DatabaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHelper dh = new DatabaseHelper(this, null, null, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.database, menu);
		return true;
	}

	
}
