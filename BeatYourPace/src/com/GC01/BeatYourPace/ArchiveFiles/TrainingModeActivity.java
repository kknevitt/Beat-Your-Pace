package com.GC01.BeatYourPace.ArchiveFiles;
	

import com.example.beatyourpace.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class TrainingModeActivity extends Activity implements OnClickListener {

	int[] tracks = new int[2];					//creating track list array
    int currentTrack = tracks[0];				//setting currentTrack and referencing it to the track list
    private MediaPlayer mediaPlayer = null;		//creating MediaPlayer object
    
    ImageButton imagebutton1, imagebutton2, imagebutton4, imagebutton5;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingmode); 	//loading training mode layout
	   
		
		tracks[0] = R.raw.song;					//setting tracks path and their array index
	    tracks[1] = R.raw.song1;
		
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
	            //auto-generated code:  TODO Auto-generated method stub
	      if(v == imagebutton1){ //onclick the first track is played
	            	mediaPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
				    mediaPlayer.start();
				}
	           // if(v == imagebutton2){ //pause function
	           // 	if (mediaPlayer.isPlaying()){
	    	//			mediaPlayer.pause();
	        //    		}            
	        //    }
	            if(v == imagebutton4){ //onclick the next track is played
	            	 mediaPlayer.release();
	                 if (currentTrack < tracks.length) {
	                   currentTrack++;
	                   mediaPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
	                   mediaPlayer.start();
	            } 
	            if(v == imagebutton5){ //onclick the previous track is played
	                 mediaPlayer.release();
	                 if (currentTrack < tracks.length) {
	                	 if(currentTrack == 0){
	                		 currentTrack = tracks.length -1;
	                		 mediaPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
	                		 mediaPlayer.start();	        
	                   }		
	                	 }
	            	}
	            }
	}

}
