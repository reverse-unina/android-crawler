package com.nofatclips.crawler.filters;


import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FormFilter extends ArrayListFilter {

	@Override
	public boolean isValidItem(View v) {
		return ((v instanceof CompoundButton) || (v instanceof TextView));
	}

}
