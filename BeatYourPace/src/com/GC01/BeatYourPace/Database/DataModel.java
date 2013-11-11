package com.GC01.BeatYourPace.Database;

public class DataModel {

	 public static final String TABLE_NAME = "bpm_pace";
	    /**
	     * DEFINE THE TABLE
	     */
	    // Columns
	    private static String ID = "_id";
	    public static int BPM = 24;  //replace with actual
	    public static double PACE = 6.0;  //replace with actual
	    public static String TITLE = "title";
	    public static String ARTIST = "artist";
	    /**
	     * DEFINE THE CONTENT TYPE AND URI
	     */
	    // TO BE done
	      
	 public DataModel(){}
	     
	        public DataModel(String ID, int BPM, double PACE, String TITLE, String ARTIST) {
	            super();
	            this.setID(ID);
	            this.BPM = BPM;
	            this.PACE = PACE;
	            this.TITLE = TITLE;
	            this.ARTIST = ARTIST;
	        }
	     
	        //getter
	        @Override
	        public String toString() {
	            return "DataModel [ID=" + getID() + ", BPM=" + BPM + ", pace=" + PACE +", title=" +TITLE +", artist=" + ARTIST +" ]";
	        }

			public static String getID() {
				return ID;
			}

			public static void setID(String iD) {
				ID = iD;
			}
	   }
