package com.nofatclips.crawler.planning.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;

public abstract class InteractorAdapter {
	
	protected HashSet<String> widgetClasses = new HashSet<String>();
	private Abstractor theAbstractor;
	private boolean eventWhenNoId = false;
	private List<String> vetoId;

	public InteractorAdapter (String ... simpleTypes) {
		for (String s: simpleTypes) {
			this.widgetClasses.add(s);
		}
	}
	
	public InteractorAdapter (Abstractor theAbstractor, String ... simpleTypes) {
		this (simpleTypes);
		setAbstractor (theAbstractor);
	}
	
	public boolean canUseWidget (WidgetState w) {
		if ( (w.getId().equals("-1"))  && (!doEventWhenNoId() || (w.getName().equals(""))) ) return false;
		for (String id: getVetoedIds()) {
			if (w.getId().equals(id)) {
				Log.d("nofatclips", "Event denied for widget #" + id);
				return false;
			}
		}
		return (w.isAvailable() && matchClass(w.getSimpleType()));
	}
	
	public List<UserEvent> getEvents (WidgetState w) {
		ArrayList<UserEvent> events = new ArrayList<UserEvent>();
		if (canUseWidget(w)) {
			Log.d("nofatclips", "Handling event '" + getInteractionType() + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			events.add(generateEvent(w));
		}
		return events;
	}

	public List<UserInput> getInputs (WidgetState w) {
		ArrayList<UserInput> inputs = new ArrayList<UserInput>();
		if (canUseWidget(w)) {
			Log.d("nofatclips", "Handling input '" + getInteractionType() + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			inputs.add(generateInput(w));
		}
		return inputs;
	}

	public List<UserEvent> getEvents (WidgetState w, String ... values) {
		ArrayList<UserEvent> events = new ArrayList<UserEvent>();
		if (canUseWidget(w)) {
			Log.d("nofatclips", "Handling event '" + getInteractionType() + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			for (String value: values) {
				Log.v ("nofatclips", "Using value: " + value);
				events.add(generateEvent(w, value));
			}
		}
		return events;
	}

	public List<UserInput> getInputs (WidgetState w, String ... values) {
		ArrayList<UserInput> inputs = new ArrayList<UserInput>();
		if (canUseWidget(w)) {
			Log.d("nofatclips", "Handling input '" + getInteractionType() + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			for (String value: values) {
				Log.v ("nofatclips", "Using value: " + value);
				inputs.add(generateInput(w, value));	
			}
		}
		return inputs;
	}

	protected UserEvent generateEvent (WidgetState w) {
		return getAbstractor().createEvent(w, getInteractionType());
	}

	protected UserInput generateInput (WidgetState w) {
		return generateInput (w,"");
	}

	protected UserEvent generateEvent (WidgetState w, String value) {
		UserEvent event = generateEvent(w);
		event.setValue(value);
		return event;
	}

	protected UserInput generateInput (WidgetState w, String value) {
		return getAbstractor().createInput(w, value, getInteractionType());
	}

	public Abstractor getAbstractor() {
		return this.theAbstractor;
	}
	
	public void setAbstractor (Abstractor a) {
		this.theAbstractor = a;
	}
	
	public abstract String getInteractionType ();
	
	protected boolean matchClass (String type) {
		for (String storedType: this.widgetClasses) {
			if (storedType.equals(type)) return true;
		}
		return false;
	}

	public boolean doEventWhenNoId() {
		return eventWhenNoId;
	}

	public void setEventWhenNoId(boolean eventWhenNoId) {
		this.eventWhenNoId = eventWhenNoId;
	}
	
	public List<String> getVetoedIds () {
		if (this.vetoId instanceof List) {
			return this.vetoId;
		}
		this.vetoId = new ArrayList<String>();
		return this.vetoId;
	}
	
	public void denyIds (String ... ids) {
		for (String id: ids) {
			getVetoedIds().add(id);
		}
	}

	public void denyIds (Collection<String> ids) {
		Log.v("nofatclips", "Added vetoes for " + this.toString());
		getVetoedIds().addAll(ids);
	}

}