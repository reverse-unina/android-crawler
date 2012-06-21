package com.nofatclips.crawler.strategy.comparator;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Comparator;
import static com.nofatclips.androidtesting.model.SimpleType.NULL;
import static com.nofatclips.crawler.Resources.COMPARE_STATE_TITLE;

public class NameComparator implements Comparator {

	public NameComparator () {
		this (true);
	};

	public NameComparator (boolean byName) {
		Log.d("nofatclips", "Comparation by name is " + ((byName)?"enabled":"disabled"));
		this.byName = byName;
		Log.d("nofatclips", "Comparation by title is " + ((compareByTitle())?"enabled":"disabled"));
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
	
	public boolean compareByTitle () {
		return COMPARE_STATE_TITLE;
	}
	
	protected boolean byName;
	protected boolean byTitle;

}
