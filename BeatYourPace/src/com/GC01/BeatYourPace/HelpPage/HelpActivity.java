/** 
 * <p> This class is used to enable the Help Page view</p>
 * <p> The page provides a user-guide on the main functions of the app</p>
 * @authorLaura Barbosa
 * @version 1.0, Updated 09/12/2013
 */
package com.GC01.BeatYourPace.HelpPage;

import com.example.beatyourpace.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}
