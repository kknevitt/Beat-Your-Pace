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
    
    ImageButton imagebutton1, imagebutton2, imagebutton4, imagebutton5;
    Button button6, button7, button8, button9;
    static TextView atext;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 
		
	
	//	targetPace = DatabaseActivity.getTargetPace();	
		
		//creates image buttons objects and gets their setup from xml
        imagebutton1 = (ImageButton) findViewById(R.id.bPlaySong); 			//play button
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); 	//pause button
        imagebutton4 = (ImageButton) findViewById(R.id.bSkipTrack); 		//skip track button
        imagebutton5 = (ImageButton) findViewById(R.id.bPreviousTrack); 	//previous track button
        button6 = (Button) findViewById(R.id.bSongTooSlow); 				//tag track as too fast
        button7 = (Button) findViewById(R.id.bSongTooFast);					//tag track as too slow
        button8 = (Button) findViewById(R.id.bDecTarget);					//increase target pace
        button9 = (Button) findViewById(R.id.bIncTarget);					//decrease target pace
        
        atext = (TextView) findViewById(R.id.CurrentTargetPace);
        
        // Takes the variable Target Pace and pushes it to the text view.
        String tarPace = String.valueOf(ButtonController.getTargetPace());
        atext.setText(tarPace);
        	
        //setting an event listener for each button
        imagebutton1.setOnClickListener(this);
        //imagebutton2.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
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
	
	
