package com.nofatclips.crawler.strategy.criteria;

import com.nofatclips.crawler.model.Strategy;

import android.util.Log;

public class MaxStepsTermination implements TerminationCriteria {
	
	private int max;
	private int current;

	public MaxStepsTermination () {
		this(1000);
	}

	public MaxStepsTermination (int maxSteps) {
		this.max=maxSteps;
		reset();
	}

	@Override
	public boolean termination () {
		this.current--;
		Log.i("nofatclips", "Check for termination: " + current + " steps left of " + max);
		return (this.current==0);
	}
	
	public void reset() {
		this.current=max;
	}
	
	@Override
	public void setStrategy(Strategy theStrategy) {}
	
}