package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.GC01.BeatYourPace.ArchiveFiles.DatabaseActivity;
import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.example.beatyourpace.R;

public class LetsRunModeActivity extends Activity implements OnClickListener {
	
	
	public double targetPace = 6.0; //setting it temporarily to 6.0
    ImageButton playSongImageButton, imagebutton2, skupSongImageButton, previousSongImageButton;
    Button songTooSlowButton, songTooFastButton, decreaseTargetPaceButton, increaseTargetPaceButton;
    static TextView targetPaceText;
    static TextView currentPaceText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	//	targetPace = DatabaseActivity.getTargetPace();
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lets_run_mode); 	
		
		//creating image buttons objects and getting their setup from xml
        playSongImageButton = (ImageButton) findViewById(R.id.bPlaySong); 
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); 
        skupSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); 
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); 
        songTooSlowButton = (Button) findViewById(R.id.bSongTooSlow);
        songTooFastButton = (Button) findViewById(R.id.bSongTooFast);
        decreaseTargetPaceButton = (Button) findViewById(R.id.bDecTarget);
        increaseTargetPaceButton = (Button) findViewById(R.id.bIncTarget);
       
        targetPaceText= (TextView) findViewById(R.id.CurrentTargetPace);
        currentPaceText = (TextView) findViewById(R.id.CurrentTargetPace);
        
        // Takes the variable Target CurrentPace and pushes it to the text view.
        String tarPace = String.valueOf(getTargetPace());
        targetPaceText.setText(tarPace);
        
        //setting an event listener for each button
        playSongImageButton.setOnClickListener(this);
       // imagebutton2.setOnClickListener(this);
        skupSongImageButton.setOnClickListener(this);
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
		
		TrackList trackList = new TrackList(getTargetPace()); // This has the Target CurrentPace as its parameter
		MusicPlayer musicPlayer = new MusicPlayer(trackList); // This has the tracklist object as the parameter

		switch (v.getId()) {		
		case R.id.bPlaySong: 	    		  
				try {
					musicPlayer.play();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
	
		case R.id.bSkipTrack:       	  
	            try {
	            	musicPlayer.skip();	
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            break;
	          
		case R.id.bPreviousTrack:
	           try {
					musicPlayer.previous();
	           } catch (IllegalArgumentException e) {
					e.printStackTrace();
	           } catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	           break;   
	            
	            // Decreases the user's preferred pace for this track by 0.5.
		case R.id.bSongTooSlow:
	            	DatabaseActivity.decPrefPace();
	          break;
	          
	            // Increases the user's preferred pace for this track by 0.5.
		case R.id.bSongTooFast:
	          DatabaseActivity.incPrefPace();
	          break;	
	            
   
		case R.id.bDecTarget:
	          setTargetPace(false); 
	          String tarPace = String.valueOf(getTargetPace());
    	      targetPaceText.setText(tarPace);
	          break;
	         
		case R.id.bIncTarget:
	          setTargetPace(true);
	          String tarPace1 = String.valueOf(getTargetPace());
    	      targetPaceText.setText(tarPace1);
	          break;
		}
	}
	
	
	public void setTargetPace(boolean increment){
			if (increment == true){	
				targetPace += 0.5;
			}
				
			else {	
				targetPace -= 0.5;
			}
				
		}
		
		public double getTargetPace(){
			return targetPace;
		}
		
	}
