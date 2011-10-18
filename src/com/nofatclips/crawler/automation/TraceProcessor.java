package com.nofatclips.crawler.automation;

import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.crawler.model.Robot;
import com.nofatclips.crawler.model.TaskProcessor;

// Not Used

public class TraceProcessor implements TaskProcessor {
	
	private Robot robot;
	
	public TraceProcessor () {
	}
	
	public TraceProcessor (Robot r) {
		setRobot (r);
	}
	
	@Override
	public void execute (Trace trace) {
		// For each transition t in the Task, call the method to execute it
		for (Transition t: trace) {
			execute (t);
		}
	}

	// For the given transition, just set the inputs and fire the events
	private void execute(Transition t) {
		for (UserInput i: t) {
			this.robot.setInput(i);
		}
		this.robot.fireEvent(t.getEvent());
	}
	
	public void setRobot (Robot r) {
		this.robot = r;
	}

}
