package com.GC01.BeatYourPace.Main;
	
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.GC01.BeatYourPace.HelpPage.AboutPageActivity;
import com.GC01.BeatYourPace.HelpPage.HelpPageActivity;
import com.GC01.BeatYourPace.MusicPlayer.AudioFocusManager;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.Settings.SettingsActivity;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



/** 
 * @author Laura Barbosa,  Kristian Knevitt & Sarah Nicholson
 * @version 1.0, Updated 22/01/2014
 */

/**
 * Handles the functionalities of the Main page. 
 */



public class LetsRunModeActivity extends Activity implements OnClickListener {

	
	public static boolean onScreen;	
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText; 	
	public static String displayTrackInfo;
	private static TextView trackInfo;
    ImageButton playOrPauseImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
     
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lets_run_mode);  
		
		EasyTracker.getInstance(this).activityStart(this);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		onScreen = true;
		
		TrackList trackList = TrackList.getInstance();

		AudioFocusManager.getInstance();
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
        displayTargetPace = sp.getString("set_target_pace", "6.0"); //comment these 3 lines out to run with runingmodetest
        targetPace = Float.valueOf(displayTargetPace);
		
        playOrPauseImageButton = (ImageButton) findViewById(R.id.bPlayAndPause); 				
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
        stopImageButton = (ImageButton) findViewById(R.id.bStopSong);

        playOrPauseImageButton.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        songTooSlowButton.setOnClickListener(this);
        songTooFastButton.setOnClickListener(this);
        decreaseTargetPaceButton.setOnClickListener(this);
        increaseTargetPaceButton.setOnClickListener(this);
        stopImageButton.setOnClickListener(this);
        
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver,
        	      new IntentFilter("Track Info Event"));
        
   }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	

	@Override
	public void onClick(View v) {

		if (AudioFocusManager.getInstance().focusTest()){
			ButtonController.buttonFunction(v);	
		}
	}
		
	public void onPause(){
		super.onPause();
		onScreen = false;	
	}
	
	
	public void onDestroy(){
		super.onDestroy();
		onScreen = false;
		
	}
	
	
	
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    // Get extra data included in the Intent
			
			  trackInfo = (TextView) findViewById(R.id.tSongName);
			  System.out.println("Intent Received");
		      displayTrackInfo = intent.getStringExtra("Track Info Action");
		      if (displayTrackInfo == null)
		      System.out.println("Track received but null");
		      else {
		    	  System.out.println(displayTrackInfo + " wasnt null");
		      }
		      trackInfo.setText(displayTrackInfo);
			  
		  }
		};
	
	}

