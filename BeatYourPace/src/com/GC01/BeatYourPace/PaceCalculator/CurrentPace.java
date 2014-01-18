package com.GC01.BeatYourPace.PaceCalculator;


import java.util.List;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.example.beatyourpace.R;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/** 
 * @author Laura Barbosa & Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 */
public class CurrentPace extends Service implements LocationListener { 

TextView speedText1;
double speed = 0;
String speed1;
LocationManager locationManager;
LocationListener locationListener;

protected void onCreate(Bundle savedInstanceState) { 
    super.onCreate(); 


    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);
    

    }

    public void onLocationChanged(Location location) {

        speed = location.getSpeed();
        getGPSInfo(speed);

    }

	public void onProviderDisabled(String provider) {
		Toast.makeText(getBaseContext(), "Provider: " + provider + " disabled", Toast.LENGTH_SHORT).show(); 
		
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(getBaseContext(), "Provider: " + provider + " enabled", Toast.LENGTH_SHORT).show();
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		String statusString = "";
		switch(status) {
		case android.location.LocationProvider.AVAILABLE:
			statusString = "available";
		case android.location.LocationProvider.OUT_OF_SERVICE:
			statusString = "out of service";
		case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
			statusString = "temporarily unavailable";
			
		Toast.makeText(getBaseContext(), provider + " " + statusString, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void onDestroy(){
		super.onDestroy();;

	}
	

	private double getGPSInfo(double speed){
         return speed;
   }

	  private void sendGPSInfo() {
          
          //temporary toast message to test that the GPS data is being sent to the training mode class
          Toast.makeText(getApplicationContext(), "send GPSInfo intent is being called.",
                             Toast.LENGTH_LONG).show();
          
            Intent intent = new Intent("Current Pace Event");
            
            intent.putExtra("GPS Current Pace Info", getGPSInfo(speed));
            LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
            System.out.println("GPS Sent");
   }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
	
	
	
