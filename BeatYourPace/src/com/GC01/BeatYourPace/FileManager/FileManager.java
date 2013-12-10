package com.GC01.BeatYourPace.FileManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.GC01.BeatYourPace.FileManager.FileInput;
import com.GC01.BeatYourPace.FileManager.FileOutput;
import com.GC01.BeatYourPace.FileManager.CSVManager;

/**
 *  This class takes either input or output, a file name, and file type as parameters
 *  It then calls separate classes to process the file
 *  
 *  Currently uses code from Graham Roberts file input and file output programs
 *  Uses ByteManager, CSVManager, ObjectManager, StringManager and ArrayManager
 *  @version 0.1 22.10.2013
 *  @author Sarah Nicholson
 */

public class FileManager {

	/**
	 * Instance variables to store file details.
	 */
	static Scanner userInput = new Scanner(System.in);      //setting scanner objects to get user inputs 
	static Scanner userInput2 = new Scanner(System.in);  
	static String operation = "";

	public static void main(String[]args) throws FileNotFoundException { 
		//String test = "Age\tGender\tFavouriteColour\t5\tGirl\tYellow";
		//  //Users/sarahnicholson/Documents/workspace/test.txt
		// //Users/sarahnicholson/Documents/workspace/test.csv
		
		//find out if the want to read or write
		System.out.println("What operation would you like to complete? Type 'read' or 'write'."); 
		operation = userInput2.nextLine(); 
		
		//get the file type and then process according to the result
		//fileType(operation);
	}

	/*
	public static void fileType(String opType) {      
		opType = operation;
		System.out.println("Please add the file path you'd like to use. Please add one extra backslash to the file path each time it occurs."); 
		System.out.println("For example, for c:\\file, please add c:\\\\file."); 
		String filePath = userInput.nextLine(); 

		//setting a variable to maintain the original filePath name to be sent to FileInput through other methods. 
		String filePath1 = filePath; 

		//variable to hold the file type
		String fileType = null;

		int i = filePath.lastIndexOf('.');                  //check for file type 
		if (i > 0) { 
			fileType = filePath.substring(i+1); 

			switch(fileType) {
			case "csv": 
				if (opType.equals("read")){          
					try {
						CSVManager.readCSV(filePath1);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}        
				} else if (opType.equals("write")){ 
					System.out.println("What would you like to write to file?"); 
					String userMessage = userInput2.nextLine(); 
					try {
						CSVManager.writeCSV(userMessage, filePath1);
					} catch (FileNotFoundException e) {
						System.out.print("oops");
						e.printStackTrace();
					}                       
				} 
				break;
			case "txt": 
				if (opType.equals("read")){ 
					StringManager.txtReader(filePath1);        
				} else if (opType.equals("write")){ 
					System.out.println("What would you like to write to file?"); 
					String userMessage = userInput2.nextLine(); 
					StringManager.txtWriter(userMessage, filePath1);   
				}
				break;
			case "html": 
				break;
			default: 
				break;
			}
		}
	}

*/

	/*
	 * The following is not yet implemented
	 * The idea was to create a file manager object that worked out the type of input, the file name, and the type of output
	 * and then process accordingly. This is now partially replaced by ftype above.
	 */
	private String filename = "" ;
	private String filetype = "" ; 
	// for the readOrWrite boolean true = read, false = write
	private boolean read;

	public FileManager(boolean inputType, String fname, String ftype) {     
		filename = fname;
		filetype = ftype;
		read = inputType;
	}

}
