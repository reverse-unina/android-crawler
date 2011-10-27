package com.nofatclips.crawler.automation;

import static com.nofatclips.crawler.Resources.*;
import static com.nofatclips.androidtesting.model.InteractionType.*;
import static com.nofatclips.androidtesting.model.SimpleType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
//import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.nofatclips.androidtesting.model.*;
import com.nofatclips.crawler.model.*;

// Automation implements the methods to interact with the application via the Instrumentation (Robot)
// and to extract informations from it (Extractor); the Robotium framework is used where possible

public class Automation implements Robot, Extractor, TaskProcessor {
	
//	private Instrumentation inst;
	@SuppressWarnings("rawtypes")
	private ActivityInstrumentationTestCase2 test; // The test case used to crawl the application
	private Activity theActivity; // Current Activity
	private Map<Integer,View> theViews = new HashMap<Integer,View> (); // A list of widgets with an id
	private ArrayList<View> allViews = new ArrayList<View>(); // A list of all widgets
	private Solo solo; // Robotium
	private Extractor extractor;
	private Restarter restarter;
	private TabHost	tabs; // Reference to the TabHost widget if present
	private int tabNum; // Number of tabs used by the Activity
	private Robot theRobot;
	private UserEvent currentEvent;
		
	// A Trivial Extractor is provided if none is assigned
	public Automation () {
		setExtractor (new TrivialExtractor());
		setRobot (this);
	}

	public Automation (Extractor e) {
		setExtractor (e);
	}
	
	// Initializations
	@SuppressWarnings("rawtypes")
	public void bind (ActivityInstrumentationTestCase2 test) {
		this.test = test;
//		this.theActivity = this.test.getActivity();
		this.solo = new Solo (test.getInstrumentation(), test.getActivity());
		afterRestart();
		refreshCurrentActivity();
		Log.w ("nofatclips","--->" + theActivity.getLocalClassName());
	}
	
	@Override
	public void execute (Trace t) {
		this.theRobot.process (t);
	}
	
	@Override
	public void process (Trace t) {
		Log.i ("nofatclips", "Restarting");
		this.restarter.restart();
		afterRestart();
		extractState();
		Log.i ("nofatclips", "Playing Trace " + t.getId());
		for (Transition step: t) {
			for (UserInput i: step) {
				setInput(i);
			}
			fireEvent (step.getEvent());
		}
	}

	@Override
	public void finalize() {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		theActivity.finish();
	}

	@Override
	public void fireEvent(UserEvent e) {
		this.currentEvent = e;
		String eventType = e.getType();
		if (eventType.equals(BACK) || eventType.equals(SCROLL_DOWN)) { // Special events
			Log.d("nofatclips", "Firing event: type= " + eventType);
			fireEventOnView(null, eventType, null);
		} else {
			View v = null;
			if (e.getWidget().getIndex()<getAllWidgets().size()) {
				v = getAllWidgets().get(e.getWidget().getIndex()); // Search widget by index
			}
			if ((v!=null) && checkWidgetEquivalence(v, Integer.parseInt(e.getWidgetId()), e.getWidgetType(), e.getWidgetName())) { // Widget found
				Log.d("nofatclips", "Firing event: type= " + eventType + " index=" + e.getWidget().getIndex() + " widget="+ e.getWidgetType());
				fireEventOnView (v, eventType, e.getValue());
			} else if (e.getWidgetId().equals("-1")) { // Widget not found. Search widget by name
				Log.d("nofatclips", "Firing event: type= " + eventType + " name=" + e.getWidgetName() + " widget="+ e.getWidgetType());
				fireEvent (e.getWidgetName(), e.getWidget().getSimpleType(), eventType, e.getValue());			
			} else { // Widget not found. Search widget by id
				Log.d("nofatclips", "Firing event: type= " + eventType + " id=" + e.getWidgetId() + " widget="+ e.getWidgetType());
				fireEvent (Integer.parseInt(e.getWidgetId()), e.getWidgetName(), e.getWidget().getSimpleType(), eventType, e.getValue());
			}
		}
	}

	@Override
	public void setInput(UserInput i) {
		Log.d("nofatclips", "Setting input: type= " + i.getType() + " id=" + i.getWidgetId() + " value="+ i.getValue());
		setInput (Integer.parseInt(i.getWidgetId()), i.getType(), i.getValue());
	}
	
	public void swapTab (String tab) {
		swapTab (this.tabs, Integer.valueOf(tab));
	}

	public void swapTab (int tab) {
		swapTab (this.tabs, tab);
	}
	
