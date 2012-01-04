package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.ActivityState;

public interface Planner {
	
	public Plan getPlanForActivity (ActivityState a);
	public Plan getPlanForBaseActivity (ActivityState a);
	public void setInputFilter(Filter inputFilter);
	public void setEventFilter(Filter eventFilter);
	public void setUser(EventHandler user);
	public void setFormFiller(InputHandler user);
	public void setAbstractor(Abstractor abstractor);
}
