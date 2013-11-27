/*package com.GC01.BeatYourPace.ArchiveFiles;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.GC01.BeatYourPace.Database.DatabaseHelper;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.example.beatyourpace.R;

public class LetsRunMode extends Activity implements OnClickListener {
	
	public double targetPace;

 
    ImageButton playSongImageButton, imagebutton2, skipSongImageButton, previousSongImageButton, imagebutton6, imagebutton7, imagebutton8, imagebutton9;
    TextView targetPaceText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	//	targetPace = DatabaseActivity.getTargetPace();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 	//loading training mode layout
	
		
		//creating image buttons objects and getting their setup from xml
        playSongImageButton = (ImageButton) findViewById(R.id.bPlaySong); // for play button
        //imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); //for pause button
        skipSongImageButton = (ImageButton) findViewById(R.id.bSkipTrack); //for skipping track button
        previousSongImageButton = (ImageButton) findViewById(R.id.bPreviousTrack); //for previous track button
        imagebutton6 = (ImageButton) findViewById(R.id.bSongTooSlow);
        imagebutton7 = (ImageButton) findViewById(R.id.bSongTooFast);
        imagebutton8 = (ImageButton) findViewById(R.id.bDecTarget);
        imagebutton9 = (ImageButton) findViewById(R.id.bIncTarget);
        
        targetPaceText= (TextView) findViewById(R.id.CurrentTargetPace);
        
        // Takes the variable Target Pace and pushes it to the text view.
        String tarPace = Double.toString(targetPace);
        targetPaceText.setText(tarPace);
        
        //setting an event listener for each button
        playSongImageButton.setOnClickListener(this);
       // imagebutton2.setOnClickListener(this);
        skipSongImageButton.setOnClickListener(this);
        previousSongImageButton.setOnClickListener(this);
        imagebutton6.setOnClickListener(this);
        imagebutton7.setOnClickListener(this);
        imagebutton8.setOnClickListener(this);
        imagebutton9.setOnClickListener(this);
        
        
        
    
   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		
		TrackList trackList = new TrackList("8"); // This has the Target Pace as its parameter
		MusicPlayer musicPlayer = new MusicPlayer(trackList); // This has the tracklist object as the parameter

	      if(v == playSongImageButton){ //onclick the first track is played
	    	  
	    	  
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
	            
	    	  
	    	  
	    	  
	            if(v == skipSongImageButton){ //onclick the next track is played
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
	            if(v == previousSongImageButton){ //onclick the previous track is played
	                 
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
	            
	            // Decreases the user's preferred pace for this track by 0.5.
	            if(v == imagebutton6){
	            	
	            	DatabaseHelper.decPrefPace();
	            	
	            }
	            
	            // Increases the user's preferred pace for this track by 0.5.
	            if(v == imagebutton7){
	            	
	            	DatabaseHelper.incPrefPace();
	            	
	            }
	            
	            if(v == imagebutton8){
	            	
	            	setTargetPace(false);     	
       	
	            }
	            
	            if(v == imagebutton9){
	            	
	            	setTargetPace(true);
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
	}
*/