	private void fireEvent (int widgetId, String widgetName, String widgetType, String eventType, String value) {
		View v = getWidget(widgetId, widgetType, widgetName);
		if (v == null) {
			v = getWidget(widgetId);
		}
		if (v == null) {
			v = theActivity.findViewById(widgetId);
		}
		fireEventOnView(v, eventType, value);
	}

	private void fireEvent (String widgetName, String widgetType, String eventType, String value) {
		View v = null;
		if (widgetType.equals(BUTTON)) {
			v = solo.getButton(widgetName);
		}
		if (v == null) {
			for (View w: getAllWidgets()) {
				if (w instanceof Button) {
					Button candidate = (Button) w;
					if (candidate.getText().equals(widgetName)) {
						v = candidate;
					}
				}
				if (v!=null) break;
			}
		}
		fireEventOnView(v, eventType, value);
	}
	
	private void fireEventOnView (View v, String eventType, String value) {
		if (eventType == CLICK) {
			click (v);
		} else if (eventType == LONG_CLICK) {
			longClick(v);
		} else if (eventType == BACK) {
			solo.goBack();
		} else if (eventType == OPEN_MENU) {
			solo.sendKey(Solo.MENU);
		} else if (eventType == SCROLL_DOWN) {
			solo.scrollDown();
		} else if (eventType == SWAP_TAB && value!=null) {
			if (v instanceof TabHost) {
				swapTab ((TabHost)v, value);
			} else {
				swapTab (value);
			}
		} else if (eventType == LIST_SELECT) {
			selectListItem((ListView)v, value);
		} else if (eventType == LIST_LONG_SELECT) {
			selectListItem((ListView)v, value, true);
		} else {
			return;
		}
		solo.sleep(SLEEP_AFTER_EVENT);
		refreshCurrentActivity();
		extractState();
	}

