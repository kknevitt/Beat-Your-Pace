package com.example.beatyourpace;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
<<<<<<< HEAD
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	//SN sets up the preferences
	SharedPreferences sPref;
	
=======
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

=======
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
public class MainActivity extends Activity implements OnClickListener{
	
	//creating button objects
    Button b1, b2, b3;

>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
		//creating image buttons objects and getting their setup from xml
        b1 = (Button) findViewById(R.id.bTrainingMode); 
        b2 = (Button) findViewById(R.id.bLetsRun); 
        b3 = (Button) findViewById(R.id.bSettings); 
        
        //setting an event listener for each button
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
<<<<<<< HEAD
>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
=======
>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// auto-generated code: Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
<<<<<<< HEAD
<<<<<<< HEAD
	
	//SN added settings option to the top action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // code to launch the Settings Activity when settings is selected from the menu
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	startActivity(new Intent("com.example.beatyourpace.settingsactivity"));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
=======
=======
>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
	@Override
	public void onClick(View v) {
	            //  auto-generated code: Auto-generated method stub
	      if(v == b1){ //onclick the user is taken to the TrainingMode view as per TrainingModeActivity class
              Intent intent = new Intent(this,TrainingModeActivity.class);
              startActivity(intent);
				}
	      if (v == b3) { // // onclick the user is taken to the Settings view as per Settings class
	    	  Intent intent = new Intent(this,SettingsActivity.class);
	    	  startActivity(intent);
	      }
	}       
	

>>>>>>> 02cc68b14f33dd7e32cbaddc6b7aaf324964e8e2
}
