package com.example.beatyourpace;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
    Button b1, b2, b3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//creating image buttons objects and getting their setup from xml
        b1 = (Button) findViewById(R.id.bTrainingMode); //goes to training mode view
        b2 = (Button) findViewById(R.id.bLetsRun); //goes to lets run view
        b3 = (Button) findViewById(R.id.bSettings); //goes to settings view
        
        //setting an event listener for each button
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
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
	      if(v == b1){ //play function
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
				}
	      if (v == b3) { // settings
	    	  Intent intent = new Intent(this,SettingsActivity.class);
	    	  startActivity(intent);
	      }
	}       
	

}
