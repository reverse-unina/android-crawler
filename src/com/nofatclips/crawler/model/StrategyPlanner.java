package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Plan;
public interface StrategyPlanner {
	
	public void addPlanner (Planner thePlanner);
	public Plan getPlanForActivity (ActivityState a);
	public Plan getPlanForBaseActivity (ActivityState a);
	
}
