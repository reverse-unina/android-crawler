package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.UserAdapter;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;
import com.nofatclips.crawler.planning.adapters.RandomInteractor;

public class SimpleUser implements UserAdapter {

	public SimpleUser () {
		this(new Random());
	}
	
	public SimpleUser (Abstractor a) {
		this();
		setAbstractor(a);
	}
	
	public SimpleUser (Random r) {
		this.eventTypes = new ArrayList<InteractorAdapter>();
		this.inputTypes = new ArrayList<InteractorAdapter>();		
		setRandomGenerator(r);		
	}
	
	public SimpleUser (Abstractor a, Random r) {
		this (r);
		setAbstractor(a);
	}
	
	@Override
	public List<UserEvent> handleEvent(WidgetState w) {
		ArrayList<UserEvent> events = new ArrayList<UserEvent>();
		for (InteractorAdapter eventAdapter: getEventTypes()) {
			events.addAll(eventAdapter.getEvents(w));
		}
		return events;
	}
	
	@Override
	public List<UserInput> handleInput(WidgetState w) {
		ArrayList<UserInput> inputs = new ArrayList<UserInput>();
		for (InteractorAdapter inputAdapter: getInputTypes()) {
			inputs.addAll(inputAdapter.getInputs(w));
		}
		return inputs;
	}
	
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
	
	public void addEvent (InteractorAdapter ... events) {
		for (InteractorAdapter e: events) {
			e.setAbstractor(getAbstractor());
			eventTypes.add(e);
		}
	}
	
	public Iterable<InteractorAdapter> getEventTypes () {
		return this.eventTypes;
	}

	public void addInput (InteractorAdapter ... inputs) {
		for (InteractorAdapter i: inputs) {
			i.setAbstractor(getAbstractor());
			if (i instanceof RandomInteractor) {
				((RandomInteractor) i).setRandomGenerator(getRandomGenerator());
			}
			inputTypes.add(i);
		}
	}
	
	public Iterable<InteractorAdapter> getInputTypes () {
		return this.inputTypes;
	}

	private Abstractor abs;
	private Random randomGenerator;
	private List<InteractorAdapter> eventTypes;
	private List<InteractorAdapter> inputTypes;

}
