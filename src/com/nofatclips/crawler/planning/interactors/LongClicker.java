package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.LONG_CLICK;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;

public class LongClicker extends InteractorAdapter {
	
	public LongClicker (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public LongClicker (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public boolean canUseWidget (WidgetState w) {
		return (w.isLongClickable() && super.canUseWidget(w));
	}

	@Override
	public String getInteractionType () {
		return LONG_CLICK;
	}

}
