package com.nofatclips.crawler.strategy.criteria;

import android.util.Log;

import com.nofatclips.crawler.model.Strategy;

public class NewActivityTransition implements TransitionCriteria {

	private Strategy theStrategy;

	public void setStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;
	}

	public boolean transition() {
		Log.i ("nofatclips", "Checking for Transition: from " + theStrategy.getStateBeforeEvent().getName() + "(" + theStrategy.getStateBeforeEvent().getId() + ") to " + theStrategy.getStateAfterEvent().getName() + "(" + theStrategy.getStateAfterEvent().getId() + ")");
		return (theStrategy.getStateBeforeEvent().getId() != theStrategy.getStateAfterEvent().getId());
	}

}
