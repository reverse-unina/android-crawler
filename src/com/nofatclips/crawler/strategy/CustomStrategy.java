package com.nofatclips.crawler.strategy;

import java.util.ArrayList;
import java.util.Collection;

import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.StrategyCriteria;
import com.nofatclips.crawler.strategy.criteria.ExplorationCriteria;
import com.nofatclips.crawler.strategy.criteria.PauseCriteria;
import com.nofatclips.crawler.strategy.criteria.TerminationCriteria;
import com.nofatclips.crawler.strategy.criteria.TransitionCriteria;

public class CustomStrategy extends SimpleStrategy {

	public CustomStrategy() {
		super();
	}

	public CustomStrategy(Comparator c) {
		super(c);
	}
	
	public CustomStrategy(Comparator c, StrategyCriteria ... criterias) {
		this (c);
		for (StrategyCriteria s: criterias) {
			if (s==null) continue;
			addCriteria(s);
		}
	}
	
	public void addCriteria (StrategyCriteria criteria) {
		if (criteria instanceof ExplorationCriteria) {
			addExplorationCriteria((ExplorationCriteria)criteria);
		} else if (criteria instanceof TransitionCriteria) {
			addTransitionCriteria((TransitionCriteria)criteria);
		} else if (criteria instanceof TerminationCriteria) {
			addTerminationCriteria((TerminationCriteria)criteria);
		} else if (criteria instanceof PauseCriteria) {
			addPauseCriteria((PauseCriteria)criteria);
		}
	}
	
	public void addExplorationCriteria (ExplorationCriteria e) {
		e.setStrategy(this);
		this.explorers.add(e);
	}
	
	@Override
	public boolean checkForExploration () { // Logic AND of the criterias
		for (ExplorationCriteria e: this.explorers) {
			if (!e.exploration()) return false;
		}
		return true;
	}

	public void addTransitionCriteria (TransitionCriteria t) {
		t.setStrategy(this);
		this.transitioners.add(t);
	}
	
	@Override
	public boolean checkForTransition () { // Logic OR of the criterias
		if (this.transitioners.isEmpty()) return true;
		for (TransitionCriteria t: this.transitioners) {
			if (t.transition()) return true;
		}
		return false;
	}
	
	private Collection<ExplorationCriteria> explorers = new ArrayList<ExplorationCriteria>();
	private Collection<TransitionCriteria> transitioners = new ArrayList<TransitionCriteria>();
	
}
