package com.nofatclips.crawler.model;

import java.util.Collection;
import java.util.List;

import com.nofatclips.androidtesting.model.Trace;

// The TaskScheduler is queried for the next task to execute every time the application is restarted.
// Implementations should provide with the politics by which such task is chosen among the list of
// planned tasks (e.g. breadth first, depth first, ...) as well as a termination criteria (e.g. the
// list is empty, time limit exceeded, max number of tasks exceeded, ...)

public interface TaskScheduler {
	
	public Trace nextTask();
	public void addTasks (Collection<Trace> newTasks);
	public void setTaskList (List<Trace> theList);
	public boolean hasMore();
	public void remove (Trace t);
	public void addTasks(Trace t);

}
