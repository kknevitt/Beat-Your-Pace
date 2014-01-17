package com.GC01.BeatYourPace.HelpPage;

import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;
import android.os.Bundle;
import android.app.Activity;

/** 
 * @author Laura Barbosa 
 * @version 1.0, Updated 17/01/2014
 */

/**
 * Sets the view for the HelpPage layout
 *  */

public class HelpPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		/**Google Analytics tracking code**/
		EasyTracker.getInstance(this).activityStart(this);
	}
}
