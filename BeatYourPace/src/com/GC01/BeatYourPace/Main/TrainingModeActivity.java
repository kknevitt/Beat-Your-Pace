package com.GC01.BeatYourPace.Main;
	
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.GC01.BeatYourPace.MusicPlayer.AudioFocusManager;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
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

public class TrainingModeActivity extends Activity implements OnClickListener {


	// Target Pace 
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText;
    
    // Current Pace
    public static String displayGPSinfo;
    private static TextView currentPaceText;
	
	// Active Screen
	public static boolean onScreen;
	
	// Current Track Info
	public static String displayTrackInfo;
	private static TextView trackInfo;
	
	// Buttons    
    ImageButton playOrPauseImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
   
    Button bTargetPaceTitle, bCurrentPaceTitle, bCurrentPaceValue, bCurrentPacePreference, bTargetPacePreference, bTargetPaceValue;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainingmode); 
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		//Keep the screen on so the user can access the buttons used to associate new BPM to tracks
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//Google Analytics tracking code 
		EasyTracker.getInstance(this).activityStart(this);
		
		onScreen = true;

		AudioFocusManager.getInstance();
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
		startCurrentPaceService(this);
		
        playOrPauseImageButton = (ImageButton) findViewById(R.id.bPlayAndPause); 				
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
        stopImageButton = (ImageButton) findViewById(R.id.bStopSong);
        bTargetPaceTitle = (Button) findViewById(R.id.bTargetPaceTitle);
        bCurrentPaceTitle = (Button) findViewById(R.id.bCurrentPaceTitle);
        bCurrentPaceValue = (Button) findViewById(R.id.bCurrentPaceValue);
        bCurrentPacePreference = (Button) findViewById(R.id.bCurrentPacePreference);
        bTargetPacePreference = (Button) findViewById(R.id.bTargetPacePreference);
        bTargetPaceValue =(Button) findViewById(R.id.bTargetPaceValue);
        targetPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        
        displayTargetPace = sp.getString("set_target_pace", "6.0"); //comment these 3 lines out to run with runingmodetest
        targetPace = Float.valueOf(displayTargetPace);
        targetPaceText.setText(displayTargetPace);
    

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
        
        // GPS Broadcast Receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(GPSReceiver,
      	      new IntentFilter("GPS Current Pace Info"));
    
        
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
	
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    // Get extra data included in the Intent
			  System.out.println("Track Received");
			  trackInfo = (TextView) findViewById(R.id.tSongName);
			  
		      displayTrackInfo = intent.getStringExtra("Track Info");
		      if (displayTrackInfo == null)
		      System.out.println("displayTrackInfo was null");
		      else {
		    	  System.out.println(displayTrackInfo + " wasnt null");
		      }
		      trackInfo.setText(displayTrackInfo);
			  
		  }
		};
	
	
	/** Creating a broadcast receiver for the GPS data**/
	private BroadcastReceiver GPSReceiver = new BroadcastReceiver() {
			  @Override
			  public void onReceive(Context context, Intent intent) {
			    // Get extra data included in the Intent
				  displayGPSinfo = intent.getStringExtra("GPS Current Pace Info");
			      if (displayGPSinfo == null)
			      System.out.println("displayGPSinfo was null");
			      else {
			    	  System.out.println(displayGPSinfo + " wasnt null");
			      }
			      
			      System.out.println("GPS Info Received");
				  currentPaceText = (TextView) findViewById(R.id.CurentPace);
			      currentPaceText.setText(displayGPSinfo);
				  
			  }
			};
	
	}

