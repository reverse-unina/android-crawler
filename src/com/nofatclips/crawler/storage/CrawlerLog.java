package com.nofatclips.crawler.storage;

public abstract class CrawlerLog {
	
	private String tag = "Crawler";
	private int defaultLevel = 1;
	
	public void setTag (String t) {
		this.tag = t;
	}
	
	public String getTag () {
		return this.tag;
	}
	
	public void setLogLevel (int level) {
		this.defaultLevel = level;
	}
	
	public int getLogLevel () {
		return this.defaultLevel;
	}
	
	public void logLn (String textToLog) {
		logLn (textToLog, getLogLevel(), getTag());
	}
	
	public void logLn (String textToLog, int logLevel) {
		logLn (textToLog, logLevel, getTag());
	}

	public void logLn (String textToLog, String tag) {
		logLn (textToLog, getLogLevel(), tag);
	}

	public abstract void logLn (String textToLog, int logLevel, String tag);

}