	// Scroll the view to the top. Only works for ListView. Support for GridView and ScrollView must be added
	public void home () {
		final ArrayList<ListView> viewList = solo.getCurrentListViews();
		if (viewList.size() > 0) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					viewList.get(0).setSelection(0);
				}
			});
		}
	}
	
	private void refreshCurrentActivity() {
		this.theActivity = solo.getCurrentActivity();
		Log.i("nofatclips", "Current activity is " + getActivity().getLocalClassName());
	}

	private void setInput (int widgetId, String inputType, String value) {
		View v = getWidget(widgetId);
		if (v == null) {
			v = theActivity.findViewById(widgetId);
		}
		if (inputType == TYPE_TEXT) {
			solo.enterText((EditText)v, value);
		} else if (inputType == CLICK) {
			click (v);
		}
	}

	private void swapTab (TabHost t, String tab) {
		swapTab (t, Integer.valueOf(tab));
	}

	private void swapTab (final TabHost t, int num) {
		final int n = Math.min(this.tabNum, Math.max(1,num))-1;
		Log.i("nofatclips", "Swapping to tab " + num);
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				t.setCurrentTab(n);
			}
		});
		this.test.getInstrumentation().waitForIdleSync();
	}

	private void selectListItem (ListView l, String item) {
		selectListItem (l, item, false);
	}

	private void selectListItem (ListView l, String item, boolean longClick) {
		selectListItem (l, Integer.valueOf(item), longClick);
	}

	private void selectListItem (final ListView l, int num, boolean longClick) {
		final int n = Math.min(l.getCount(), Math.max(1,num))-1;
		l.requestFocus();
		Log.i("nofatclips", "Swapping to listview item " + num);
		solo.sendKey(Solo.DOWN);
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				l.setSelection(n);
			}
		});
		this.test.getInstrumentation().waitForIdleSync();
		if (n<l.getCount()/2) {
			solo.sendKey(Solo.DOWN);
			solo.sendKey(Solo.UP);
		} else {
			solo.sendKey(Solo.UP);			
			solo.sendKey(Solo.DOWN);
		}
		this.test.getInstrumentation().waitForIdleSync();
		View v = l.getSelectedView();
		if (longClick) {
			longClick(v);
		} else {
			click (v);
		}
		describeCurrentEvent(v);
	}
	
	protected void click (View v) {
//		android.test.TouchUtils.clickView(this.test, v);
		solo.clickOnView(v);
	}
	
	protected void longClick (View v) {
		solo.clickLongOnView(v);
	}

	private boolean describeCurrentEvent (View v) {
		if (v instanceof TextView) {
			this.currentEvent.setDescription(((TextView)v).getText().toString());
			return true;
		} else if (v instanceof ViewGroup) {
			int childNum = ((ViewGroup)v).getChildCount();
			for (int i = 0; i<childNum; i++) {
				View child =  ((ViewGroup)v).getChildAt(i);
				if (describeCurrentEvent(child)) return true;
			}
		}
		return false;
	}

	public void clearWidgetList() {
		theViews.clear();
		allViews.clear();		
	}
	
	public void retrieveWidgets () {
		home();
		clearWidgetList();
		Log.i("nofatclips", "Retrieving widgets");
		for (View w: solo.getCurrentViews()) {
			String text = (w instanceof TextView)?": "+((TextView)w).getText().toString():"";
//			int xy[] = new int[2];
//			int xy2[] = new int[2];
//			w.getLocationInWindow(xy);
//			w.getLocationOnScreen(xy2);
			Log.d("nofatclips", "Found widget: id=" + w.getId() + " ("+ w.toString() + ")" + text); // + " in window at [" + xy[0] + "," + xy[1] + "] on screen at [" + xy2[0] + "," + xy2[1] +"]");
			allViews.add(w);
			if (w.getId()>0) {
				theViews.put(w.getId(), w); // Add only if the widget has a valid ID
			}
			if (w instanceof TabHost) {
				setTabs((TabHost)w);
				Log.d("nofatclips", "Found tabhost: id=" + w.getId());
			}
		}
	}
	
	public void setRobot (Robot r) {
		this.theRobot = r;
	}

	public Map<Integer,View> getWidgets () {
		return this.theViews;
	}

	public ArrayList<View> getAllWidgets () {
		return this.allViews;
	}

	public Activity getActivity() {
		return this.theActivity;
	}

	public void setExtractor (Extractor e) {
		this.extractor = e;
	}

	public void setRestarter (Restarter r) {
		this.restarter = r;
	}
	
	public void setTabs (TabHost t) {
		this.tabs = t;
		this.tabNum = t.getTabWidget().getTabCount();
	}

	public void afterRestart() {
		solo.sleep(SLEEP_AFTER_RESTART);
		Log.d("nofatclips", "Ready to operate after restarting...");
	}
	
	public String getAppName () {
		return solo.getCurrentActivity().getApplicationInfo().toString();
	}

	@Override
	public View getWidget (int id) {
		return this.extractor.getWidget(id);
	}
	
	public View getWidget (int theId, String theType, String theName) {
		for (View testee: getWidgetsById(theId)) {
			if (checkWidgetEquivalence(testee, theId, theType, theName)) {
				return testee;
			}
		}
		return null;
	}
	
	public boolean checkWidgetEquivalence (View testee, int theId, String theType, String theName) {
		Log.i("nofatclips", "Retrieved from return list id=" + testee.getId());
		String testeeType = testee.getClass().getName();
		Log.i("nofatclips", "Testing for type (" + testeeType + ") against the original (" + theType + ")");
		String testeeName = (testee instanceof TextView)?((TextView) testee).getText().toString():"";
		Log.i("nofatclips", "Testing for name (" + testeeName + ") against the original (" + theName + ")");
		if ( (theType.equals(testeeType)) && (theName.equals(testeeName)) && (theId == testee.getId()) ) {
			return true;
		}
		return false;
	}
	
	public ArrayList<View> getWidgetsById (int id) {
		ArrayList<View> theList = new ArrayList<View>();
		for (View theView: getAllWidgets()) {
			if (theView.getId() == id) {
				Log.i("nofatclips", "Added to return list id=" + id);
				theList.add(theView);
			}
		}
		return theList;
	}

	@Override
	public ActivityDescription describeActivity() {
		return this.extractor.describeActivity();
	}

	@Override
	public void extractState() {
		this.extractor.extractState();
	}
	
	// The TrivialExtractor uses the same methods available in Automation to create
	// a description of the Activity, which is basically the name and a list of widgets
	// in the Activity.
	
	public class TrivialExtractor implements Extractor {

		@Override
		public void extractState() {
			retrieveWidgets();
		}

		@Override
		public View getWidget (int key) {
			return getWidgets().get(key);
		}

		@Override
		public ActivityDescription describeActivity() {
			return new ActivityDescription() {
				
				@Override
				public Iterator<View> iterator() {
					return getAllWidgets().iterator();
				}
				
				@Override
				public int getWidgetIndex(View v) {
					return getAllWidgets().indexOf(v);
				}

				@Override
				public String getActivityName() {
//					return getActivity().getLocalClassName();
					return getActivity().getClass().getSimpleName();
				}
				
				@Override
				public String toString() {
					return getActivityName();
				}

			};
		}

	}
	
}
