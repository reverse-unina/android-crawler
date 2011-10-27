package com.nofatclips.crawler.strategy;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.Strategy;

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
	public boolean checkForTransition() {
		return true;
	}

	@Override
	public void setTask(Trace theTask) {}

	@Override
	public boolean checkForExploration() {
		return !isLastComparationPositive();
	}

	@Override
	public boolean isLastComparationPositive() {
		return false;
	}

	@Override
	public Trace getTask() {
		return null;
	}

	@Override
	public ActivityState getStateBeforeEvent() {
		return null;
	}

	@Override
	public ActivityState getStateAfterEvent() {
		return null;
	}

}
