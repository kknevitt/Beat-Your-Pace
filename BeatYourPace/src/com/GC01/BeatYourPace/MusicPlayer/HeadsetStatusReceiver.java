package com.GC01.BeatYourPace.MusicPlayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class HeadsetStatusReceiver extends BroadcastReceiver {
    @Override
    
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            if (MusicController.isMusicPlaying() == true) {
            	
            	MusicController.pressPlay_Pause();
            	Toast.makeText(context, "Headphone Connected", Toast.LENGTH_LONG).show();;
            	Log.i("NoisyAudioStreamReceiver", "Headphone Connected");
            }
        }
    }
}
