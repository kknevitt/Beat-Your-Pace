package com.example.beatyourpace;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	//SN sets up the preferences
	SharedPreferences sPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//SN added settings option to the top action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // code to launch the Settings Activity when settings is selected from the menu
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	startActivity(new Intent("com.example.beatyourpace.settingsactivity"));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
