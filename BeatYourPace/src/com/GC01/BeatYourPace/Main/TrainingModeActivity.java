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
import android.widget.Toast;

public class TrainingModeActivity extends Activity implements OnClickListener {

	public static float targetPace;
	private static String displayTargetPace; 
    public static String displayGPSinfo;
	public static boolean onScreen;	
	public static String displayTrackInfo;
	protected static ImageButton playOrPauseImageButton, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    protected static Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    protected static Button bTargetPaceTitle, bCurrentPaceTitle, bCurrentPaceValue, bCurrentPacePreference, bTargetPacePreference, bTargetPaceValue;
    protected static TextView targetPaceText, currentPaceText, trackInfo, targetUnit, currentPaceUnit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainingmode);  
		
        playOrPauseImageButton = (ImageButton) findViewById(R.id.bPlayAndPause); 				
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
        stopImageButton = (ImageButton) findViewById(R.id.bStopSong);
        targetPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        targetUnit = (TextView) findViewById(R.id.targetPaceUnit);
        currentPaceUnit = (TextView) findViewById(R.id.CurrentPaceUnit);
        
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		 
		EasyTracker.getInstance(this).activityStart(this);
		
		onScreen = true;
		
		TrackList trackList = TrackList.getInstance();

		AudioFocusManager.getInstance();
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
		startCurrentPaceService(this);
		
        if (Integer.parseInt(sp.getString("unitType", "1")) == 1) {
        	String minPerMile = "min/Miles";
        	targetUnit.setText(minPerMile);
        	currentPaceUnit.setText(minPerMile);
        }
        
        displayTargetPace = sp.getString("set_target_pace", "6.0");
        targetPace = Float.valueOf(displayTargetPace);
        targetPaceText.setText(displayTargetPace);
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
        
        LocalBroadcastManager.getInstance(this).registerReceiver(GPSReceiver,
      	      new IntentFilter("GPS Current Pace Info"));    
        
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
	

	public void startNewService(View view) {
		startService(new Intent(this, MusicPlayer.class));
	
	}
		
	
	/** Broadcast receiver for the TrackInfo data**/
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
	

	public void startCurrentPaceService(Context context) {
		startService(new Intent(this, CurrentPace.class));		
	}
		

	/** Broadcast receiver for the GPS data**/
	private BroadcastReceiver GPSReceiver = new BroadcastReceiver() {
			  @Override
			  public void onReceive(Context context, Intent intent) {
				  displayGPSinfo = intent.getStringExtra("GPS Current Pace Info");
			      if (displayGPSinfo == null)
			      System.out.println("displayGPSinfo was null");
			      else {
			    	  System.out.println(displayGPSinfo + " wasnt null");
			      }

			      Toast.makeText(getApplicationContext(), " GPSInfo is on training mode.",
						   Toast.LENGTH_LONG).show();
				
				  currentPaceText = (TextView) findViewById(R.id.currentPaceText);
			      currentPaceText.setText(displayGPSinfo);
				  
			  }
			};
	
			
    public void onPause(){
 	  super.onPause();
	  onScreen = false;	
			}

			
	public void onDestroy(){
	  onScreen = false;
	 super.onDestroy();
			//	MusicPlayer.getInstance().stopPlayback();			
			}

			
	}

