package com.nofatclips.crawler.model;

import android.app.Activity;

import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.androidtesting.model.Trace;

public interface Persistence {
	
	public void save ();
	public void setFileName(String name);
	public void setSession (Session s);
	public void addTrace (Trace t);
	public void setContext(Activity activity); // Set the context in which the disk operations will be performed
	public boolean exists (String fileName);
	public void registerListener (SaveStateListener listener);

}
