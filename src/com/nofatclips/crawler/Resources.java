package com.nofatclips.crawler;

import com.nofatclips.androidtesting.model.InteractionType;
import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.StrategyCriteria;
import com.nofatclips.crawler.planning.UserFactory;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;
import com.nofatclips.crawler.storage.*;
import com.nofatclips.crawler.strategy.comparator.*;
import com.nofatclips.crawler.planning.interactors.*;
import com.nofatclips.crawler.strategy.criteria.*;

import android.location.LocationManager;
import android.util.Log;

import static com.nofatclips.androidtesting.model.SimpleType.*;
import static com.nofatclips.androidtesting.model.InteractionType.*;

@SuppressWarnings("unused")
public class Resources {

	
	public final static String PACKAGE_NAME = "it.simple.app";
	public final static String CLASS_NAME = "it.simple.app.SimpleAppActivity";
	public final static Comparator COMPARATOR = new NameComparator();
	public final static int SLEEP_AFTER_EVENT = 2000;
	public final static int SLEEP_AFTER_RESTART = 2000;
	

	static {
		UserFactory.addEvent(CLICK, BUTTON);
		UserFactory.addInput(TYPE_TEXT, EDIT_TEXT);
	}
	
	// Precrawling sequence
	public final static String[] PRECRAWLING = new String[] { };
	
	// Strategy Parameters
	public final static int MAX_NUM_TRACES = 0; // After performing this amount of traces, the crawler exits (0 = no length limit)
	public final static int PAUSE_AFTER_TRACES = 0; // After performing this amount of traces, the crawler pauses (0 = no pause)
	public final static long MAX_TIME_CRAWLING = 0; // In seconds (0 = no time limit)
	public final static long PAUSE_AFTER_TIME = 0; // In seconds (0 = no pause)
	public final static int TRACE_MAX_DEPTH = 0; // Max number of transitions in a trace (0 = no depth limit)
	public final static boolean CHECK_FOR_TRANSITION = false;
	public final static boolean EXPLORE_ONLY_NEW_STATES = true;
//	public final static Comparator COMPARATOR = new NullComparator();
	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {};
	public final static boolean COMPARE_STATE_TITLE = true;
	public final static boolean COMPARE_LIST_COUNT = true;
	
	// Persistence Parameters
	public final static int MAX_TRACES_IN_RAM = 0; // After performing this amount of traces, the crawler saves to disk, empties the session and continues (0 = keep all in RAM)
	public final static boolean ENABLE_RESUME = false;
	public final static String TASK_LIST_FILE_NAME = "tasklist.xml"; // Save state for resume
	public final static String ACTIVITY_LIST_FILE_NAME = "activities.xml"; // Save state for resume
	public final static String PARAMETERS_FILE_NAME = "parameters.obj";
	public final static String FILE_NAME = "guitree.xml"; // Output

	// Screenshot Parameters
	public final static boolean SCREENSHOT_FOR_STATES = false; // Performs an image capture of the screen after processing a task
	public final static boolean SCREENSHOT_ONLY_NEW_STATES = false; // Capture only if comparation was negative - Only relevant if SCREENSHOT_FOR_STATES is true
	public final static boolean SCREENSHOT_FOR_EVENTS = false; // Performs an image capture of the screen before firing an event

	// User/Planner Parameters
	public final static int MAX_EVENTS_PER_WIDGET = 5; // For GroupViews (0 = try all items in the group)
	public final static int MAX_TASKS_PER_EVENT = 1; // How many input sequences to generate for each event on a widget; 0 = no limit
	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not
	public final static boolean BACK_BUTTON_EVENT = true;
	public final static boolean MENU_EVENTS = false;
	public final static boolean ORIENTATION_EVENTS = false;
	public final static boolean TAB_EVENTS_START_ONLY = true; // true -> click on tabs only on the start activity
	public final static boolean SCROLL_DOWN_EVENT = false;
	public final static long RANDOM_SEED = 93874383493L; // 0 = Random
	public final static InteractorAdapter[] ADDITIONAL_EVENTS = new InteractorAdapter[] {};
	public final static InteractorAdapter[] ADDITIONAL_INPUTS = new InteractorAdapter[] {};
	
	// More Parameters
	public final static boolean IN_AND_OUT_FOCUS = true;
	public final static boolean FORCE_RESTART = true;
	public final static boolean ACTIVITY_DESCRIPTION_IN_SESSION = true;
	public final static boolean RETRY_FAILED_TRACES = false; // Crashed and failed traces are retried once in case the failure had an asynchronous cause
	public final static int SLEEP_ON_THROBBER = 30000; // How long to wait on spinning wheels (in ms -- 0 = don't wait)
	public final static String XML_BODY_BEGIN = "    <TRACE";
	public final static String XML_BODY_END = "/TRACE>";
	public final static CrawlerLog LOGGER = new SessionLogger();

	/** @author nicola amatucci */
	//Sensor events on/off
	public static boolean USE_SENSORS = false;
	public static boolean USE_GPS = false;
	public static boolean EXCLUDE_WIDGETS_INPUTS_IN_SENSORS_EVENTS = false;
	
	//Sensors used
	public final static Integer[] SENSOR_TYPES = new Integer[] {
		android.hardware.Sensor.TYPE_ACCELEROMETER,
		android.hardware.Sensor.TYPE_ORIENTATION,
		android.hardware.Sensor.TYPE_MAGNETIC_FIELD,
		android.hardware.Sensor.TYPE_TEMPERATURE
		//android.hardware.Sensor.TYPE_GRAVITY
		//android.hardware.Sensor.TYPE_GYROSCOPE
		//android.hardware.Sensor.TYPE_LIGHT
		//android.hardware.Sensor.TYPE_LINEAR_ACCELERATION
		//android.hardware.Sensor.TYPE_PRESSURE
		//android.hardware.Sensor.TYPE_PROXIMITY
		//android.hardware.Sensor.TYPE_ROTATION_VECTOR
	};
	
	//location provider
	public static final String TEST_LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
	
	//emulator port
	public static final int EMULATOR_PORT = 5554;
	
	//sms, call
	public static final boolean SIMULATE_INCOMING_CALL = true;
	public static final boolean SIMULATE_INCOMING_SMS = true;
	/** @author nicola amatucci */
	
	public static Class<?> theClass;
	static {
		try {
			theClass = Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}