package com.nofatclips.crawler.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.model.TerminationCriteria;
import com.nofatclips.crawler.model.TransitionCriteria;

public class SimpleStrategy implements Strategy {
	
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
		String name = theActivity.getName();
		Log.i("nofatclips", "Checking strategy for activity " + name);
		for (ActivityState stored: guiNodes) {
			Log.d("nofatclips", "Comparing against activity " + stored.getName());
			if (getComparator().compare(theActivity, stored)) {
				theActivity.setId(stored.getId());
				return true;
			}
		}
		Log.i("nofatclips", "Registering activity " + name + " (id: " + theActivity.getId() + ") as a new found state");
		addState (theActivity);
		return false;
	}
	
	@Override
	public boolean checkForTermination (ActivityState a) {
		for (TerminationCriteria t: this.terminators) {
			if (t.termination(a)) return true;
		}
		return false;
	}
	
	public boolean checkForTransition (ActivityState a) {
		// TODO Stub method: assume that there is always a transition
		return true;
	}
	
	public void addTerminationCriteria (TerminationCriteria t) {
		this.terminators.add(t);
	}

	@Override
	public void addTransitionCriteria(TransitionCriteria theCriteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Comparator getComparator() {
		return this.c;
	}

	@Override
	public void setComparator(Comparator c) {
		this.c = c;
	}

	private HashSet<ActivityState> guiNodes = new HashSet<ActivityState> ();
	private Comparator c;
	private Collection<TerminationCriteria> terminators = new ArrayList<TerminationCriteria>();

}
