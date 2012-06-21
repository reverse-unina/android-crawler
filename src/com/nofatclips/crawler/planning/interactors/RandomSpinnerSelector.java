package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.SPINNER_SELECT;
import static com.nofatclips.androidtesting.model.SimpleType.SPINNER;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.RandomInteractorAdapter;

public class RandomSpinnerSelector extends RandomInteractorAdapter {

	public RandomSpinnerSelector () {
		this (SPINNER);
	}
	
	public RandomSpinnerSelector (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public RandomSpinnerSelector (Abstractor theAbstractor) {
		this ();
		setAbstractor(theAbstractor);
	}
	
	public RandomSpinnerSelector (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public RandomSpinnerSelector (Abstractor theAbstractor, int minValue) {
		this (theAbstractor);
		setMin(minValue);
	}

	public RandomSpinnerSelector (Abstractor theAbstractor, int minValue, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
		setMin(minValue);
	}

	public RandomSpinnerSelector (Abstractor theAbstractor, int minValue, int maxValue) {
		this (theAbstractor);
		setMin(minValue);
		setMax(maxValue);
	}

	public RandomSpinnerSelector (Abstractor theAbstractor, int minValue, int maxValue, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
		setMin(minValue);
		setMax(maxValue);
	}
	
	public String getInteractionType() {
		return SPINNER_SELECT;
	}

	@Override
	public boolean canUseWidget (WidgetState w) {
//		Log.e("nofatclips","canUseWidget: #" + w.getId() + " (" + w.getSimpleType() + ")");
		if (getMin(w)>getMax(w)) return false;
		return super.canUseWidget(w);
	}
	
	@Override
	public int getMin () {
		return 1;
	}
	
	@Override
	public int getMax(WidgetState w) {
//		Log.e("nofatclips","getMax: #" + w.getId() + ": " + w.getCount());
		return w.getCount();
	}

}
