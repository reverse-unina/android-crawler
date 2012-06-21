package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.LIST_SELECT;
import static com.nofatclips.androidtesting.model.SimpleType.LIST_VIEW;
import static com.nofatclips.androidtesting.model.SimpleType.PREFERENCE_LIST;
import static com.nofatclips.crawler.Resources.ALL_EVENTS_ON_PREFERENCES;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.IterativeInteractorAdapter;

public class ListSelector extends IterativeInteractorAdapter {
	
	public ListSelector () {
		this (LIST_VIEW);
	}
	
	public ListSelector (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public ListSelector (int maxItems) {
		this(maxItems, LIST_VIEW);
	}
	
	public ListSelector (Abstractor theAbstractor) {
		this (theAbstractor, LIST_VIEW);
	}

	public ListSelector (int maxItems, String ... simpleTypes) {
		super (maxItems, simpleTypes);
	}

	public ListSelector (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public ListSelector (Abstractor theAbstractor, int maxItems) {
		this (theAbstractor, maxItems, LIST_VIEW);
	}

	public ListSelector (Abstractor theAbstractor, int maxItems, String ... simpleTypes) {
		super (theAbstractor, maxItems, simpleTypes);
	}

	@Override
	public boolean cannotIdentifyWidget (WidgetState w) {
		return ( (w.getId().equals("-1"))  && (!doEventWhenNoId()) );
	}
	
	@Override
	public int getToItem (WidgetState w, int fromItem, int toItem) {
		if (w.getSimpleType().equals(PREFERENCE_LIST) && ALL_EVENTS_ON_PREFERENCES) {
			return toItem;
		}
		return super.getToItem (w,fromItem,toItem);
	}

	public String getInteractionType () {
		return LIST_SELECT;
	}

}