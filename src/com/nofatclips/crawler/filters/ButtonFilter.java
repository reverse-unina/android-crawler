package com.nofatclips.crawler.filters;


import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class ButtonFilter extends ListFilter {

	@Override
	public boolean isValidItem(View v) {
		return ( ((v instanceof Button) && (!(v instanceof CompoundButton))) || (v instanceof ImageButton) );
	}

}
