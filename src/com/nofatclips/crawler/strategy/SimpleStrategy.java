package com.nofatclips.crawler.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.strategy.criteria.TerminationCriteria;

public class SimpleStrategy implements Strategy {

	private HashSet<ActivityState> guiNodes = new HashSet<ActivityState> ();
	private Comparator c;
	protected Collection<TerminationCriteria> terminators = new ArrayList<TerminationCriteria>();
	protected boolean positiveComparation = true;
	private Trace theTask;
	private ActivityState beforeEvent;
	private ActivityState afterEvent;

	public SimpleStrategy () {
		super();
	}

	public SimpleStrategy (Comparator c) {
		super();
		setComparator(c);
	}
	
	@Override
	public void addState(ActivityState newActivity) {
		this.guiNodes.add(newActivity);
	}

	@Override
	public boolean compareState(ActivityState theActivity) {
		this.afterEvent = theActivity;
		this.positiveComparation = true;
		String name = theActivity.getName();
		if (theActivity.getId() == "exit") {
			Log.i("nofatclips", "Exit state. Not performing comparation for activity " + name);
			return false;
		}
		Log.i("nofatclips", "Performing comparation for activity " + name);
		for (ActivityState stored: guiNodes) {
			Log.d("nofatclips", "Comparing against activity " + stored.getName());
			if (getComparator().compare(theActivity, stored)) {
				theActivity.setId(stored.getId());
				return true;
			}
		}
		Log.i("nofatclips", "Registering activity " + name + " (id: " + theActivity.getId() + ") as a new found state");
		this.positiveComparation = false;
		addState (theActivity);
		return false;
	}
	
	@Override
	public boolean checkForTermination (ActivityState a) { // Logic OR of the criterias
		for (TerminationCriteria t: this.terminators) {
			if (t.termination()) return true;
		}
		return false;
	}
	
	public boolean checkForTransition () { // Assume that there is always a transition
		return true;
	}
	
	public void addTerminationCriteria (TerminationCriteria t) {
		this.terminators.add(t);
	}

	@Override
	public Comparator getComparator() {
		return this.c;
	}

	@Override
	public void setComparator(Comparator c) {
		this.c = c;
	}

	@Override
	public boolean checkForExploration() {
		return !isLastComparationPositive();
	}

	@Override
	public boolean isLastComparationPositive() {
		return positiveComparation;
	}
	
	@Override
	public void setTask(Trace theTask) {
		this.theTask = theTask;
		this.beforeEvent = theTask.getFinalTransition().getStartActivity();
	}
	
	public Trace getTask () {
		return this.theTask;
	}

	public ActivityState getStateBeforeEvent () {
		return this.beforeEvent;
	}

	public ActivityState getStateAfterEvent () {
		return this.afterEvent;
	}	

}
