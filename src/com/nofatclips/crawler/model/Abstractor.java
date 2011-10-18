package com.nofatclips.crawler.model;

import java.util.Collection;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;

public interface Abstractor {

	public ActivityState createActivity (ActivityDescription desc);
	public void setBaseActivity (ActivityDescription desc);
	public ActivityState getBaseActivity ();
	
	public UserEvent createEvent (WidgetState target, String type);
	public UserInput createInput (WidgetState target, String value, String type);
	public Trace createTrace (Trace prototype, Transition appendix);
	public Transition createStep (ActivityState start, Collection<UserInput> inputs, UserEvent event);
	
}
