package com.GC01.BeatYourPace.Main;
	
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.GC01.BeatYourPace.ArchiveFiles.DatabaseActivity;
import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.AudioFocusManager;
import com.GC01.BeatYourPace.MusicPlayer.MusicController;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.CurrentPace;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.GC01.BeatYourPace.Settings.AccessSettings;
import com.example.beatyourpace.R;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingModeActivity extends Activity implements OnClickListener {


	public static double targetPace = 10.0; //setting temporarily to 10

    
    ImageButton playSongImageButton, imagebutton2, skipSongImageButton, previousSongImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    
    //placeholder buttons
    Button pause;
    Button stop;
    Context context;
    AudioFocusManager aFM;
    
    static TextView targetPaceText;
    static TextView currentPaceText;
    TextView currentTrack, currentTitle;
    String pace ="";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 
		
		if (aFM == null) {
		aFM = new AudioFocusManager(this);
		}
		
		
		if (aFM.focusTest() != true) {
		
		System.out.print("Didn't have focus, requesting it");
		aFM.requestFocus();
		}
		
		startCurrentPaceService(context);

		playSongImageButton = (ImageButton) findViewById(R.id.bPlaySong); 			 	
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);	
        currentTrack = (TextView) findViewById(R.id.CurrentTargetPace);
        currentTitle = (TextView) findViewById(R.id.CurrentTargetPace);
        
        // adding placeholder buttons
        pause = (Button) findViewById(R.id.placeHolderPause);
        stop = (Button) findViewById(R.id.placeHolderStop);
        
        
        targetPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        
        String tarPace = String.valueOf(TargetPace.getTargetPace());
        targetPaceText.setText(tarPace);
    
        
        
        //temp functions to display title and track
        //currentTrack.setText(MusicPlayer.class.getTrack());
        //currentTitle.setText(MusicPlayer.class.getTitle());
        
        //setting an event listener for each button
        playSongImageButton.setOnClickListener(this);
        //imagebutton2.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        songTooSlowButton.setOnClickListener(this);
        songTooFastButton.setOnClickListener(this);
        decreaseTargetPaceButton.setOnClickListener(this);
        increaseTargetPaceButton.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
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
		if (aFM.focusTest())
		ButtonController.buttonFunction(v);
		}

}
	

