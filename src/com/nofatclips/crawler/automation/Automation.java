package com.nofatclips.crawler.automation;

import static android.content.Context.WINDOW_SERVICE;
import static android.view.Surface.ROTATION_0;
import static android.view.Surface.ROTATION_180;
import static com.nofatclips.androidtesting.model.InteractionType.ACCELEROMETER_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.AMBIENT_TEMPERATURE_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.BACK;
import static com.nofatclips.androidtesting.model.InteractionType.CHANGE_ORIENTATION;
import static com.nofatclips.androidtesting.model.InteractionType.CLICK;
import static com.nofatclips.androidtesting.model.InteractionType.CLICK_ON_TEXT;
import static com.nofatclips.androidtesting.model.InteractionType.GPS_LOCATION_CHANGE_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.GPS_PROVIDER_DISABLE_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.LIST_LONG_SELECT;
import static com.nofatclips.androidtesting.model.InteractionType.LIST_SELECT;
import static com.nofatclips.androidtesting.model.InteractionType.LONG_CLICK;
import static com.nofatclips.androidtesting.model.InteractionType.MAGNETIC_FIELD_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.OPEN_MENU;
import static com.nofatclips.androidtesting.model.InteractionType.ORIENTATION_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.SCROLL_DOWN;
import static com.nofatclips.androidtesting.model.InteractionType.SET_BAR;
import static com.nofatclips.androidtesting.model.InteractionType.SPINNER_SELECT;
import static com.nofatclips.androidtesting.model.InteractionType.SWAP_TAB;
import static com.nofatclips.androidtesting.model.InteractionType.TEMPERATURE_SENSOR_EVENT;
import static com.nofatclips.androidtesting.model.InteractionType.TYPE_TEXT;
import static com.nofatclips.androidtesting.model.SimpleType.BUTTON;
import static com.nofatclips.androidtesting.model.SimpleType.MENU_ITEM;
import static com.nofatclips.crawler.Resources.FORCE_RESTART;
import static com.nofatclips.crawler.Resources.IN_AND_OUT_FOCUS;
import static com.nofatclips.crawler.Resources.PRECRAWLING;
import static com.nofatclips.crawler.Resources.SLEEP_AFTER_EVENT;
import static com.nofatclips.crawler.Resources.SLEEP_AFTER_RESTART;
import static com.nofatclips.crawler.Resources.SLEEP_ON_THROBBER;
import static com.nofatclips.crawler.Resources.TEST_LOCATION_PROVIDER;
import static com.nofatclips.crawler.Resources.USE_GPS;
import static com.nofatclips.crawler.Resources.USE_SENSORS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.crawler.model.ActivityDescription;
import com.nofatclips.crawler.model.Extractor;
import com.nofatclips.crawler.model.ImageCaptor;
import com.nofatclips.crawler.model.Restarter;
import com.nofatclips.crawler.model.Robot;
import com.nofatclips.crawler.model.TaskProcessor;

// Automation implements the methods to interact with the application via the Instrumentation (Robot)
// and to extract informations from it (Extractor); the Robotium framework is used where possible

public class Automation implements Robot, Extractor, TaskProcessor, ImageCaptor {
	
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
	private ImageCaptor imageCaptor;
		
	/** @author nicola amatucci */
	//settati da GuiTreeEngine.setUp()
	public LocationManager locationManager;
	/** @author nicola amatucci */
	
	// A Trivial Extractor is provided if none is assigned
	public Automation () {
		TrivialExtractor te = new TrivialExtractor(); 
		setExtractor (te);
		this.imageCaptor = te;
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
		this.solo = new Solo (getInstrumentation(), test.getActivity());
		afterRestart();
		refreshCurrentActivity();
		Log.w ("nofatclips","--->" + theActivity.getLocalClassName());
	}
	
	public void execute (Trace t) {
		this.theRobot.process (t);
	}
	
