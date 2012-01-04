package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.SET_BAR;
import static com.nofatclips.androidtesting.model.SimpleType.SEEK_BAR;
import static com.nofatclips.androidtesting.model.SimpleType.RATING_BAR;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.RandomInteractorAdapter;

public class Slider extends RandomInteractorAdapter {

	public Slider () {
		this (SEEK_BAR, RATING_BAR);
	}
	
	public Slider (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public Slider (Abstractor theAbstractor) {
		this();
		setAbstractor(theAbstractor);
	}
	
	public Slider (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public Slider (Abstractor theAbstractor, int minValue) {
		this (theAbstractor);
		setMin(minValue);
	}

	public Slider (Abstractor theAbstractor, int minValue, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
		setMin(minValue);
	}

	@Override
	public String getInteractionType() {
		return SET_BAR;
	}
	
	public int getMin () {
		return 0;
	}
	
	public int getMax(WidgetState w) {
		return w.getCount();
	}

}
