package it.unina.androidripper.strategy.criteria;

import it.unina.androidripper.model.Strategy;
import android.util.Log;

public class MaxDepthTermination implements TerminationCriteria {

	private int maxDepth;
	private Strategy theStrategy;

	public MaxDepthTermination () {}
	
	public MaxDepthTermination (int maxDepth) {
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

	public boolean termination() {
		int transitions = theStrategy.getDepth();
		Log.i("androidripper", "Checking for depth: this trace is " + transitions + " transitions deep (max = " + getMaxDepth() + ")");
		return (transitions>=getMaxDepth());
	}

}