package com.GC01.BeatYourPace.Database;

import com.example.beatyourpace.R;
import com.example.beatyourpace.R.layout;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
@SuppressLint("NewApi")
public class DataActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		
		getMetaData();
		
	}
		
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public void getMetaData(){
	
	// use the mediametadata.retrieviver API to call the details
    MediaMetadataRetriever retMeta = new MediaMetadataRetriever();
    retMeta.setDataSource("R.raw.song");
    String title = retMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
    String  artist = retMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    
    //write to console to test it works!
    System.out.print(title);
    System.out.print(artist);
    
    // close object
    retMeta.release();
    
}

}
