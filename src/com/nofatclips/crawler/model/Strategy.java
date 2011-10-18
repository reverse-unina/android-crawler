package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.ActivityState;

// Strategy takes three decisions:
// 1) detecting whether a transition occurred (store it!) or not (discard it!) after the injection of an event
// 2) when a transition has actually occurred, whether to plan for deeper exploration (planning the injection of further events) or not (start a new trace)
// 3) whether to exit the crawling loop (terminate) even though more tasks are available

public interface Strategy {
	
	public void addState (ActivityState newActivity);
	
	public boolean compareState (ActivityState theActivity);
	public Comparator getComparator ();
	public void setComparator (Comparator c);

	public boolean checkForTransition (ActivityState theActivity);
	public void addTransitionCriteria (TransitionCriteria theCriteria);

	public boolean checkForTermination (ActivityState theActivity);
	public void addTerminationCriteria (TerminationCriteria theCriteria);
	
}
