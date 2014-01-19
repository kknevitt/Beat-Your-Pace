package com.GC01.BeatYourPace.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.os.Environment;

public class DatabaseExportFile {

		public static void expDb() {
			try {
				File sd = Environment.getExternalStorageDirectory();
				File data = Environment.getDataDirectory();

				if (sd.canWrite()) {
					
					String currentDBPath = "//data//BeatYourPace//databases//byp.db";
					String backupDBPath = "BYP";
					File currentDB = new File(data, currentDBPath);
					File backupDB = new File(sd, backupDBPath);

					if (currentDB.exists()) {
						FileChannel src = new FileInputStream(currentDB).getChannel();
						FileChannel dst = new FileOutputStream(backupDB).getChannel();
						dst.transferFrom(src, 0, src.size());
						src.close();
						dst.close();
					}
				}
			} catch (Exception e) {
			}
		}
	}

