package com.nofatclips.crawler.strategy.criteria;

import com.nofatclips.crawler.model.SaveStateListener;
import com.nofatclips.crawler.model.SessionParams;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.storage.PersistenceFactory;

import android.os.SystemClock;
import android.util.Log;

public class TimeElapsedTermination implements TerminationCriteria, SaveStateListener {
	
	private long max;
	private long start;
	private static final String ACTOR_NAME = "TimeElapsedTermination";
	private static final String PARAM_NAME = "start";
	
	public TimeElapsedTermination () {
		this(3600);
	}
	
	public TimeElapsedTermination (long maxTime) {
		this.max = maxTime;
		this.start = SystemClock.uptimeMillis();
		PersistenceFactory.registerForSavingState(this);
	}
	
	public boolean termination () {
		long current = (SystemClock.uptimeMillis()-this.start)/1000;
		Log.i ("nofatclips", "Check for termination. Time elapsed: " + current + "s; time limit: " + this.max + "s");
		return (current>=max);
	}
	
	public void setStrategy(Strategy theStrategy) {}

	public SessionParams onSavingState() {
		return new SessionParams(PARAM_NAME, this.start);
	}

	public void onLoadingState(SessionParams sessionParams) {
		this.start = sessionParams.getLong(PARAM_NAME);
	}
	
	public String getListenerName() {
		return ACTOR_NAME;
	}

}