	public void process (Trace t) {
		Log.i ("nofatclips", "Restarting");
		if (FORCE_RESTART) {
			this.restarter.restart();
		}
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

	public void finalize() {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		theActivity.finish();
	}

	public void fireEvent(UserEvent e) {
		this.currentEvent = e;
		String eventType = e.getType();
		String eventValue = e.getValue();
		if (eventType.equals(BACK) || eventType.equals(SCROLL_DOWN)) { // Special events
			Log.d("nofatclips", "Firing event: type= " + eventType);
			fireEventOnView(null, eventType, null);
		} else if (eventType.equals(CLICK_ON_TEXT)) {
			Log.d("nofatclips", "Firing event: type= " + eventType + " value= " + eventValue);
			fireEventOnView(null, eventType, eventValue);
		} else {
			View v = null;
			if (e.getWidget().getIndex()<getAllWidgets().size()) {
				v = getAllWidgets().get(e.getWidget().getIndex()); // Search widget by index
			}
			if ((v!=null) && checkWidgetEquivalence(v, Integer.parseInt(e.getWidgetId()), e.getWidgetType(), e.getWidgetName())) { // Widget found
				Log.i("nofatclips", "Firing event: type= " + eventType + " index=" + e.getWidget().getIndex() + " widget="+ e.getWidgetType());
				fireEventOnView (v, eventType, eventValue);
			} else if (e.getWidgetId().equals("-1")) { // Widget not found. Search widget by name
				Log.i("nofatclips", "Firing event: type= " + eventType + " name=" + e.getWidgetName() + " widget="+ e.getWidgetType());
				fireEvent (e.getWidgetName(), e.getWidget().getSimpleType(), eventType, eventValue);
			} else { // Widget not found. Search widget by id
				Log.i("nofatclips", "Firing event: type= " + eventType + " id=" + e.getWidgetId() + " widget="+ e.getWidgetType());
				fireEvent (Integer.parseInt(e.getWidgetId()), e.getWidgetName(), e.getWidget().getSimpleType(), eventType, eventValue);
			}
		}
		this.currentEvent = null;
	}

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
		} else if (widgetType.equals(MENU_ITEM)) {
			v = solo.getText(widgetName);
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
		injectInteraction(v, eventType, value);
		wait(SLEEP_AFTER_EVENT);
		waitOnThrobber();
		refreshCurrentActivity();
		extractState();
	}
	
	private void injectInteraction (View v, String interactionType, String value) {
		if (v!=null) {
			requestView(v);
		}
		if (interactionType.equals(CLICK)) {
			click (v);
		} else if (interactionType.equals(LONG_CLICK)) {
			longClick(v);
		} else if (interactionType.equals(BACK)) {
			solo.goBack();
		} else if (interactionType.equals(OPEN_MENU)) {
			solo.sendKey(Solo.MENU);
		} else if (interactionType.equals(SCROLL_DOWN)) {
			solo.scrollDown();
		} else if (interactionType.equals(CHANGE_ORIENTATION)) {
			changeOrientation();
		} else if (interactionType.equals(CLICK_ON_TEXT)) {
			clickOnText(value);
		} else if (interactionType.equals(SWAP_TAB) && (value!=null)) {
			if (v instanceof TabHost) {
				swapTab ((TabHost)v, value);
			} else {
				swapTab (value);
			}
		} else if (interactionType.equals(LIST_SELECT)) {
			selectListItem((ListView)v, value);
		} else if (interactionType.equals(LIST_LONG_SELECT)) {
			selectListItem((ListView)v, value, true);
		} else if (interactionType.equals(SPINNER_SELECT)) {
			selectSpinnerItem((Spinner)v, value);
		} else if (interactionType.equals(TYPE_TEXT)) {
			solo.enterText((EditText)v, value);
		} else if (interactionType.equals(SET_BAR)) {
			solo.setProgressBar((ProgressBar)v, Integer.parseInt(value));		
			
			/** @author nicola amatucci */
		} else  if (
					interactionType.equals(ORIENTATION_SENSOR_EVENT) ||
					interactionType.equals(ACCELEROMETER_SENSOR_EVENT) ||
					interactionType.equals(MAGNETIC_FIELD_SENSOR_EVENT) ||
					interactionType.equals(TEMPERATURE_SENSOR_EVENT) ||
					interactionType.equals(AMBIENT_TEMPERATURE_SENSOR_EVENT)
					) {
				fireSensorEvent(value, interactionType);
				
		} else if ( interactionType.equals(GPS_LOCATION_CHANGE_EVENT) ) {
			fireGPSLocationChangeEvent(value);
		} else if ( interactionType.equals(GPS_PROVIDER_DISABLE_EVENT) ) {
			
			/** @author nicola amatucci */
		} else {
			
			
			return;
		}
	}

	/** @author nicola amatucci */	
	private void fireSensorEvent(String value, String interactionType)
	{
		if (value != null && interactionType != null)
		{
			String[] stringValues = value.split("\\|");
			
			if (stringValues != null && stringValues.length == 3)
			{
				float[] floatValues = new float[3];
				floatValues[0] = Float.parseFloat(stringValues[0]);
				floatValues[1] = Float.parseFloat(stringValues[1]);
				floatValues[2] = Float.parseFloat(stringValues[2]);
				
				
				
			}
		}
	}
	
