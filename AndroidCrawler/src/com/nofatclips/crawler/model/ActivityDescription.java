package com.nofatclips.crawler.model;

import android.view.View;

public interface ActivityDescription extends Iterable<View> {
	
	public String getActivityName();
	public String getActivityTitle();
	public int getWidgetIndex (View v);

}
