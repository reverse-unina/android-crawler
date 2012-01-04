package com.nofatclips.crawler.model;

import android.app.Activity;
import android.view.View;

public interface Extractor {
	
	public ActivityDescription describeActivity();
	public Activity getActivity();
	public void extractState();
	public View getWidget (int key);

}
