package com.nofatclips.crawler.model;

import com.nofatclips.crawler.planning.adapters.InteractorAdapter;

public interface UserAdapter extends EventHandler, InputHandler {
	
	public void addInput (InteractorAdapter ... inputs);
	public void addEvent (InteractorAdapter ... events);
	
}