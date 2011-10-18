package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.androidtesting.model.Trace;

public interface Persistence {
	
	public void save ();
	public void setFileName(String name);
	public void setSession (Session s);
	public void addTrace (Trace t);

}
