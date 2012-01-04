package com.nofatclips.crawler.strategy.criteria;

import android.util.Log;

import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.crawler.model.Strategy;

public class MaxDepthExplore implements ExplorationCriteria {

	private int maxDepth;
	private Strategy theStrategy;

	public MaxDepthExplore () {}
	
	public MaxDepthExplore (int maxDepth) {
		setMaxDepth (maxDepth);
	}
	
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	public int getMaxDepth () {
		return this.maxDepth;
	}

	public void setStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;
	}

	@SuppressWarnings("unused")
	public boolean exploration() {
		int transitions = 0;
		for (Transition t: this.theStrategy.getTask()) {
			transitions++;
		}
		Log.i("nofatclips", "Checking for depth: this trace is " + transitions + " transitions deep (max = " + getMaxDepth() + ")");
		return (transitions<getMaxDepth());
	}
	
	

}
