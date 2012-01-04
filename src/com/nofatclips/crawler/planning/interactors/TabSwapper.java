package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.SWAP_TAB;
import static com.nofatclips.androidtesting.model.SimpleType.TAB_HOST;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.IterativeInteractorAdapter;

public class TabSwapper extends IterativeInteractorAdapter {
	
	private boolean onlyOnce = false;
	private boolean first = true;

	public TabSwapper () {
		this (TAB_HOST);
	}
	
	public TabSwapper (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public TabSwapper (int maxItems) {
		this(maxItems, TAB_HOST);
	}
	
	public TabSwapper (Abstractor theAbstractor) {
		this (theAbstractor, TAB_HOST);
	}

	public TabSwapper (int maxItems, String ... simpleTypes) {
		super (maxItems, simpleTypes);
	}

	public TabSwapper (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public TabSwapper (Abstractor theAbstractor, int maxItems) {
		this (theAbstractor, maxItems, TAB_HOST);
	}

	public TabSwapper (Abstractor theAbstractor, int maxItems, String ... simpleTypes) {
		super (theAbstractor, maxItems, simpleTypes);
	}

	@Override
	public String getInteractionType () {
		this.first = false;
		return SWAP_TAB;
	}
	
	public boolean doOnlyOnce() {
		return this.onlyOnce;
	}
	
	public void setOnlyOnce (boolean onlyOnce) {
		this.onlyOnce = onlyOnce;
	}

	@Override
	public boolean canUseWidget(WidgetState w) {
		return super.canUseWidget(w) && (isFirst() || !doOnlyOnce());
	}

	private boolean isFirst() {
		return this.first;
	}

}