package com.nofatclips.crawler.strategy.criteria;

import android.util.Log;

import com.nofatclips.crawler.model.Strategy;

public class AfterWidgetDontExplore implements ExplorationCriteria {

	private Strategy theStrategy;
	private String[] forbiddenWidgets;

	public AfterWidgetDontExplore (int ... forbiddenWidgets) {
		String[] ids = new String[forbiddenWidgets.length];
		for (int n=0; n<forbiddenWidgets.length; n++) {
			ids[n] = String.valueOf(forbiddenWidgets[n]);
		}
		this.forbiddenWidgets = ids;
	}
	
	public AfterWidgetDontExplore (String ... forbiddenWidgets) {
		this.forbiddenWidgets = forbiddenWidgets;
	}
	
	public void setStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;	
	}

	public boolean exploration() {
		return (checkWidgetId()); 
	}

	private boolean checkWidgetId() {
		String widgetId = this.theStrategy.getTask().getFinalTransition().getEvent().getWidgetId();
		if (widgetId.equals("")) return true;
		Log.i("nofatclips", "Checking for exploration: event widget id = " + widgetId);
		for (String id: this.forbiddenWidgets) {
			if (id.equals(widgetId)) return false;
		}
		return true;
	}

}
