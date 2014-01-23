package com.example.beatyourpace;

import com.GC01.BeatYourPace.Main.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;


/**
 * Splash screen for the application
 * 
 * @author Laura Barbosa,  
 * @version 2014/01/22
 */


public class SplashScreen extends Activity {

	private static int SPLASH_TIME = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		new Handler().postDelayed(new Runnable() {

		    public void run() {
		    	 Intent intent = new Intent(SplashScreen.this, MainActivity.class);
	                startActivity(intent);
	                finish();
		    }
			
		}, SPLASH_TIME);
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
