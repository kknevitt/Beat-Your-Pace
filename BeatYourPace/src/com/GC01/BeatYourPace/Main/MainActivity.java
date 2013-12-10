package com.GC01.BeatYourPace.Main;

import android.app.Activity;

import com.GC01.BeatYourPace.Database.DatabaseIntentService;
import com.GC01.BeatYourPace.HelpPage.HelpPageActivity;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.Settings.SettingsActivity;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	//creating button objects
    Button trainingModeButton, letsRunModeButton, settingsButton, helpPageButton;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Analytics tracking user on this page
		EasyTracker.getInstance(this).activityStart(this);
		
		//creating image buttons objects and getting their setup from xml
        trainingModeButton = (Button) findViewById(R.id.bTrainingMode); 
        letsRunModeButton = (Button) findViewById(R.id.bLetsRun); 
        settingsButton = (Button) findViewById(R.id.bSettings); 
        helpPageButton = (Button) findViewById(R.id.bHelpPage);
        
        //setting an event listener for each button
        trainingModeButton.setOnClickListener(this);
        letsRunModeButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        helpPageButton.setOnClickListener(this);
        
        //Start loading music data to the database
        //Implemented as a background service
        Intent intentDb = new Intent(this,DatabaseIntentService.class); 
        this.startService(intentDb);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		EasyTracker.getInstance(this).activityStart(this);
		return true;
	}
	@Override
	public void onClick(View v) {
	            //  auto-generated code: Auto-generated method stub
	      if(v == trainingModeButton){ //onclick the user is taken to the TrainingMode view as per TrainingModeActivity class
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
              EasyTracker.getInstance(this).activityStop(this);
              
              //start the CurrentPace service class when training mode is selected.
              Intent CurrentPaceService = new Intent(this, CurrentPace.class);
              startService(CurrentPaceService);
              
				}
	      if (v == letsRunModeButton) {
	    	  Intent intent = new Intent(this,LetsRunModeActivity.class);
	    	  startActivity(intent);
	      }
	      if (v == settingsButton) { // // onclick the user is taken to the Settings view as per Settings class
	    	  Intent intent = new Intent(this,SettingsActivity.class);
	    	  startActivity(intent);
	      }
	      
	      if (v == helpPageButton) { // // onclick the user is taken to the HelpPage view as per Settings class
	    	  Intent intent = new Intent(this,HelpPageActivity.class);
	    	  startActivity(intent);
	      }
	}       
	
	//Add settings option to the top action bar
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // code to launch the Settings Activity when settings is selected from the menu
		    switch (item.getItemId()) {
		        case R.id.action_settings:
		        	startActivity(new Intent(this,SettingsActivity.class));
		            return true;
		        case R.id.helpPageTitle:
		        	startActivity(new Intent(this,SettingsActivity.class));
		            return true;
		    	
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

}
