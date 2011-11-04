package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.crawler.model.TaskScheduler;

public class TraceDispatcher implements Iterable<Trace> {

	private TaskScheduler scheduler;
	
	public TraceDispatcher () {
		setScheduler(getTrivialScheduler());
	}

	public TraceDispatcher(TaskScheduler taskScheduler) {
		setScheduler(taskScheduler);
	}
	
	public void setScheduler (TaskScheduler ts) {
		this.scheduler = ts;
	}
	
	public void addTasks (Collection<Trace> t) {
		this.scheduler.addTasks(t);
	}
	
	public void addTasks (Trace t) {
		this.scheduler.addTasks(t);
	}
	
	public TaskScheduler getTrivialScheduler() {
		TaskScheduler s = new TrivialScheduler();
		s.setTaskList(new ArrayList<Trace>());
		return s;
	}

	@Override
	public Iterator<Trace> iterator() {
		return new Iterator<Trace> () {
			
			Trace lastTask;

			@Override
			public boolean hasNext() {
				return scheduler.hasMore();
			}

			@Override
			public Trace next() {
				this.lastTask = scheduler.nextTask();
				remove();
				return this.lastTask;
			}

			@Override
			public void remove() {
				scheduler.remove(this.lastTask);
			}
			
		};
	}

	private class TrivialScheduler implements TaskScheduler {
		
		private List<Trace> tasks;
		
		public TrivialScheduler () {
		}
		
		@Override
		public Trace nextTask() {
			Log.i("nofatclips", "Dispatching new task. " + tasks.size() + " more tasks remaining.");
			return (hasMore())?tasks.get(0):null;
		}

		@Override
		public void addTasks(Collection<Trace> newTasks) {
			for (Trace t: newTasks) {
				tasks.add(t);
			}				
		}

		@Override
		public void setTaskList(List<Trace> theList) {
			this.tasks = theList;
		}

		@Override
		public boolean hasMore() {
			return (!tasks.isEmpty());
		}

		@Override
		public void remove(Trace t) {
			tasks.remove(t);
		}

		@Override
		public void addTasks(Trace t) {
			this.tasks.add(t);
		}
	}

}
