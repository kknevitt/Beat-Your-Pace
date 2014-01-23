package com.GC01.BeatYourPace.HelpPage;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Accessed via the main bar menu, this section provides information about the app, including copyright and constraints.
 * 
 * @version $Date: 2014/01/19
 * @author sarah nicholson
 *
 */

public class AboutPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		/**Google Analytics tracking code**/
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
