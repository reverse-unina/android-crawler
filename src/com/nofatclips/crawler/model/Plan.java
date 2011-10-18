package com.nofatclips.crawler.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.nofatclips.androidtesting.model.Transition;

public class Plan implements Iterable<Transition> {

	private Set<Transition> tasks = new HashSet<Transition> ();

	@Override
	public Iterator<Transition> iterator() {
		return this.tasks.iterator();
	}
	
	public boolean addTask (Transition t) {
		return this.tasks.add(t);
	}
	
}
