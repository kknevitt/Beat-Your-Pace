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
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
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
import android.widget.Toast;

/** 
 * @author Laura Barbosa,  Kristian Knevitt & Sarah Nicholson
 * @version 1.0, Updated 22/01/2014
 */

/**
 * Handles the functionalities of the Lets Run Mode page. 
 */

public class LetsRunModeActivity extends Activity implements OnClickListener {

	
	public static boolean onScreen;	
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText; 	
	public static String displayTrackInfo;
	private static TextView trackInfo;
	private SharedPreferences sp;
	private static AudioFocusManager aFM;
	
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

		aFM = AudioFocusManager.getInstance();
			
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
        
        if(displayTargetPace == null){
        	
            displayTargetPace = sp.getString("set_target_pace", "6.0");
            targetPace = Float.valueOf(displayTargetPace);

            }

            else {
            	
            	displayTargetPace = sp.getString("saved_target_pace", "6.0");
            	targetPace = Float.valueOf(displayTargetPace);
            	
            }

		
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
        
        // Displaying a short toast due to TP not always being shown on screen.
        Toast.makeText(getBaseContext(), "Your current Target Pace is: " + displayTargetPace, Toast.LENGTH_SHORT).show();
        
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
		
	public void onResume(){
		sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		
		onScreen = true; 
		
		aFM = AudioFocusManager.getInstance();
		
	if (aFM.focusTest() != true) {
			System.out.print("Didn't have focus, requesting it");
			aFM.requestFocus();
			}
			

	LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver,
	        	      new IntentFilter("Track Info Event"));

	        super.onResume();
		}

		
	
	public void onPause(){
		
		onScreen = false;
		super.onPause();
	}
	
	
	public void onDestroy(){
		
		onScreen = false;
		super.onDestroy();
	}
	
	
	
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
			
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

