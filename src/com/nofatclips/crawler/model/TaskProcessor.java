package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.Trace;

// TaskProcessor defines the steps by which the crawler interact with the application under test.
// The actual interaction operations (e.g. calls to Robotium) should be provided by a Robot, in
// order to make it easy to switch between different testing frameworks if needed.

public interface TaskProcessor {
	
	public void execute (Trace trace);
	public void setRobot (Robot theRobot);

}
