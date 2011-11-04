package com.nofatclips.crawler.strategy.criteria;

import android.util.Log;

import com.nofatclips.crawler.model.Strategy;

public class AfterEventDontExplore implements ExplorationCriteria {

	private Strategy theStrategy;
	private String[] forbiddenDescriptions;

	public AfterEventDontExplore (String ... forbiddenDescriptions) {
		this.forbiddenDescriptions = forbiddenDescriptions;
	}
	
	@Override
	public void setStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;	
	}

	@Override
	public boolean exploration() {
		return (checkDescription() && checkWidgetName()); 
	}

	public boolean checkDescription() {
		String eventDescription = this.theStrategy.getTask().getFinalTransition().getEvent().getDescription();
		if (eventDescription.equals("")) return true;
		Log.i("nofatclips", "Checking for exploration: event description = " + eventDescription);
		for (String desc: this.forbiddenDescriptions) {
			if (desc.equals(eventDescription)) return false;
		}
		return true;
	}

	public boolean checkWidgetName() {
		String widgetDescription = this.theStrategy.getTask().getFinalTransition().getEvent().getWidgetName();
		if (widgetDescription.equals("")) return true;
		Log.i("nofatclips", "Checking for exploration: event widget name = " + widgetDescription);
		for (String desc: this.forbiddenDescriptions) {
			if (desc.equals(widgetDescription)) return false;
		}
		return true;
	}

}
