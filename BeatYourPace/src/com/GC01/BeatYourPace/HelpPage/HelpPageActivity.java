package com.GC01.BeatYourPace.HelpPage;

import com.example.beatyourpace.R;
import com.example.beatyourpace.R.layout;
import com.example.beatyourpace.R.menu;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		EasyTracker.getInstance(this).activityStart(this);
	}

	

}
