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
=======
	// Target Pace 
	public static float targetPace;
	private static String displayTargetPace; 
    protected static TextView targetPaceText;
	
	// Active Screen
	public static boolean onScreen;
	
	// Current Track Info
>>>>>>> a054825d0b7f7069b034c11fed81572b3e7cbcb1
	public static String displayTrackInfo;
	private static TextView trackInfo;	
	protected static ImageButton playOrPauseImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
	protected static Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
	protected static Button bTargetPaceTitle, bCurrentPaceTitle, bCurrentPaceValue, bCurrentPacePreference, bTargetPacePreference, bTargetPaceValue;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_lets_run_mode);  
		
	      playOrPauseImageButton = (ImageButton) findViewById(R.id.bPlayAndPause); 				
	      skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
	      previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
	      songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
	      songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
	      decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
	      increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
	      stopImageButton = (ImageButton) findViewById(R.id.bStopSong);
	  
		
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
		
<<<<<<< HEAD
	    
=======
		startCurrentPaceService(this);
		
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
>>>>>>> a054825d0b7f7069b034c11fed81572b3e7cbcb1
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


	public void startNewService(View view) {
		startService(new Intent(this, MusicPlayer.class));
	
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
		
	public void onPause(){
		super.onPause();
		onScreen = false;	
		}
		
		
	public void onDestroy(){
		super.onDestroy();
		onScreen = false;
		//	MusicPlayer.getInstance().stopPlayback();
			
		}
		
		
	}

