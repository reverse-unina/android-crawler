package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.ActivityState;

public interface TransitionCriteria {
	
	public boolean transition (ActivityState currentActivity);

}
