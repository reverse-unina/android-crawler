package com.nofatclips.crawler.strategy.comparator;

import com.nofatclips.androidtesting.model.WidgetState;

public class WPCustomWidgetsDeepComparator extends WPCustomWidgetsComparator {
	
	public WPCustomWidgetsDeepComparator (String... widgets) {
		super (widgets);
	}
	
	public WPCustomWidgetsDeepComparator (boolean ignore, String... widgets) {
		super(ignore, widgets);
	}

	@Override
	protected boolean matchWidget (WidgetState campo, WidgetState altroCampo) {
		return (super.matchWidget(campo, altroCampo) && altroCampo.getName().equals(campo.getName()));
	}
	
}