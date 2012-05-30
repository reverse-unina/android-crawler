package com.nofatclips.crawler.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.nofatclips.androidtesting.model.Transition;

public class Plan implements Iterable<Transition> {

	private List<Transition> tasks = new ArrayList<Transition> ();

	@Override
	public Iterator<Transition> iterator() {
		return this.tasks.iterator();
	}
	
	public boolean addTask (Transition t) {
		return this.tasks.add(t);
	}
	
}
