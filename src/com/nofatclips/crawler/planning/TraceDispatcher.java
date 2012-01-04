package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.crawler.model.DispatchListener;
import com.nofatclips.crawler.model.TaskScheduler;

public class TraceDispatcher implements Iterable<Trace> {

	private TaskScheduler scheduler;
	private List<DispatchListener> theListeners = new ArrayList<DispatchListener>();
	
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
		getScheduler().addTasks(t);
	}
	
	public void addTasks (Trace t) {
		getScheduler().addTasks(t);
	}
	
	public TaskScheduler getTrivialScheduler() {
		TaskScheduler s = new TrivialScheduler();
		s.setTaskList(new ArrayList<Trace>());
		return s;
	}
	
	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void registerListener(DispatchListener theListener) {
//		throw new Error();
		this.theListeners.add(theListener);
	}

	public Iterator<Trace> iterator() {
		return new Iterator<Trace> () {
			
			Trace lastTask;

			public boolean hasNext() {
				return scheduler.hasMore();
			}

			public Trace next() {
				this.lastTask = scheduler.nextTask();
				for (DispatchListener theListener: theListeners) {
					theListener.onTaskDispatched(this.lastTask);
				}
				remove();
				return this.lastTask;
			}

			public void remove() {
				scheduler.remove(this.lastTask);
			}
			
		};
	}
	
	private class TrivialScheduler implements TaskScheduler {
		
		private List<Trace> tasks;
		
		public TrivialScheduler () {
		}
		
		public Trace nextTask() {
			Log.i("nofatclips", "Dispatching new task. " + tasks.size() + " more tasks remaining.");
			Trace t = (hasMore())?tasks.get(0):null;
			return t;
		}

		public void addTasks(Collection<Trace> newTasks) {
			for (Trace t: newTasks) {
				tasks.add(t);
				for (DispatchListener theListener: theListeners) {
					theListener.onNewTaskAdded(t);
				}
			}				
		}

		public void setTaskList(List<Trace> theList) {
			this.tasks = theList;
		}

		public List<Trace> getTaskList() {
			return this.tasks;
		}

		public boolean hasMore() {
			return (!tasks.isEmpty());
		}

		public void remove(Trace t) {
			tasks.remove(t);
		}

		public void addTasks(Trace t) {
			this.tasks.add(t);
		}
		
	}

}
