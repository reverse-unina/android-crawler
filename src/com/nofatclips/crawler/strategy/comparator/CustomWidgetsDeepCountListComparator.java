package com.nofatclips.crawler.strategy.comparator;

import com.nofatclips.androidtesting.model.WidgetState;

public class CustomWidgetsDeepCountListComparator extends CustomWidgetsDeepComparator {
 	
	public CustomWidgetsDeepCountListComparator (String... widgets) {
		super (widgets);
	}
	
	public CustomWidgetsDeepCountListComparator (boolean ignore, String... widgets) {
		super(ignore, widgets);
	}

	@Override
	protected boolean matchWidget (WidgetState campo, WidgetState altroCampo) {		
		return (campo.getSimpleType().equals("listView"))?
			(super.matchWidget(campo, altroCampo)&&(altroCampo.getCount()==campo.getCount())):
			(super.matchWidget(campo, altroCampo));
	}

}