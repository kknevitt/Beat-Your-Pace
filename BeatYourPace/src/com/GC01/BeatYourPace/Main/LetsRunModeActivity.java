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

import com.GC01.BeatYourPace.Database.DatabaseActivity;
import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.example.beatyourpace.R;

public class LetsRunModeActivity extends Activity implements OnClickListener {
	
	
	public double targetPace;

    ImageButton imagebutton1, imagebutton2, imagebutton4, imagebutton5;
    Button button6, button7, button8, button9;
    TextView atext;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	//	targetPace = DatabaseActivity.getTargetPace();
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lets_run_mode); 	
		
		//creating image buttons objects and getting their setup from xml
        imagebutton1 = (ImageButton) findViewById(R.id.bPlaySong); // for play button
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); //for pause button
        imagebutton4 = (ImageButton) findViewById(R.id.bSkipTrack); //for skipping track button
        imagebutton5 = (ImageButton) findViewById(R.id.bPreviousTrack); //for previous track button
        button6 = (Button) findViewById(R.id.bSongTooSlow);
        button7 = (Button) findViewById(R.id.bSongTooFast);
        button8 = (Button) findViewById(R.id.bDecTarget);
        button9 = (Button) findViewById(R.id.bIncTarget);
        
        atext= (TextView) findViewById(R.id.CurrentTargetPace);
        
        // Takes the variable Target Pace and pushes it to the text view.
        String tarPace = Double.toString(targetPace);
        atext.setText(tarPace);
        
        //setting an event listener for each button
        imagebutton1.setOnClickListener(this);
       // imagebutton2.setOnClickListener(this);
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
		
		TrackList trackList = new TrackList(getTargetPace()); // This has the Target Pace as its parameter
		MusicPlayer musicPlayer = new MusicPlayer(trackList); // This has the tracklist object as the parameter

		switch (v.getId()) {		
		case R.id.bPlaySong: 	    		  
				try {
					musicPlayer.play();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
	
		case R.id.bSkipTrack:       	  
	            try {
	            	musicPlayer.skip();	
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            break;
	          
		case R.id.bPreviousTrack:
	           try {
					musicPlayer.previous();
	           } catch (IllegalArgumentException e) {
	        	   // TODO Auto-generated catch block
					e.printStackTrace();
	           } catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
	          break;
	         
		case R.id.bIncTarget:
	          setTargetPace(true);
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
