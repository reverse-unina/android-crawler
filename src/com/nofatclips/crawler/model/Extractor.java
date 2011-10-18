package com.nofatclips.crawler.model;


import android.view.View;

public interface Extractor {
	
	public ActivityDescription describeActivity();
	public void extractState();
	public View getWidget (int key);

}