	private void fireGPSLocationChangeEvent(String value)
	{
		//abilita il provider se disabilitato
		if ( locationManager.isProviderEnabled( TEST_LOCATION_PROVIDER ) == false )
			locationManager.setTestProviderEnabled(TEST_LOCATION_PROVIDER, true);
		
		if (value != null)
		{
			String[] stringValues = value.split("\\|");
			
			if (stringValues != null && stringValues.length == 3)
			{
				double[] doubleValues = new double[3];
				doubleValues[0] = Double.parseDouble(stringValues[0]); //latitude
				doubleValues[1] = Double.parseDouble(stringValues[1]); //longitude
				doubleValues[2] = Double.parseDouble(stringValues[2]); //altitude
				
		        Location location = new Location(TEST_LOCATION_PROVIDER);
		        location.setLatitude(doubleValues[0]);
		        location.setLongitude(doubleValues[1]);
		        location.setAltitude(doubleValues[2]);
		        locationManager.setTestProviderLocation(TEST_LOCATION_PROVIDER, location);
			}
		}
	}
	
	/*
	private void disableGPSLocationProvider()
	{
		//disabilita il provider
		if ( locationManager.isProviderEnabled( TEST_LOCATION_PROVIDER ) == true )
			locationManager.setTestProviderEnabled(TEST_LOCATION_PROVIDER, false);
	}
	*/
	/** @author nicola amatucci */
	
	// Scroll the view to the top. Only works for ListView and ScrollView. Support for GridView and others must be added
	public void home () {
		
		// Scroll listviews up
		final ArrayList<ListView> viewList = solo.getCurrentListViews();
		if (viewList.size() > 0) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					viewList.get(0).setSelection(0);
				}
			});
		}
		
		// Scroll scrollviews up
		final ArrayList<ScrollView> viewScroll = solo.getCurrentScrollViews();
		if (viewScroll.size() > 0) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					viewScroll.get(0).fullScroll(ScrollView.FOCUS_UP);
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
		injectInteraction(v, inputType, value);
	}

	private void swapTab (TabHost t, String tab) {
		swapTab (t, Integer.valueOf(tab));
	}
	
	private void clickOnText (String text) {
		solo.clickOnText (text);
	}

	private void swapTab (final TabHost t, int num) {
		assertNotNull(t, "Cannon swap tab: the tab host does not exist");
		ActivityInstrumentationTestCase2.assertTrue("Cannot swap tab: tab index out of bound", num<=t.getTabWidget().getTabCount());
		final int n = Math.min(this.tabNum, Math.max(1,num))-1;
		Log.i("nofatclips", "Swapping to tab " + num);
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				t.setCurrentTab(n);
			}
		});
		sync();
		describeCurrentEvent(t.getTabWidget().getChildAt(n));
	}

	private void selectListItem (ListView l, String item) {
		selectListItem (l, item, false);
	}

	private void selectListItem (ListView l, String item, boolean longClick) {
		selectListItem (l, Integer.valueOf(item), longClick);
	}

	private void selectListItem (final ListView l, int num, boolean longClick) {
		assertNotNull(l, "Cannon select list item: the list does not exist");
		final int n = Math.min(l.getCount(), Math.max(1,num))-1;
		requestFocus(l);
		Log.i("nofatclips", "Swapping to listview item " + num);
		solo.sendKey(Solo.DOWN);
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				l.setSelection(n);
			}
		});
		sync();
		if (n<l.getCount()/2) {
			solo.sendKey(Solo.DOWN);
			solo.sendKey(Solo.UP);
		} else {
			solo.sendKey(Solo.UP);			
			solo.sendKey(Solo.DOWN);
		}
		sync();
		View v = l.getSelectedView();
		if (longClick) {
			longClick(v);
		} else {
			click (v);
		}
	}

	private void selectSpinnerItem (Spinner l, String item) {
		selectSpinnerItem (l, Integer.valueOf(item));
	}

	private void selectSpinnerItem (final Spinner s, int num) {
		assertNotNull(s, "Cannon press spinner item: the spinner does not exist");
		Log.i("nofatclips", "Clicking the spinner view");
		click(s);
		sync();
		selectListItem(solo.getCurrentListViews().get(0), num, false);
	}

	protected void assertNotNull (final View v) {
		ActivityInstrumentationTestCase2.assertNotNull(v);
	}

	protected void assertNotNull (final View v, String errorMessage) {
		ActivityInstrumentationTestCase2.assertNotNull(errorMessage, v);
	}

	public static boolean isInAndOutFocusEnabled() {
		return (IN_AND_OUT_FOCUS);
	}		

	protected void requestFocus (final View v) {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				v.requestFocus();		
			}
		});
		sync();
	}
	
