package com.nofatclips.crawler.storage;

import static android.util.Log.*;


// Logs using the Android standard Log class

public class SessionLogger extends CrawlerLog {

	@Override
	public void logLn(String textToLog, int logLevel, String tag) {
		switch (logLevel) {
			case 1: d (textToLog, tag); break;
			case 2: i (textToLog, tag); break;
			case 3: w (textToLog, tag); break;
			case 4: e (textToLog, tag); break;
			default: v (textToLog, tag); break;
		}
	}

}
