package com.nofatclips.crawler.strategy;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.TerminationCriteria;

public class MaxStepsTermination implements TerminationCriteria {

	public MaxStepsTermination () {
		this(1000);
	}

	public MaxStepsTermination (int maxSteps) {
		this.max=maxSteps;
		reset();
	}

	@Override
	public boolean termination (ActivityState currentActivity) {
		this.current--;
		Log.i("nofatclips", "Check for termination: " + current + " steps left of " + max);
		return (this.current==0);
	}
	
	public void reset() {
		this.current=max;
	}
	
	private int max;
	private int current;
	
}