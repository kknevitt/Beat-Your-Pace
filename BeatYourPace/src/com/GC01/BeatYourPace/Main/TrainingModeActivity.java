package com.GC01.BeatYourPace.Main;
	
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.AudioFocusManager;
import com.GC01.BeatYourPace.MusicPlayer.MusicController;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingModeActivity extends Activity implements OnClickListener {


	public static double targetPace = 10.0; //setting temporarily to 10
	public static boolean onScreen;
	    
    ImageButton playOrPauseImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, pauseImageButton, stopImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    
    Context context;
  //  AudioFocusManager aFM;
    
    static TextView targetPaceText;
    static TextView currentPaceText;
    
    String pace ="";
    boolean paused;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainingmode); 
	    
		/**Keep the screen on so the user can access the buttons used to associate new BPM to tracks**/
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		/**Google Analytics tracking code **/
		EasyTracker.getInstance(this).activityStart(this);
		
		onScreen = true;

		AudioFocusManager.getInstance();
		
		if (AudioFocusManager.getInstance().focusTest() != true) {
		System.out.print("Didn't have focus, requesting it");
		AudioFocusManager.getInstance().requestFocus();
		}
		
		startCurrentPaceService(this);

		
		//creates image buttons objects and gets their setup from xml
        playOrPauseImageButton = (ImageButton) findViewById(R.id.bPlayAndPause); 				
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
        stopImageButton = (ImageButton) findViewById(R.id.bStopSong);
        
        targetPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        
        String tarPace = String.valueOf(TargetPace.getTargetPace());
        targetPaceText.setText(tarPace);
    
        
        //setting an event listener for each button
        playOrPauseImageButton.setOnClickListener(this);
        //imagebutton2.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        songTooSlowButton.setOnClickListener(this);
        songTooFastButton.setOnClickListener(this);
        decreaseTargetPaceButton.setOnClickListener(this);
        increaseTargetPaceButton.setOnClickListener(this);
       // pauseImageButton.setOnClickListener(this);
        stopImageButton.setOnClickListener(this);
   }


    
	public void startNewService(View view) {
		
		startService(new Intent(this, MusicPlayer.class));
	
	}
	
	public void startCurrentPaceService(Context context) {
	
		startService(new Intent(this, CurrentPace.class));		
		currentPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
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
	
	}

