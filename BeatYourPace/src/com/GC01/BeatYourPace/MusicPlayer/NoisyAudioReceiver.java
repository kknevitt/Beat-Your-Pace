package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

/** Receiver to check for the status of AUDIO_BECOMING_NOISY indicating the removal of headphones
 */
// Information read from Android MediaPlayer API.
public class NoisyAudioReceiver extends BroadcastReceiver {
    @Override
    
    // Pauses music in order to ensure that audio doesn't continue playback when the user wouldn't want to.
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            if (MusicController.isMusicPlaying() == true) {
            	
            	MusicController.pressPlay_Pause();
            	Toast.makeText(context, "Headphone Disconnected", Toast.LENGTH_LONG).show();;
            	Log.i("NoisyAudioStreamReceiver", "Headphone Disconnected");
            }
        }
    }
}
