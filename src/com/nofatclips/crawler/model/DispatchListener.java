package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.Trace;

public interface DispatchListener {
	
	public void onTaskDispatched (Trace t);
	public void onNewTaskAdded (Trace t);

}
