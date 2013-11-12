package com.GC01.BeatYourPace.Main;
	

import java.io.IOException;

import com.GC01.BeatYourPace.MusicPlayer.NewMusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.NewTrackList;
import com.example.beatyourpace.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class newTrainingModeActivity extends Activity implements OnClickListener {

    
    ImageButton imagebutton1, imagebutton2, imagebutton4, imagebutton5;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 	//loading training mode layout
		
		//creating image buttons objects and getting their setup from xml
        imagebutton1 = (ImageButton) findViewById(R.id.bPlaySong); // for play button
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); //for pause button
        imagebutton4 = (ImageButton) findViewById(R.id.bSkipTrack); //for skipping track button
        imagebutton5 = (ImageButton) findViewById(R.id.bPreviousTrack); //for previous track button
        
        //setting an event listener for each button
        imagebutton1.setOnClickListener(this);
       // imagebutton2.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
        
        
    
   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		
		NewTrackList trackList = new NewTrackList("8"); // This has the Target Pace as its parameter
		NewMusicPlayer musicPlayer = new NewMusicPlayer(trackList); // This has the tracklist object as the parameter

	      if(v == imagebutton1){ //onclick the first track is played
	    	  
	    	  
	    	  try {
	    		  
					musicPlayer.play();
	    	  
					
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
	      }
	            
	    	  
	    	  
	    	  
	            if(v == imagebutton4){ //onclick the next track is played
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
	                 
	                 
	                	 
	            } 
	            if(v == imagebutton5){ //onclick the previous track is played
	                 
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
	            }
	      }
	}
