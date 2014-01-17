package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;

import com.GC01.BeatYourPace.Database.DatabaseIntentService;
import com.GC01.BeatYourPace.Database.DatabaseExportFile;
import com.GC01.BeatYourPace.FileManager.FileExport;
import com.GC01.BeatYourPace.HelpPage.HelpPageActivity;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.Settings.SettingsActivity;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	final private static String LOG_TAG = "Main";
	
	//creating button objects
    Button trainingModeButton, letsRunModeButton, settingsButton, helpPageButton;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_main);
		
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
        
        //Start loading music data to the database as a background service ready for the player
        //Intent intentDb = new Intent(this,DatabaseIntentService.class); 
        //this.startService(intentDb);
        
        //Code to test the JSON export functionality is working
       String jsonfname = "BYPtoJSON.txt";
        FileExport fileExp = new FileExport(jsonfname);
        try {
			fileExp.exportJsonToTxt();
		} catch (JSONException e) {
			Log.d(LOG_TAG, "Unable to export JSON data");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(LOG_TAG, "Unable to create JSON file");
			e.printStackTrace();
		}
        
        //copy db to accessible location for testing
        DatabaseExportFile.expDb();
        
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
	     if(v == trainingModeButton){ //onclick the user is taken to the TrainingMode view as per TrainingModeActivity class
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
              
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
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case R.id.action_settings:
		        	startActivity(new Intent(this,SettingsActivity.class));		        	
		            return true;
		        case R.id.helpPageTitle:
		        	 startActivity(new Intent(this,HelpPageActivity.class));
			    	 return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

}