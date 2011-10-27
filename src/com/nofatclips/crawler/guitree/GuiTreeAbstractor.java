package com.nofatclips.crawler.guitree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AbsSpinner;
import android.widget.TabHost;

import com.nofatclips.androidtesting.guitree.*;
import com.nofatclips.androidtesting.model.*;
import com.nofatclips.crawler.model.*;

public class GuiTreeAbstractor implements Abstractor, FilterHandler {

	private GuiTree theSession;
	private StartActivity baseActivity;
	private HashSet<Filter> filters;
	private int eventId=0;
	private int inputId=0;
	private int activityId=0;
	private TypeDetector detector;

	public GuiTreeAbstractor () throws ParserConfigurationException {
		this (new GuiTree());
	}
	
	public GuiTreeAbstractor(GuiTree s) {
		super();
		this.filters = new HashSet<Filter>();
		setTheSession(s);
	}

	public GuiTree getTheSession() {
		return this.theSession;
	}

	public void setTheSession(GuiTree theSession) {
		this.theSession = theSession;
	}
	
	public TypeDetector getTypeDetector () {
		return this.detector;
	}
	
	public void setTypeDetector (TypeDetector t) {
		this.detector = t;
	}

	@Override
	public ActivityState createActivity (ActivityDescription desc) {
		return createActivity (desc,false);
	}
	
	// If the boolean parameter is omitted, the overloading method will default to a Final Activity
	public ActivityState createActivity (ActivityDescription desc, boolean start) {
		ActivityState newActivity = (start)?StartActivity.createActivity(getTheSession()):FinalActivity.createActivity(getTheSession());
		newActivity.setName(desc.getActivityName());
		newActivity.setId(getUniqueActivityId());
		for (Filter f: this.filters) {
			f.clear();
		}
		boolean hasDescription = updateDescription(newActivity, desc, false);
		if (!hasDescription) newActivity.setId("exit");
		return newActivity;
	}

	@Override
	public boolean updateDescription (ActivityState newActivity, ActivityDescription desc) {
		return updateDescription  (newActivity, desc, true);
	}

	public boolean updateDescription (ActivityState newActivity, ActivityDescription desc, boolean detectDuplicates) {
		boolean hasDescription = false;
		for (View v: desc) {
			hasDescription = true;
			if (!v.isShown()) continue;
			TestCaseWidget w = TestCaseWidget.createWidget(getTheSession());
			String id = String.valueOf(v.getId());
			String text = "";
			if (v instanceof TextView) {
				TextView t = (TextView)v;
				int type = t.getInputType();
				if (type!=0) {
					w.setTextType("" + type);
				}
				text = t.getText().toString();
			}
			w.setIdNameType(id, text, v.getClass().getName());
			w.setSimpleType(getTypeDetector().getSimpleType(v));
			setCount (v,w);
			w.setAvailable((v.isEnabled())?"true":"false");
			w.setClickable((v.isClickable())?"true":"false");
			w.setIndex(desc.getWidgetIndex(v));
			if (detectDuplicates && newActivity.hasWidget(w)) continue;
			newActivity.addWidget(w);
			for (Filter f: this.filters) {
				f.loadItem(v, w);
			}
		}
		return hasDescription;
	}
	
	@SuppressWarnings("rawtypes")
	private void setCount (View v, WidgetState w) {
		if (v instanceof AdapterView) {
			w.setCount(((AdapterView)v).getCount());
			return;
		}
		if (v instanceof AbsSpinner) {
			w.setCount(((AbsSpinner)v).getCount());
			return;
		}
		if (v instanceof TabHost) {
			w.setCount(((TabHost)v).getTabWidget().getTabCount());
			return;
		}
		if (v instanceof ViewGroup) {
			w.setCount(((ViewGroup)v).getChildCount());
		}
	}
	
	public void setBaseActivity (ActivityDescription desc) {
		this.baseActivity = (StartActivity) createActivity(desc,true);
	}
	
	public ActivityState getBaseActivity () {
		return this.baseActivity;
	}

	@Override
	public Iterator<Filter> iterator() {
		return this.filters.iterator();
	}

	@Override
	public void addFilter(Filter f) {
		this.filters.add(f);
	}

	@Override
	public UserEvent createEvent(WidgetState target, String type) {
		TestCaseEvent newEvent = TestCaseEvent.createEvent(getTheSession());
		if (target == null) {
			target = TestCaseWidget.createWidget(getTheSession());
			target.setType("null");
			target.setId("-1");
			target.setSimpleType("null");
			newEvent.setWidget (target);
		} else {
			newEvent.setWidget (target.clone());
		}
		newEvent.setType(type);
		newEvent.setId(getUniqueEventId());
		return newEvent;
	}

	@Override
	public UserInput createInput(WidgetState target, String text, String type) {
		TestCaseInput newInput = TestCaseInput.createInput(getTheSession());
		newInput.setWidget(target);
		newInput.setValue(text);
		newInput.setType(type);
		newInput.setId(getUniqueInputId());
		return newInput;
	}

	@Override
	public Trace createTrace(Trace head, Transition tail) {
		TestCaseTrace t;
		if (head!= null) {
			t = ((TestCaseTrace)head).clone();
		} else {
			t = new TestCaseTrace (getTheSession());
		}
		t.addTransition(tail);
		return t;
	}
	
	public Transition createStep (ActivityState start, Collection<UserInput> inputs, UserEvent event) {
		Transition t = TestCaseTransition.createTransition(start.getElement().getOwnerDocument());
		try {
			t.setStartActivity(StartActivity.createActivity(start));
			for (UserInput inPut: inputs) {
				t.addInput(inPut);
			}
			t.setEvent (event);
		}
		catch (DOMException e) {
			Log.i("nofatclips", "Abstractor->createStep(activity): " + ((e.code==DOMException.HIERARCHY_REQUEST_ERR)?"HIERARCHY_REQUEST_ERR":String.valueOf(e.code)));
		}
		return t;
	}
	
	public String getUniqueEventId () {
		this.eventId++;
		return "e" + this.eventId;
	}

	public String getUniqueActivityId () {
		this.activityId++;
		return "a" + this.activityId;
	}

	public String getUniqueInputId () {
		this.inputId++;
		return "i" + this.inputId;
	}

}