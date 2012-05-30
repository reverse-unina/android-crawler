package com.nofatclips.crawler.strategy.criteria;

import android.os.SystemClock;
import android.util.Log;

import com.nofatclips.crawler.model.Strategy;

public class TimeElapsedPause implements PauseCriteria {
	
	private long max;
	private long start;
	
	public TimeElapsedPause () {
		this(3600);
	}
	
	public TimeElapsedPause (long maxTime) {
		this.max = maxTime;
		this.start = SystemClock.uptimeMillis();
	}
	
	public boolean pause () {
		long current = (SystemClock.uptimeMillis()-this.start)/1000;
		Log.i ("nofatclips", "Check for pause. Time elapsed: " + current + "s; time limit: " + this.max + "s");
		return (current>=max);
	}
	
	public void setStrategy(Strategy theStrategy) {}

}