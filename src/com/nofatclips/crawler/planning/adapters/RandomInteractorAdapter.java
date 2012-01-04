package com.nofatclips.crawler.planning.adapters;

import java.util.List;
import java.util.Random;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;

public abstract class RandomInteractorAdapter extends InteractorAdapter implements RandomInteractor {
	
	private Random random;
	private int min=0;
	private int max=100;

	public RandomInteractorAdapter (String ... simpleTypes) {
		super (simpleTypes);
	}

	public RandomInteractorAdapter (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	@Override
	public void setRandomGenerator (Random r) {
		this.random = r;
	}
	
	public Random getRandomGenerator () {
		return this.random;
	}

	public void setMin (int min) {
		this.min = min;
	}

	public void setMax (int max) {
		this.max = max;
	}
	
	public void setMinMax (int minValue, int maxValue) {
		if (minValue>maxValue) {
			setMinMax(maxValue, minValue);
		}
		setMin(minValue);
		setMax(maxValue);
	}

	public int getMax() {
		return this.max;
	}
	
	public int getMin() {
		return this.min;
	}

	public int getMax(WidgetState w) {
		return getMax();
	}
	
	public int getMin(WidgetState w) {
		return getMin();
	}

	public int getValue() {
		return getRandomGenerator().nextInt(getMax()-getMin()) + getMin();
	}
	
	public int getValue (WidgetState w) {
		return getRandomGenerator().nextInt(getMax(w)-getMin(w)) + getMin(w);
	}

	@Override
	public List<UserEvent> getEvents (WidgetState w) {
		return getEvents (w, String.valueOf(getValue(w)));
	}

	@Override
	public List<UserInput> getInputs (WidgetState w) {
		return getInputs (w, String.valueOf(getValue(w)));
	}
	
}