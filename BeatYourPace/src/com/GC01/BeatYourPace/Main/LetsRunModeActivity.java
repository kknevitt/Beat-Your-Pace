package com.GC01.BeatYourPace.Main;
	
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.GC01.BeatYourPace.MusicPlayer.AudioFocusManager;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LetsRunModeActivity extends Activity implements OnClickListener {

<<<<<<< HEAD
	
	public static boolean onScreen;	
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText;
=======
	
	// Target Pace 
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText;
	
	// Active Screen
	public static boolean onScreen;
	
	// Current Track Info
>>>>>>> ebca37bf67e85e818c29ff7e2044028b97dabacc
	public static String displayTrackInfo;
	private static TextView trackInfo;
	
	// Buttons    
    ImageButton playOrPauseImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
   
    Button bTargetPaceTitle, bCurrentPaceTitle, bCurrentPaceValue, bCurrentPacePreference, bTargetPacePreference, bTargetPaceValue;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lets_run_mode);  
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		//Keep the screen on so the user can access the buttons used to associate new BPM to tracks
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//Google Analytics tracking code 
		EasyTracker.getInstance(this).activityStart(this);
		
		onScreen = true;
		
		TrackList trackList = TrackList.getInstance();

		AudioFocusManager.getInstance();
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
<<<<<<< HEAD
	    		
=======
		startCurrentPaceService(this);
		
>>>>>>> ebca37bf67e85e818c29ff7e2044028b97dabacc
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
    

        //setting an event listener for each button
        playOrPauseImageButton.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        songTooSlowButton.setOnClickListener(this);
        songTooFastButton.setOnClickListener(this);
        decreaseTargetPaceButton.setOnClickListener(this);
        increaseTargetPaceButton.setOnClickListener(this);
        stopImageButton.setOnClickListener(this);
        
        // Track Broadcast Receiver 
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver,
        	      new IntentFilter("Track Info Event"));
        
   }


	/** Method used to call the music player **/
	public void startNewService(View view) {
		startService(new Intent(this, MusicPlayer.class));
	
	}
	
	/** Method used to start the GPS service **/
	public void startCurrentPaceService(Context context) {
		startService(new Intent(this, CurrentPace.class));		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	//	MusicPlayer.getInstance().stopPlayback();
		
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

