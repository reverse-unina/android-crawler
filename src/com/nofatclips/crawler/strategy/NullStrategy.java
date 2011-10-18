package com.nofatclips.crawler.strategy;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.model.TerminationCriteria;
import com.nofatclips.crawler.model.TransitionCriteria;

public class NullStrategy implements Strategy {

	@Override
	public void addState(ActivityState newActivity) {}

	@Override
	public boolean compareState(ActivityState theActivity) {
		return false; // Always return no match
	}

	@Override
	public Comparator getComparator() {
		return null;
	}

	@Override
	public void setComparator(Comparator c) {}

	@Override
	public boolean checkForTermination(ActivityState theActivity) {
		return false;
	}

	@Override
	public void addTerminationCriteria(TerminationCriteria t) {}

	@Override
	public boolean checkForTransition(ActivityState theActivity) {
		return true;
	}

	@Override
	public void addTransitionCriteria(TransitionCriteria theCriteria) {}

}
