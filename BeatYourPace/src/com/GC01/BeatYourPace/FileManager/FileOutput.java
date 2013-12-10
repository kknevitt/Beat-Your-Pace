package com.GC01.BeatYourPace.FileManager;

/*
 *  This code is from the book:
 *
 *    Winder, R and Roberts, G (2000) Developing Java
 *    Software, second edition, John Wiley &amp; Sons.
 *
 *  It is copyright (c) 2000 Russel Winder and Graham Roberts.
 */

import java.io.* ;
/**
 *  A simple output class to write values to a file of characters.
 *  If any file errors occur, methods in this class will display
 *  an error message and terminate the program.
 *
 *  @version 1.1 1999.09.10
 *  @author Graham Roberts
 *  @author Russel Winder
 */
public class FileOutput {  
  /**
   * Instance variables to store file name.
   */
  private String filename = "" ;
  /**
   * Instance variables to store stream.
   */
  private BufferedWriter writer = null ; 
  /** 
   * Construct &lt;CODE&gt;FileOutput&lt;/CODE&gt; object given a file name.
   */
  public FileOutput(final String name) {
    filename = name ;
    try {
      writer = new BufferedWriter (new FileWriter (filename)) ;
    }
    catch (IOException e) {
      error("Can't open file: " + filename) ;
    }
  }
  /**
   *  Construct &lt;CODE&gt;FileOutput&lt;/CODE&gt; object given a
   * &lt;CODE&gt;File&lt;/CODE&gt; object..
   */
  public FileOutput(final File file) {
    filename = file.getName() ;
    try {
      writer = new BufferedWriter (new FileWriter (filename)) ;
    }
    catch (IOException e) {
      error("Can't open file: " + filename) ;
    }
  }
  /**
   *  Close the file when finished
   */
  public final synchronized void close() {
    try {
      writer.close() ;
    } 
    catch (IOException e) {
      error("Can't close file: " + filename) ;
    }
  }
  /**
   *  Write an &lt;CODE&gt;int&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeInteger(final int i) {
    try {
      writer.write(Integer.toString(i)) ;
    }
    catch (IOException e)  {
      error("writeInteger failed for file: " + filename) ;
    }
  }
  /**
   *  Write a &lt;CODE&gt;long&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeLong(final long l) {
    try {
      writer.write(Long.toString(l)) ;
    }
    catch (IOException e)  {
      error("writeLong failed for file: " + filename) ;
    }
  }
  /**
   *  Write a &lt;CODE&gt;double&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeDouble(final double d) {
    try {
      writer.write(Double.toString(d)) ;
    }
    catch (IOException e)  {
      error("writeDouble failed for file: " + filename) ;
    }
  }
  /**
   *  Write a &lt;CODE&gt;float&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeFloat(final float f) {
    try {
      writer.write(Float.toString(f)) ;
    }
    catch (IOException e)  {
      error("writeFloat failed for file: " + filename) ;
    }
  }
  /**
   *  Write a &lt;CODE&gt;char&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeCharacter(final char c) {
    try {
      writer.write(c) ;
    }
    catch (IOException e)  {
      error("writeCharacter failed for file: " + filename) ;
    }
  }
  /**
   *  Write a &lt;CODE&gt;String&lt;/CODE&gt; value to a file.
   */
  public final synchronized void writeString(final String s) {
    try {
      writer.write(s) ;
    }
    catch (IOException e)  {
      error("writeString failed for file: " + filename) ;
    }
  }
  /**
   *  Write a newline to a file.
   */
  public final synchronized void writeNewline() {
    try {
      writer.write('\n') ;
    }
    catch (IOException e)  {
      error("writeNewline failed for file: " + filename) ;
    }
  }
  /**
   * Deal with a file error
   */
  private void error(final String msg) {
    System.err.println(msg) ;
    System.err.println("Unable to continue executing program.") ;
    System.exit(0) ;
  }
}

