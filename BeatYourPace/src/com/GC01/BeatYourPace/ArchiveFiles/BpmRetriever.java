/**
 * 
 * @author Laura Barbosa
 * @version 1.0, Updated 18/11/2013
 * 
 * 
 * Class implements getBPM method used to retrieve a track BPM value as provided by EchoNest free library. 
 * More info about the at: http://the.echonest.com/
 * 
 * The class has temporary methods and variables set for local testing purposes. 
 */

package com.GC01.BeatYourPace.ArchiveFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;


public class BpmRetriever {
	
	/**creating get method to query EchoNest library 
	 * 
	 * @return
	 * @throws EchoNestException
	 * @throws IOException
	 */
	double getBPM() throws EchoNestException, IOException{

	//adding EchoNest API developer key
	final String API_KEY = ("LKMQIIIK24AL58W6D"); 
		
	//instance of library class
	EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
		
	//open file and uploads track to get BPM analysed
	//to be replaced by a 'get music' method
    File file = new File("C:\\Users\\Solange\\Desktop\\eclipse java\\BPM\\res\\raw\\04.mp3");
    Track track = echoNest.uploadTrack(file, true);
    
    //setting a max time for analysis that will retrieve a track's BPM
    track.waitForAnalysis(30000);
    TrackAnalysis analysis = track.getAnalysis();
    
    //retrieve the BPM
    double tempo = analysis.getTempo(); 
    
    //temporary function
    System.out.print("Tempo " + analysis.getTempo());
    
    return tempo;

	}
	
}
