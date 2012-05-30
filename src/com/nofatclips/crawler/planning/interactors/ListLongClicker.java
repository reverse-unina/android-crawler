package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.LIST_LONG_SELECT;
import com.nofatclips.crawler.model.Abstractor;

public class ListLongClicker extends ListSelector {
	
	public ListLongClicker () {
		super ();
	}
	
	public ListLongClicker (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public ListLongClicker (int maxItems) {
		super (maxItems);
	}
	
	public ListLongClicker (Abstractor theAbstractor) {
		super (theAbstractor);
	}
	
	public ListLongClicker (int maxItems, String ... simpleTypes) {
		super (maxItems, simpleTypes);
	}
	
	public ListLongClicker (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}
	
	public ListLongClicker (Abstractor theAbstractor, int maxItems) {
		super (theAbstractor, maxItems);
	}
	
	public ListLongClicker (Abstractor theAbstractor, int maxItems, String ... simpleTypes) {
		super (theAbstractor, maxItems, simpleTypes);
	}

	@Override
	public String getInteractionType () {
		return LIST_LONG_SELECT;
	}

}
