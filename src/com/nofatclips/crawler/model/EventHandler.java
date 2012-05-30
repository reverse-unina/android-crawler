package com.nofatclips.crawler.model;

import java.util.List;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.WidgetState;

public interface EventHandler {
	
	public List<UserEvent> handleEvent (WidgetState w);

}
