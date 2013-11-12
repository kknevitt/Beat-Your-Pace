package com.GC01.BeatYourPace.BPM;

import java.io.File;
import java.io.IOException;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;


public class BpmRetriever {
	
	//adding EchoNest API developer key
	private static final String API_KEY = ("LKMQIIIK24AL58W6D"); 
	
	//instance of library class
	EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
	
	
	//creating get method to query EchoNest library
	double getBPM() throws EchoNestException, IOException{

	//open file and uploads track to get BPM analysed
	//to be replaced by 'get music' method
    File file = new File("C:\\Users\\Solange\\Desktop\\eclipse java\\BPM\\res\\raw\\04.mp3");
    Track track = echoNest.uploadTrack(file, true);
    
    //analysing track to get BPM
    track.waitForAnalysis(30000);
    TrackAnalysis a = track.getAnalysis();
    
    //retrieve the BPM
    double tempo = a.getTempo(); 
    
    //temporary function
    System.out.print("Tempo " + a.getTempo());
    
    return tempo;
   

	}
}
