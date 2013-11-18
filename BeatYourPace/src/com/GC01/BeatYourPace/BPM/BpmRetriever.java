//getBPM method used to retrieve BPM value as provided by EchoNest free library  
//more info at: http://developer.echonest.com/licensing.html

package com.GC01.BeatYourPace.BPM;

import java.io.File;
import java.io.IOException;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;


public class BpmRetriever {
		
	
	//creating get method to query EchoNest library
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
