package com.GC01.BeatYourPace.FileManager;

import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.StringTokenizer;

import com.GC01.BeatYourPace.FileManager.FileInput;
import com.GC01.BeatYourPace.FileManager.FileOutput;

//import java.util.regex;

/*
 * CSVManager class that and read and write doubles, strings and other types into files, 
 * separated by commas (CSV file). 
 * @param fname
 * @author Sarah Nicholson
 */

public class CSVManager {
	
	/*
	 * read the contents of a CSV, and convert to a continuous string printing out to the console
	 */
	public static void readCSV (String fname)  throws FileNotFoundException  {
		
		//use the file input manager to create an object that lets you read the file
		FileInput fi = new FileInput(fname); 
		
		//read in the string content using the readString method from FileInput which should
		//read until the eof is reached
		//String result = fi.readString();
		
		//result = result.replace("\n", ","); 
		
		//spit the string into components based on the comma separator
			//String[] result1 = result.split(",");
			  //  for (int x=0; x<result1.length; x++)
			    //  System.out.println(result1[x]);
		
		while (fi.eof() != true) {
			System.out.print(fi.readString());
		}

	}
		public static void writeCSV (String sname, String fpath) throws FileNotFoundException {						
			//Pass in the string and swap a tab with , to spaces
			//String s = sname.replace("\t", ","); 
	 
			//Create a fileoutput object with the contents of the string s
			FileOutput fo = new FileOutput(fpath);
			fo.writeString(sname);
			fo.close();
		}
} 