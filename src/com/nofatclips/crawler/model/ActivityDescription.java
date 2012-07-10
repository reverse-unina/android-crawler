package com.nofatclips.crawler.model;

import android.view.View;

public interface ActivityDescription extends Iterable<View> {
	
	public String getActivityName();
	public String getActivityTitle();
	public int getWidgetIndex (View v);

/** @author nicola amatucci */
	public boolean usesSensorsManager();
	public boolean usesLocationManager();
	public boolean hasMenu();
	public boolean hasOnOptionsItemSelected();
	public boolean handlesKeyPress();
	public boolean handlesLongKeyPress();
	public boolean isTabActivity();
/** @author nicola amatucci */
}
