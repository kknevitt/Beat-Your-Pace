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
import com.GC01.BeatYourPace.MusicPlayer.HeadsetStatusReceiver;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.Settings.SettingsActivity;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
public class TrainingModeActivity extends Activity implements OnClickListener {
=======
public class TrainingModeActivity extends Activity implements OnClickListener {
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf

	private HeadsetStatusReceiver headsetReceiver;
	private AudioFocusManager aFM;
	private SharedPreferences sp;
	
<<<<<<< HEAD
=======
	
	// Target Pace 
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf
	public static float targetPace;
	private static String displayTargetPace; 
    public static TextView targetPaceText;
    public static String displayGPSinfo;
    private static TextView currentPaceText;
	public static boolean onScreen;
<<<<<<< HEAD
=======

	// Current Track Info
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf
	public static String displayTrackInfo;
	private static TextView trackInfo, targetUnit, currentPaceUnit;
	
		
<<<<<<< HEAD
	// Buttons    
=======
	// Buttons    
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf
    ImageButton playOrPauseImageButton, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    Button bTargetPaceTitle, bCurrentPaceTitle, bCurrentPaceValue, bCurrentPacePreference, bTargetPacePreference, bTargetPaceValue;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_mode);  
		
		sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		//Keep the screen on so the user can access the buttons used to associate new BPM to tracks
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//Google Analytics tracking code 
		EasyTracker.getInstance(this).activityStart(this);
		
		onScreen = true;
		
		aFM = AudioFocusManager.getInstance();
		
		if (aFM.focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		aFM.requestFocus();
		
		startCurrentPaceService(this);
		
		Log.d("gps message from oncreate", "help");
		}
		
		
		
		
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
        trackInfo = (TextView) findViewById(R.id.tSongName);
        currentPaceText = (TextView) findViewById(R.id.currentPaceText);
        
        if (Integer.parseInt(sp.getString("unitType", "1")) == 1) {
        	String minPerMile = "min/Miles";
        	targetUnit.setText(minPerMile);
        	currentPaceUnit.setText(minPerMile);
        }
        
        
        if(displayTargetPace == null){
        	
        displayTargetPace = sp.getString("set_target_pace", "6.0");
        targetPace = Float.valueOf(displayTargetPace);

        }

        else {
        	
        	displayTargetPace = sp.getString("saved_target_pace", "6.0");
        	targetPace = Float.valueOf(displayTargetPace);
        	
        }
        

        

        
        
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
        LocalBroadcastManager.getInstance(this).registerReceiver(GPSReceiver, new IntentFilter("GPS Current Pace Info"));
        
        /*
        headsetReceiver = new HeadsetStatusReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(headsetReceiver, intentFilter);
        */
        
   }
	
	/** Method used to call the music player **/
	public void startNewService(View view) {
		startService(new Intent(this, MusicPlayer.class));
	
	}
	
	/** Method used to start the GPS service **/
	public void startCurrentPaceService(Context context) {
		startService(new Intent(this, CurrentPace.class));
		
		Log.d("startCurrentPaceService is being called", "help");
		
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
		
	      if(MusicPlayer.getInstance().currentlyPlaying() == true) {
	    	  playOrPauseImageButton.setBackgroundResource(R.drawable.pause);
	      }
	      else {
	    	  playOrPauseImageButton.setBackgroundResource(R.drawable.play);  
	      }
		
	}
	
	public void onPause(){
		super.onPause();
		displayTargetPace = String.valueOf(targetPace);
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat("saved_target_pace", 1);
		editor.commit();
		
		onScreen = false;	
		
	//	unregisterReceiver(headsetReceiver);
		
	}

	
	
	public void onDestroy(){
		super.onDestroy();
		displayTargetPace = String.valueOf(targetPace);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("saved_target_pace", displayTargetPace);
		editor.commit();
		
		onScreen = false;
	//	unregisterReceiver(headsetReceiver);
		
		
	}
	


	private BroadcastReceiver bReceiver = new BroadcastReceiver() {
		  @Override
<<<<<<< HEAD
		  public void onReceive(Context context, Intent intent) {
=======
		  public void onReceive(Context context, Intent intent) {
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf

			  
			    if (intent.getStringExtra("Track Info Action") != null){
			    	displayTrackInfo = intent.getStringExtra("Track Info Action");
			    	trackInfo.setText(displayTrackInfo);
			    	Log.i("Track Info Recieved", " - " + displayTrackInfo);
			      } 
			         
				  
		      if (intent.getStringExtra("GPS") != null){
		    	  displayGPSinfo = intent.getStringExtra("GPS");
		    	  currentPaceText.setText(displayGPSinfo);
		    	  Log.d("onReceive on TrainingMode is being called", "GPS is working");
		      	} 
<<<<<<< HEAD
		      
=======
		      
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf
		    // Get extra data included in the Intent
			
		      displayTrackInfo = intent.getStringExtra("Track Info Action");
		      Log.i("Track Info Recieved", " - " + displayTrackInfo);
		      trackInfo.setText(displayTrackInfo);
<<<<<<< HEAD
  
=======
  
>>>>>>> 76f16d1458c108f6cfc5109e643606a294520cbf
		  }
		};
		
	

	private BroadcastReceiver GPSReceiver = new BroadcastReceiver() {
			  @Override
			  public void onReceive(Context context, Intent intent) {
			    // Get extra data included in the Intent
				  displayGPSinfo = intent.getStringExtra("GPS Current Pace Info");
				  Toast.makeText(ContextProvider.getContext(), "GPS broadcast recognised", Toast.LENGTH_SHORT).show();
				  
			      if (displayGPSinfo == null)
			      System.out.println("displayGPSinfo was null");
			      else {
			    	  
			    	  System.out.println(displayGPSinfo + " wasnt null");
			    	  Toast.makeText(ContextProvider.getContext(), "GPS Data was receieved but null", Toast.LENGTH_SHORT).show();
					  
			      }
			      
			      System.out.println("GPS Info Received");
				  currentPaceText = (TextView) findViewById(R.id.currentPaceText);
			      currentPaceText.setText(displayGPSinfo);
			      Toast.makeText(ContextProvider.getContext(), "GPS Data was RECEIVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
				  
			  }
			};

	}

