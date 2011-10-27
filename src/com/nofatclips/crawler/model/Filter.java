package com.nofatclips.crawler.model;

import android.view.View;

import com.nofatclips.androidtesting.model.WidgetState;

public interface Filter extends Iterable<WidgetState> {
	
	public void loadItem(View v, WidgetState w);
	public void clear ();
	public boolean isValidItem (View v);
	public int numWidgets();
}
