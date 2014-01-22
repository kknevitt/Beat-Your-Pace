package com.GC01.BeatYourPace.Main;
import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;

import com.GC01.BeatYourPace.Database.DatabaseIntentService;
import com.GC01.BeatYourPace.FileManager.FileExport;
import com.GC01.BeatYourPace.HelpPage.AboutPageActivity;
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

/** 
 * @author Laura Barbosa,  Kristian Knevitt & Sarah Nicholson
 * @version 1.0, Updated 22/01/2014
 */

/**
 * Handles the functionalities of the Main page. 
 */


public class MainActivity extends Activity implements OnClickListener{
	
	final private static String LOG_TAG = "Main";
	
    Button trainingModeButton, letsRunModeButton, settingsButton, helpPageButton;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EasyTracker.getInstance(this).activityStart(this);
		
		trainingModeButton = (Button) findViewById(R.id.bTrainingMode); 
        letsRunModeButton = (Button) findViewById(R.id.bLetsRun); 
        settingsButton = (Button) findViewById(R.id.bSettings); 
        helpPageButton = (Button) findViewById(R.id.bHelpPage);
        trainingModeButton.setOnClickListener(this);
        letsRunModeButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        helpPageButton.setOnClickListener(this);
        
        //Start loading music data to the database as a background service ready for the player
        Intent intentDb = new Intent(this,DatabaseIntentService.class); 
        this.startService(intentDb);
        
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
	     if(v == trainingModeButton){ 
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
              
          
              Intent CurrentPace = new Intent(this, CurrentPace.class);
              startService(CurrentPace);
              
				}
	      if (v == letsRunModeButton) {
	    	  Intent intent = new Intent(this,LetsRunModeActivity.class);
	    	  startActivity(intent);
	      }
	      if (v == settingsButton) { 
	    	  Intent intent = new Intent(this,SettingsActivity.class);
	    	  startActivity(intent);
	      }
	      
	      if (v == helpPageButton) { 
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
		        case R.id.aboutPageTitle:
		        	startActivity(new Intent(this,AboutPageActivity.class));
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
}