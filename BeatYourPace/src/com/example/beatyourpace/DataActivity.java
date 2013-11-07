package com.example.beatyourpace;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
@SuppressLint("NewApi")
public class DataActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
