package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.Collection;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.crawler.model.Plan;
import com.nofatclips.crawler.model.Planner;
import com.nofatclips.crawler.model.StrategyPlanner;


public class SimpleStrategyPlanner implements StrategyPlanner{
	
	public SimpleStrategyPlanner() {
		super();
	}

	public Plan getPlanForActivity (ActivityState a) {		
		Plan AllPlan=new Plan();
		for (Planner p: this.planners) {
			Plan temp=p.getPlanForActivity(a);			
			for (Transition t: temp) {
				AllPlan.addTask(t);
			}
		}
		return AllPlan;
	}
	
	public Plan getPlanForBaseActivity (ActivityState a) {
		Plan AllPlan=new Plan();
		for (Planner p: this.planners) {
			Plan temp=p.getPlanForBaseActivity(a);			
			for (Transition t: temp) {
				AllPlan.addTask(t);
			}
		}
		return AllPlan;
	}

	public void addPlanner (Planner p) {
		this.planners.add(p);
	}

	private Collection<Planner> planners = new ArrayList<Planner>();
}
