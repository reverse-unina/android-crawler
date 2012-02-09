package com.nofatclips.crawler.strategy;

import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.model.StrategyCriteria;
import com.nofatclips.crawler.strategy.criteria.MaxDepthExplore;
import com.nofatclips.crawler.strategy.criteria.MaxStepsPause;
import com.nofatclips.crawler.strategy.criteria.MaxStepsTermination;
import com.nofatclips.crawler.strategy.criteria.NewActivityExplore;
import com.nofatclips.crawler.strategy.criteria.NewActivityTransition;
import com.nofatclips.crawler.strategy.criteria.TimeElapsedPause;
import com.nofatclips.crawler.strategy.criteria.TimeElapsedTermination;

public class StrategyFactory {

	private Comparator comparator;
	private int maxTraces = 0;
	private int maxTransitions = 0;
	private long maxSeconds = 0;
	private long pauseSeconds = 0;
	private boolean checkTransistions = false;
	private StrategyCriteria[] otherCriterias = new StrategyCriteria[] {};
	private int pauseTraces;
	
	public StrategyFactory () {}
	
	public StrategyFactory (Comparator c) {
		setComparator (c);
	}

	public StrategyFactory (Comparator c, StrategyCriteria ... criterias) {
		this (c);
		setMoreCriterias(criterias);
	}

	public void setComparator(Comparator c) {
		this.comparator = c;
	}

	public Strategy getStrategy () {
		if (useCustomStrategy ()) {
			CustomStrategy s = new CustomStrategy(this.comparator, this.otherCriterias);
			s.addCriteria (new NewActivityExplore());
			if (checkMaxTraces()) {
				s.addCriteria(new MaxStepsTermination(this.maxTraces));
			}
			if (checkForDepth()) {
				s.addCriteria(new MaxDepthExplore(this.maxTransitions));
			}
			if (checkTransition()) {
				s.addCriteria(new NewActivityTransition());
			}
			if (checkSessionTime()) {
				s.addCriteria(new TimeElapsedTermination(this.maxSeconds));
			}
			if (checkSessionTimeForPause()) {
				s.addCriteria(new TimeElapsedPause(this.pauseSeconds));
			}
			if (checkTracesForPause()) {
				s.addCriteria(new MaxStepsPause(this.pauseTraces));
			}
			return s;
		}
		SimpleStrategy s = new SimpleStrategy (this.comparator);
		if (checkMaxTraces()) {
			s.addTerminationCriteria(new MaxStepsTermination(this.maxTraces));
		}
		return s;

	}
	
	public boolean useCustomStrategy () {
		return (checkTransition() || checkForDepth() || checkSessionTime() || hasMoreCriterias() || checkTracesForPause() || checkSessionTimeForPause());
	}
	
	public boolean checkForDepth() {
		return (this.maxTransitions>0);
	}
	
	public boolean checkMaxTraces() {
		return (this.maxTraces>0);
	}
	
	public boolean checkTracesForPause() {
		return (this.pauseTraces>0);
	}
	
	public boolean checkTransition() {
		return this.checkTransistions;
	}
	
	public boolean checkSessionTime() {
		return (this.maxSeconds>0);
	}

	public boolean checkSessionTimeForPause() {
		return (this.pauseSeconds>0);
	}

	public boolean hasMoreCriterias() {
		return (this.otherCriterias.length>0);
	}

	public void setDepth(int depth) {
		this.maxTransitions = depth;
	}
	
	public void setMaxTraces(int length) {
		this.maxTraces = length;
	}
	
	public void setCheckTransitions (boolean check) {
		this.checkTransistions = check;
	}
	
	public void setMaxSeconds (long max) {
		this.maxSeconds = max;
	}

	public void setPauseSeconds (long span) {
		this.pauseSeconds = span;
	}

	public void setMoreCriterias (StrategyCriteria ... s) {
		this.otherCriterias = s;
	}

	public void setPauseTraces(int pauseAfterTraces) {
		this.pauseTraces = pauseAfterTraces;		
	}
	
}
