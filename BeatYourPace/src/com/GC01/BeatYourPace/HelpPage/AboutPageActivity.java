package com.GC01.BeatYourPace.HelpPage;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Displays information about the app.
 * 
 * 	<dt> Description:
 * 	<dd> Accessed via the main bar menu, this section provides information about the app, including copyright and constraints.
 * </dl>
 * 
 * @version $Date: 2014/01/19
 * @author sarahnicholson
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