//    public void  sendKeyDownUpLong(int key) {
//		long downTime = SystemClock.uptimeMillis();
//		long eventTime = SystemClock.uptimeMillis();
//		KeyEvent down = new KeyEvent(downTime, eventTime, KeyEvent.ACTION_DOWN, key, 100);
//        getInstrumentation().sendKeySync(down);
//        solo.sleep(1500);//solo.sleep((int) (android.view.ViewConfiguration.getLongPressTimeout() * 2.5f));
//		eventTime = SystemClock.uptimeMillis();
//		KeyEvent up = new KeyEvent(downTime, eventTime, KeyEvent.ACTION_UP, key, 0);
//		up = KeyEvent.changeFlags(down, KeyEvent.FLAG_LONG_PRESS);
//        getInstrumentation().sendKeySync(up);
//    }
	
	// Scroll until the view is on the screen if IN_AND_OUT_OF_FOCUS is enabled or if the force parameter is true 
	protected void requestView (final View v, boolean force) {
		if (force || isInAndOutFocusEnabled()) {
			home();
			solo.sendKey(Solo.UP); // Solo.waitForView() requires a widget to be focused		
			solo.waitForView(v, 1000, true);
		}
		requestFocus(v);
	}		

	protected void requestView (final View v) {
		requestView(v, false);
	}
	
	protected void click (View v) {
		assertNotNull(v,"Cannot click: the widget does not exist");
//		android.test.TouchUtils.clickView(this.test, v);
		describeCurrentEvent(v);
		solo.clickOnView(v);
	}
	
	protected void longClick (View v) {
		assertNotNull(v, "Cannot longClick: the widget does not exist");
		describeCurrentEvent(v);
		solo.clickLongOnView(v);
	}
	
	protected void wait (int milli) {
		Log.i("nofatclips", "Waiting for " + ((milli>=1000)?(milli/1000 + " sec."):(milli + " msec.")));
		solo.sleep(milli);
	}

	private boolean describeCurrentEvent (View v) {
		if (this.currentEvent == null) return false; // This is probably an input, not an event
		if (v instanceof TextView) {
			String s = ((TextView)v).getText().toString();
			this.currentEvent.setDescription(s);
			Log.d ("nofatclips", "Event description: " + s);
			return true;
		} else if (v instanceof TabHost) {
			this.currentEvent.setDescription(((TabHost)v).getCurrentTabTag());
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
		ArrayList<View> viewList = (isInAndOutFocusEnabled())?solo.getViews():solo.getCurrentViews();
		for (View w: viewList) {
			String text = (w instanceof TextView)?": "+((TextView)w).getText().toString():"";
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
		solo.setActivityOrientation(Solo.PORTRAIT);
		wait(SLEEP_AFTER_RESTART);
		waitOnThrobber();
		if (PRECRAWLING.length>0) {
			refreshCurrentActivity();
			extractState();
			processPrecrawling();
		}
		Log.d("nofatclips", "Ready to operate after restarting...");
	}
	
	private void processPrecrawling() {
		Log.i("nofatclips", "Processing precrawling");
		String[] params = new String[3];
		int paramCount=0;
		for (String s: PRECRAWLING) {
			if (s == null) {
				switch (paramCount) {
					case 0: continue;
					case 1: {
						Log.i ("nofatclips", "Firing event " + params[0]);
						fireEventOnView(null, params[0], null);
						break;
					}
					case 2: {
						Log.i ("nofatclips", "Firing event " + params[0] + " with value: " + params[1]);
						fireEventOnView(null, params[0], params[1]);
						break;
					}	
					case 3: {
						View v = getWidget(Integer.parseInt(params[1]));
						Log.i ("nofatclips", "Firing event " + params[0] + " on widget #" + params[1] + " with value: " + params[2]);
						fireEventOnView(v, params[0], params[2]);
						break;
					}
				}; 
				paramCount = 0;
			} else {
				params[paramCount] = s;
				paramCount++;
			}
		}
	}
	
	public Instrumentation getInstrumentation() {
		return this.test.getInstrumentation();
	}
	
	public void changeOrientation() {
		Display display = ((WindowManager) getInstrumentation().getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int angle = display.getRotation();
		int newAngle = ((angle==ROTATION_0)||(angle==ROTATION_180))?Solo.LANDSCAPE:Solo.PORTRAIT;
		solo.setActivityOrientation(newAngle);
	}
	
	public void waitOnThrobber() {
		int sleepTime = SLEEP_ON_THROBBER;
		if (sleepTime==0) return;
		
		boolean flag;
		do {
			flag = false;
			int oldId = 0;
			ArrayList<ProgressBar> bars = solo.getCurrentProgressBars();
			for (ProgressBar b: bars) {
				if (b.isShown() && b.isIndeterminate()) {
					int newId = b.getId();
					if (newId != oldId) { // Only log if the throbber changed since the last time
						Log.d("nofatclips", "Waiting on Progress Bar #" + newId);
						oldId = newId;
					}
					flag = true;
					wait(500);
					sleepTime-=500;
				}
			}
		} while (flag && (sleepTime>0));
		sync();
	}
	
	public String getAppName () {
		return solo.getCurrentActivity().getApplicationInfo().toString();
	}

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
		Log.d("nofatclips", "Testing for type (" + testeeType + ") against the original (" + theType + ")");
		String testeeText = (testee instanceof TextView)?(((TextView)testee).getText().toString()):"";
		
		String testeeName = testeeText;
		if (testee instanceof EditText) {
			CharSequence hint = ((EditText)testee).getHint();
			testeeName = (hint==null)?"":hint.toString();
		}
		
//		String testeeName = (testee instanceof EditText)?(((EditText)testee).getHint().toString()):testeeText;
		Log.d("nofatclips", "Testing for name (" + testeeName + ") against the original (" + theName + ")");
		if ( (theType.equals(testeeType)) && (theName.equals(testeeName)) && (theId == testee.getId()) ) {
			return true;
		}
		return false;
	}
	
	public ArrayList<View> getWidgetsById (int id) {
		ArrayList<View> theList = new ArrayList<View>();
		for (View theView: getAllWidgets()) {
			if (theView.getId() == id) {
				Log.d("nofatclips", "Added to return list id=" + id);
				theList.add(theView);
			}
		}
		return theList;
	}

	public ActivityDescription describeActivity() {
		return this.extractor.describeActivity();
	}

	public void extractState() {
		this.extractor.extractState();
	}
	
	public Bitmap captureImage() {
		return this.imageCaptor.captureImage();
	}
	
	public void sync() {
		getInstrumentation().waitForIdleSync();
	}
	
	// The TrivialExtractor uses the same methods available in Automation to create
	// a description of the Activity, which is basically the name and a list of widgets
	// in the Activity.
	
	public class TrivialExtractor implements Extractor, ImageCaptor {

		public void extractState() {
			retrieveWidgets();
		}

		public View getWidget (int key) {
			return getWidgets().get(key);
		}

		public Activity getActivity() {
			return theActivity;
		}

		public ActivityDescription describeActivity() {
			return new ActivityDescription() {
				
				public Iterator<View> iterator() {
					return getAllWidgets().iterator();
				}
				
				public int getWidgetIndex(View v) {
					return getAllWidgets().indexOf(v);
				}

				public String getActivityName() {
//					return getActivity().getLocalClassName();
					return getActivity().getClass().getSimpleName();
				}

				public String getActivityTitle() {
					return getActivity().getTitle().toString();
				}

				public String toString() {
					return getActivityName();
				}
				
				/** @author nicola amatucci */
				public boolean usesSensorsManager()
				{
					//it.unina.android.hardware.SensorEventListener
					//android.hardware.SensorEventListener
					//TODO: cambiare a seconda delle decisioni prese
					if (USE_SENSORS)
						return com.nofatclips.crawler.automation.sensors_utils.ReflectorHelper.scanClassForInterface(getActivity().getClass(), "android.hardware.SensorEventListener");
					else
						return false;
				}
				
				public boolean usesLocationManager()
				{
					if (USE_GPS)
						return com.nofatclips.crawler.automation.sensors_utils.ReflectorHelper.scanClassForInterface(getActivity().getClass(), "android.location.LocationListener");
					else
						return false;
				}
				/** @author nicola amatucci */

			};
		}
		
		public Bitmap captureImage() {
			final View view = solo.getViews().get(0);
			final boolean flag = view.isDrawingCacheEnabled();
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (!flag) {
						view.setDrawingCacheEnabled(true);
					}
		            view.buildDrawingCache();
				}
			});
			sync();
			Bitmap b = view.getDrawingCache();
            b = b.copy(b.getConfig(), false);
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (!flag) {
						view.setDrawingCacheEnabled(false);
					}
				}
			});
			return b;
		}

	}
	
}
