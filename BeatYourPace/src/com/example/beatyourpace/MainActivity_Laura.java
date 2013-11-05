package com.example.beatyourpace;

import com.example.appproto.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainActivity_Laura extends Activity implements OnClickListener{

	
int[] tracks = new int[2];
int currentTrack = tracks[0];
private MediaPlayer mediaPlayer = null;

ImageButton imagebutton1, imagebutton2, imagebutton4, imagebutton5;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main); 
    
    //setting tracks path
	tracks[0] = R.raw.song;
    tracks[1] = R.raw.song1;
	
	//creating image buttons objects and getting their setup from xml
    imagebutton1 = (ImageButton) findViewById(R.id.imageButton1); //play
    imagebutton2 = (ImageButton) findViewById(R.id.imageButton2); //pause
    imagebutton4 = (ImageButton) findViewById(R.id.bSkipTrack); //next
    imagebutton5 = (ImageButton) findViewById(R.id.imageButton5); //last
    
    //setting an event listener for each button
    imagebutton1.setOnClickListener(this);
    imagebutton2.setOnClickListener(this);
    imagebutton4.setOnClickListener(this);
    imagebutton5.setOnClickListener(this);

}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}

@Override
public void onClick(View v) {
            // TODO Auto-generated method stub
      if(v == imagebutton1){ //play function
            	mediaPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
			    mediaPlayer.start();
			}
            if(v == imagebutton2){ //pause function
            	if (mediaPlayer.isPlaying()){
    				mediaPlayer.pause();
            		}            
            }
            if(v == imagebutton4){ //skip next song function
            	 mediaPlayer.release();
                 if (currentTrack < tracks.length) {
                   currentTrack++;
                   mediaPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
                   mediaPlayer.start();
            } 
            if(v == imagebutton5){ //back function
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

