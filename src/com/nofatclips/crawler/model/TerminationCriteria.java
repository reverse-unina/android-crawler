package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.ActivityState;

public interface TerminationCriteria {
	
	public boolean termination (ActivityState currentActivity);

}
