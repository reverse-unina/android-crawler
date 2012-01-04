package com.nofatclips.crawler.filters;
import android.view.View;
import android.widget.Spinner;

public class SpinnerAndFormFilter extends FormFilter {

	@Override
	public boolean isValidItem(View v) {
		return (super.isValidItem(v) || (v instanceof Spinner));
	}

}