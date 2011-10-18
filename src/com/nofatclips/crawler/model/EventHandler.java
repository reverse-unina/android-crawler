package com.nofatclips.crawler.model;

import java.util.Collection;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.WidgetState;

public interface EventHandler {
	
	public Collection<UserEvent> handleEvent (WidgetState w);

}
