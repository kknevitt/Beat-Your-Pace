package com.GC01.BeatYourPace.Main;
	
import java.io.IOException;

import android.app.Activity;

import com.GC01.BeatYourPace.Database.DatabaseActivity;
import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.example.beatyourpace.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TrainingModeActivity extends Activity implements OnClickListener {

	public static double targetPace = 6.0; //setting temporarily to 6
    
    ImageButton playSongImageButton, imagebutton2, skipSongImageButton, previousSongImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    static TextView targetPaceText;
    static TextView currentPaceText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 
		
	
	//targetPace = DatabaseActivity.getTargetPace();	
		
		//creates image buttons objects and gets their setup from xml
        playSongImageButton = (ImageButton) findViewById(R.id.bPlaySong); 			
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); 	
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 		
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 	
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow); 				
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);					
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);					
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);					
        
        targetPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        currentPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        
        // Takes the variable Target Pace and pushes it to the text view.
        String tarPace = String.valueOf(ButtonController.getTargetPace());
        targetPaceText.setText(tarPace);
       
        
        //setting an event listener for each button
        playSongImageButton.setOnClickListener(this);
        //imagebutton2.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        songTooSlowButton.setOnClickListener(this);
        songTooFastButton.setOnClickListener(this);
        decreaseTargetPaceButton.setOnClickListener(this);
        increaseTargetPaceButton.setOnClickListener(this);
   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {

		ButtonController.buttonFunction(v);
		
	}
	
			
}
	
	
