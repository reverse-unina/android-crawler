package com.nofatclips.crawler.strategy.comparator;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Comparator;
import static com.nofatclips.androidtesting.model.SimpleType.NULL;
import static com.nofatclips.crawler.Resources.COMPARE_STATE_TITLE;

public class NameComparator implements Comparator {

	public NameComparator () {
		this.byName = true;
		this.byTitle = COMPARE_STATE_TITLE;
	};
	
	@Override
	public boolean compare(ActivityState a, ActivityState b) {
		if (!(this.byTitle || this.byName)) return true;
		if ((this.byTitle) && !(a.getTitle().equals(b.getTitle()))) return false;
		if ((this.byName) && !(a.getName().equals(b.getName()))) return false;
		return true;
	}
	
	public String describe() {
		return NULL;
	}
	
	private boolean byName;
	private boolean byTitle;

}
