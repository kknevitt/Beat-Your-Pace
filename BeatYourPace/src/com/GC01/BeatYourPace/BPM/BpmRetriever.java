package com.GC01.BeatYourPace.BPM;

import java.io.File;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;


public class BpmRetriever {

	
	private static final String API_KEY = ("LKMQIIIK24AL58W6D");

	public static void main (String [] args)throws EchoNestException, Exception{

    EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
    File file = new File("C:\\Users\\Solange\\Desktop\\eclipse java\\BPM\\res\\raw\\04.mp3");
    Track track = echoNest.uploadTrack(file, true);
    track.waitForAnalysis(30000);
    TrackAnalysis a = track.getAnalysis();
    
    
    System.out.print("Tempo " + a.getTempo());

	
}
