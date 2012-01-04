package com.nofatclips.crawler.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.util.Log;
import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.StateDiscoveryListener;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.model.TerminationListener;
import com.nofatclips.crawler.strategy.criteria.PauseCriteria;
import com.nofatclips.crawler.strategy.criteria.TerminationCriteria;

public class SimpleStrategy implements Strategy {

	private HashSet<ActivityState> guiNodes = new HashSet<ActivityState> ();
	private Comparator c;
	protected Collection<TerminationCriteria> terminators = new ArrayList<TerminationCriteria>();
	protected Collection<PauseCriteria> pausers = new ArrayList<PauseCriteria>();
	protected boolean positiveComparation = true;
	private Trace theTask;
	private ActivityState beforeEvent;
	private ActivityState afterEvent;
	private List<StateDiscoveryListener> theListeners = new ArrayList<StateDiscoveryListener>();
	private List<TerminationListener> endListeners = new ArrayList<TerminationListener>();

	public SimpleStrategy () {
		super();
	}

	public SimpleStrategy (Comparator c) {
		super();
		setComparator(c);
	}
	
	public void addState(ActivityState newActivity) {
		for (StateDiscoveryListener listener: getListeners()) {
			listener.onNewState(newActivity);
		}
		this.guiNodes.add(newActivity);
	}

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
	
	public boolean checkForTermination () { // Logic OR of the criterias
		for (TerminationCriteria t: this.terminators) {
			if (t.termination()) {
				for (TerminationListener tl: getEndListeners()) {
					tl.onTerminate();
				}
				return true;
			}
		}
		return false;
	}

	public boolean checkForPause () { // Logic OR of the criterias
		for (PauseCriteria p: this.pausers) {
			if (p.pause()) return true;
		}
		return false;
	}

	public boolean checkForTransition () { // Assume that there is always a transition
		return true;
	}
	
	public void addTerminationCriteria (TerminationCriteria t) {
		this.terminators.add(t);
	}

	public void addPauseCriteria (PauseCriteria p) {
		this.pausers.add(p);
	}

	public Comparator getComparator() {
		return this.c;
	}

	public void setComparator(Comparator c) {
		this.c = c;
	}

	public boolean checkForExploration() {
		return !isLastComparationPositive();
	}

	public boolean isLastComparationPositive() {
		return positiveComparation;
	}
	
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

	public List<StateDiscoveryListener> getListeners() {
		return this.theListeners;
	}

	public List<TerminationListener> getEndListeners() {
		return this.endListeners;
	}

	public void registerStateListener(StateDiscoveryListener theListener) {
		this.theListeners.add(theListener);
	}

	public void registerTerminationListener(TerminationListener theListener) {
		this.endListeners.add(theListener);
	}

}
