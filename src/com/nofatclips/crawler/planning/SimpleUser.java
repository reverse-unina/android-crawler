package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.util.Log;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.EventHandler;
import com.nofatclips.crawler.model.InputHandler;

import static com.nofatclips.crawler.Resources.*;
import static com.nofatclips.androidtesting.model.InteractionType.*;
import static com.nofatclips.androidtesting.model.SimpleType.*;

public class SimpleUser implements EventHandler, InputHandler {

	public SimpleUser () {
		super();
		this.randomGenerator = new Random();
	}
	
	public SimpleUser (Abstractor a) {
		this();
		setAbstractor(a);
	}
	
	@SuppressWarnings("unused")
	@Override
	public Collection<UserEvent> handleEvent(WidgetState w) {
		ArrayList<UserEvent> events = new ArrayList<UserEvent>();
		if (!useForEvent(w)) return events;
		if ( (w.getId().equals("-1"))  && (!EVENT_WHEN_NO_ID || (w.getName().equals("")) )) return events;
		Log.d("nofatclips", "Handling event on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
		UserEvent event = getAbstractor().createEvent(w, CLICK);
		events.add(event);
		return events;
	}
	
	@Override
	public UserInput handleInput(WidgetState w) {
		if (!useForInput(w)) return null;
		Log.d("nofatclips", "Handling input on widget id=" + w.getId() + " type=" + w.getSimpleType());
		if (w.getSimpleType().equals(CHECKBOX)) {
//			if (randomGenerator.nextBoolean()) return null;
			UserInput input = getAbstractor().createInput(w, "", CLICK);
			return input;
		} else if (w.getSimpleType().equals("editText")) {
			int randomInt = randomGenerator.nextInt(this.upperLimit-this.lowerLimit) + this.lowerLimit;  
			UserInput input = getAbstractor().createInput(w, String.valueOf(randomInt), TYPE_TEXT);
			return input;
		}
		return null;
	}

	protected boolean useForEvent (WidgetState w) {
		return (w.getSimpleType().equals(BUTTON));
	}

	protected boolean useForInput (WidgetState w) {
		if (useForEvent(w)) return false;
		return ( (w.getSimpleType().equals(EDIT_TEXT) || w.getSimpleType().equals(RADIO) || w.getSimpleType().equals(CHECKBOX)) && !w.getId().equals("-1"));
	}
	
//	protected boolean useForSwapTabs (WidgetState w) {
//		return w.getSimpleType().equals("tabHost");
//	}

	public Abstractor getAbstractor() {
		return this.abs;
	}

	public void setAbstractor(Abstractor abs) {
		this.abs = abs;
	}

	public Random getRandomGenerator() {
		return this.randomGenerator;
	}

	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
	
	public void setLimits (int lower, int upper) {
		this.lowerLimit = lower;
		this.upperLimit = upper;
	}
	
	private Abstractor abs;
	private Random randomGenerator;
	private int lowerLimit = 0;
	private int upperLimit = 100;

}
