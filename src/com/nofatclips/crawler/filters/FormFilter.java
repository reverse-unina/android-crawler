package com.nofatclips.crawler.filters;

import android.view.View;
import android.widget.*;

public class FormFilter extends ArrayListFilter {

	@Override
	public boolean isValidItem(View v) {
		return ((v instanceof CompoundButton) || (v instanceof TextView) || (v instanceof ProgressBar) || (v instanceof Spinner));
	}

}
