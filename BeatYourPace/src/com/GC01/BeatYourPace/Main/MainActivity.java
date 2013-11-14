package com.GC01.BeatYourPace.Main;


import com.example.beatyourpace.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	//SN This sets up the preferences
	//It may be needed to call default settings for the first time the app is used
//	SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
	
	//creating button objects
    Button b1, b2, b3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//creating image buttons objects and getting their setup from xml
        b1 = (Button) findViewById(R.id.bTrainingMode); 
        b2 = (Button) findViewById(R.id.bLetsRun); 
        b3 = (Button) findViewById(R.id.bSettings); 
        
        //setting an event listener for each button
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
	            //  auto-generated code: Auto-generated method stub
	      if(v == b1){ //onclick the user is taken to the TrainingMode view as per TrainingModeActivity class
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
				}
	      if (v == b3) { // // onclick the user is taken to the Settings view as per Settings class
	    	  Intent intent = new Intent(this,SettingsActivity.class);
	    	  startActivity(intent);
	      }
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