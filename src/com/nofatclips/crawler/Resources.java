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
import static android.view.KeyEvent.*;

@SuppressWarnings("unused")
public class Resources {

//	public final static String PACKAGE_NAME = "com.softmimo.android.mileagetracker";
//	public final static String CLASS_NAME = "com.softmimo.android.mileagetracker.ListView";
//	public final static String FILE_NAME = "mileage_tracker.xml"; // Output
//	public final static Comparator COMPARATOR = new NameComparator();
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;

//	public final static String PACKAGE_NAME = "com.blazing_skies.caloriecalculator";
//	public final static String CLASS_NAME = "com.blazing_skies.caloriecalculator.PreMainActivity";
//	public final static Comparator COMPARATOR = new EditTextComparator();
//	public final static int SLEEP_AFTER_EVENT = 300;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static int SLEEP_AFTER_TASK = 5000;

//	public final static String PACKAGE_NAME = "com.evancharlton.mileage";
//	public final static String CLASS_NAME = "com.evancharlton.mileage.Mileage";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, EDIT_TEXT, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
////	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 4000;
//	public final static int SLEEP_AFTER_RESTART = 4000;
	
//	public final static String PACKAGE_NAME = "net.sf.andbatdog.batterydog";
//	public final static String CLASS_NAME = "net.sf.andbatdog.batterydog.BatteryDog";
//	public final static String FILE_NAME = "batterydog.xml"; // Output
//	public final static Comparator COMPARATOR = new NameComparator();
//	public final static int SLEEP_AFTER_EVENT = 5000;
//	public final static int SLEEP_AFTER_RESTART = 2000;

	// Wordpress 1
//	public final static String PACKAGE_NAME = "org.wordpress.android";
//	public final static String CLASS_NAME = "org.wordpress.android.splashScreen";
//	public final static String FILE_NAME = "wordpress.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, EDIT_TEXT, BUTTON, MENU_VIEW, DIALOG_VIEW, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 500;
//	public final static int SLEEP_AFTER_RESTART = 6000;
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Remove Blog", "Add"),
//	};

//	// Wordpress 2 (Beta)
//	public final static String PACKAGE_NAME = "org.wordpress.android";
//	public final static String CLASS_NAME = "org.wordpress.android.Dashboard";
////	public final static String FILE_NAME = "wordpress2.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON, MENU_VIEW, DIALOG_VIEW, LIST_VIEW, SINGLE_CHOICE_LIST, MULTI_CHOICE_LIST);
//	public final static int SLEEP_AFTER_EVENT = 3000;
//	public final static int SLEEP_AFTER_RESTART = 1000;
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Remove Blog"),
//		new AfterWidgetDontExplore (2131165200),
//	};
//	static {
//		UserFactory.denyInteractionOnIds(TYPE_TEXT, 2131165215, 2131165217); // Don't change user and password
//		UserFactory.denyInteractionOnIds(CLICK, 2131165204, 2131165351); // Don't click Linear Layout container, don't check Geotag Posts
//	}
//	//	public final static InteractorAdapter[] ADDITIONAL_INPUTS = new InteractorAdapter[] { // Username and password
//	//	new FixedValueEditor().addIdValuePair(2131165215, "").addIdValuePair(2131165217, ""),
//	//};

//	public final static String PACKAGE_NAME = "com.bwx.bequick";
//	public final static String CLASS_NAME = "com.bwx.bequick.ShowSettingsActivity";
//	public final static String FILE_NAME = "quick_settings.xml"; // Output
//	public final static Comparator COMPARATOR = new EditTextComparator();
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 0;

//	public final static String PACKAGE_NAME = "net.bible.android.activity";
//	public final static String CLASS_NAME = "net.bible.android.activity.StartupActivity";
//	public final static String FILE_NAME = "bible.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON, MENU_VIEW, DIALOG_VIEW, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 1500;
//	public final static int SLEEP_AFTER_RESTART = 10000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not 
//	static {
//		UserFactory.addEvent(CLICK, BUTTON, SPINNER);
//		UserFactory.addEvent(LONG_CLICK, WEB_VIEW);
//	}
	
//	public final static String PACKAGE_NAME = "com.ichi2.anki";
//	public final static String CLASS_NAME = "com.ichi2.anki.StudyOptions";
//	public final static String FILE_NAME = "anki_guitree.xml"; // Output
//	public final static Comparator COMPARATOR = new ButtonComparator();
//	public final static int SLEEP_AFTER_EVENT = 10000;
//	public final static int SLEEP_AFTER_RESTART = 4000;

//	// API Demos
//	
//	public final static String PACKAGE_NAME = "com.example.android.apis";
//	public final static String CLASS_NAME = "com.example.android.apis.ApiDemos";
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(TEXT_VIEW, DIALOG_VIEW, MENU_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 500;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not 
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("1. Array", "13. Slow Adapter", "14. Efficient Adapter", "4. ListAdapter", "9. Array (Overlay)"),
//	};

//	public final static String PACKAGE_NAME = "com.googlecode.andoku";
//	public final static String CLASS_NAME = "com.googlecode.andoku.MainActivity";
//	public final static String FILE_NAME = "andoku.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(EDIT_TEXT, BUTTON, CHECKBOX);
//	public final static int SLEEP_AFTER_EVENT = 1000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static int MAX_EVENTS_PER_WIDGET = 11; // For GroupViews (0 = try all items in the group)
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Reset All Puzzles"),
//	};

//	public final static String PACKAGE_NAME = "de.fmaul.android.cmis";
//	public final static String CLASS_NAME = "de.fmaul.android.cmis.HomeActivity";
//	public final static String FILE_NAME = "cmis.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;

//	public final static String PACKAGE_NAME = "cz.romario.opensudoku";
//	public final static String CLASS_NAME = "cz.romario.opensudoku.gui.FolderListActivity";
//	public final static String FILE_NAME = "opensudoku.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(BUTTON, EDIT_TEXT, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static int MAX_EVENTS_PER_WIDGET = 5; // For GroupViews (0 = try all items in the group)
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Delete folder", "Delete puzzle"),
//	};
	
//	public final static String PACKAGE_NAME = "org.chemlab.dealdroid";
//	public final static String CLASS_NAME = "org.chemlab.dealdroid.Preferences";
//	public final static String FILE_NAME = "dealdroid.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 1000;
//	public final static int SLEEP_AFTER_RESTART = 1000;

//	public final static String PACKAGE_NAME = "com.kiwifruitmobile.sudoku";
//	public final static String CLASS_NAME = "com.kiwifruitmobile.sudoku.MainActivity";
//	public final static String FILE_NAME = "supersudoku.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 2000;

//	public final static String PACKAGE_NAME = "com.saatcioglu.android.guessthenumber";
//	public final static String CLASS_NAME = "com.saatcioglu.android.guessthenumber.GfxMain";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 1000;
//	public final static int SLEEP_AFTER_RESTART = 1000;

//	public final static String PACKAGE_NAME = "org.connectbot";
//	public final static String CLASS_NAME = "org.connectbot.HostListActivity";
//	public final static String FILE_NAME = "connectbot.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
////		new AfterEventDontExplore("Reset All Puzzles"),
//	};

//	public final static String PACKAGE_NAME = "com.google.android.diskusage";
//	public final static String CLASS_NAME = "com.google.android.diskusage.SelectActivity";
//	public final static String FILE_NAME = "diskusage.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(DIALOG_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 4000;
//	public final static int SLEEP_AFTER_RESTART = 1000;

//	public final static String PACKAGE_NAME = "net.mandaria.tippytipper";
//	public final static String CLASS_NAME = "net.mandaria.tippytipper.activities.TippyTipper";
//	public final static String FILE_NAME = "tippytipper.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(DIALOG_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 1000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "com.FireFart.Permissions";
//	public final static String CLASS_NAME = "com.FireFart.Permissions.Permissions";
//	public final static String FILE_NAME = "permissions.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(TEXT_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 3400;
//	public final static int SLEEP_AFTER_RESTART = 3400;
//	public final static int MAX_EVENTS_PER_WIDGET = 4; // For GroupViews (0 = try all items in the group)

//	public final static String PACKAGE_NAME = "ee.smkv.calc.loan";
//	public final static String CLASS_NAME = "ee.smkv.calc.loan.MainActivity";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(TEXT_VIEW, DIALOG_VIEW, MENU_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 500;

	// TomDroid
//	public final static String PACKAGE_NAME = "org.tomdroid";
//	public final static String CLASS_NAME = "org.tomdroid.ui.Tomdroid";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON, MENU_VIEW); //, DIALOG_VIEW, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 1000;
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Service"),
//	};
//	public final static InteractorAdapter[] ADDITIONAL_INPUTS = new InteractorAdapter[] { // Username and password
//		new FixedValueEditor().addIdValuePair(16908291, "\r\n"),
//	};
//	public final static String[] PRECRAWLING = new String[] {
//		OPEN_MENU, null,
//		CLICK_ON_TEXT, "Settings", null,
//		CLICK_ON_TEXT, "Server", null,
//	};

//	// Aarddict
//	public final static String PACKAGE_NAME = "aarddict.android";
//	public final static String CLASS_NAME = "aarddict.android.LookupActivity";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON, MENU_VIEW, DIALOG_VIEW, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 6000;
//	public final static int SLEEP_AFTER_RESTART = 6000;
//	static {
//		UserFactory.addEvent(CLICK);
//		UserFactory.forceIdsForEvent(CLICK, "2131099660");
//	}
//	public final static InteractorAdapter[] ADDITIONAL_EVENTS = new InteractorAdapter[] { // Search
//		new FixedValueEditor().addIdValuePair(2131099648, "5"),
//	};
//	public final static String[] PRECRAWLING = new String[] {
//		TYPE_TEXT, "2131099659", "wa", null,
//		CLICK_ON_TEXT, "Miller", null,
//	};
	
	// Book Catalog
//	public final static String PACKAGE_NAME = "com.eleybourn.bookcatalogue";
//	public final static String CLASS_NAME = "com.eleybourn.bookcatalogue.BookCatalogue";
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(LIST_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static String[] PRECRAWLING = new String[] {
//		LIST_SELECT, "16908298", "2", null,
//		OPEN_MENU, null,
//		CLICK_ON_TEXT, "Duplicate Book", null,
//		TYPE_TEXT, "2131034177", "85", null,
//		CLICK, "2131034189", NULL, null,
//		CLICK, "2131034152", NULL, null,
//		CLICK, "2131034199", NULL, null,
//		CLICK_ON_TEXT, "Save Book", null,
//		LIST_SELECT, "16908298", "3", null,
//		CLICK, "2131034189", NULL, null,
//		SWAP_TAB, "16908306", "4", null,
//	};

	public final static String PACKAGE_NAME = "com.nofatclips.keyevent";
	public final static String CLASS_NAME = "com.nofatclips.keyevent.KeyEventsDumpActivity";
	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(EDIT_TEXT);
	public final static int SLEEP_AFTER_EVENT = 4000;
	public final static int SLEEP_AFTER_RESTART = 4000;


	/*
	 * 				Default Parameters
	 */

	// Default events and inputs for the User
	static {
//		UserFactory.addEvent(CLICK, BUTTON, MENU_ITEM, LINEAR_LAYOUT);
//		UserFactory.addEvent(LONG_CLICK, WEB_VIEW);
//		UserFactory.addEvent(LIST_SELECT, LIST_VIEW, SINGLE_CHOICE_LIST, PREFERENCE_LIST);
//		UserFactory.addEvent(LIST_LONG_SELECT, LIST_VIEW, SINGLE_CHOICE_LIST);
//		UserFactory.addEvent(SWAP_TAB, TAB_HOST);
//		UserFactory.addInput(CLICK, CHECKBOX, RADIO, TOGGLE_BUTTON);
//		UserFactory.addInput(SET_BAR, SEEK_BAR);
//		UserFactory.addInput(WRITE_TEXT, EDIT_TEXT);
//		UserFactory.addInput(SPINNER_SELECT, SPINNER);
	}
	public final static int[] KEY_EVENTS = {KEYCODE_HOME, KEYCODE_ENDCALL, KEYCODE_VOLUME_DOWN};
	
	// Precrawling sequence
	public final static String[] PRECRAWLING = new String[] {};
	
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
	public final static boolean COMPARE_LIST_COUNT = false;
	
	// Persistence Parameters
	public final static int MAX_TRACES_IN_RAM = 0; // After performing this amount of traces, the crawler saves to disk, empties the session and continues (0 = keep all in RAM)
	public final static boolean ENABLE_RESUME = false;
	public final static String TASK_LIST_FILE_NAME = "tasklist.xml"; // Save state for resume and optionally output
	public final static String ACTIVITY_LIST_FILE_NAME = "activities.xml"; // Save state for resume
	public final static String PARAMETERS_FILE_NAME = "parameters.obj"; // Save state for resume
	public final static String FILE_NAME = "guitree.xml"; // Output

	// Screenshot Parameters
	public final static boolean SCREENSHOT_FOR_STATES = false; // Performs an image capture of the screen after processing a task
	public final static boolean SCREENSHOT_ONLY_NEW_STATES = false; // Capture only if comparation was negative - Only relevant if SCREENSHOT_FOR_STATES is true
	public final static boolean SCREENSHOT_FOR_EVENTS = false; // Performs an image capture of the screen before firing an event

	// User/Planner Parameters
	public final static int MAX_EVENTS_PER_WIDGET = 2; // For GroupViews (0 = try all items in the group)
	public final static boolean ALL_EVENTS_ON_PREFERENCES = true; // Bypass MAX_EVENTS_PER_WIDGET for PREFERENCE_LIST when true
	public final static int MAX_TASKS_PER_EVENT = 1; // How many input sequences to generate for each event on a widget; 0 = no limit
	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not
	public final static boolean BACK_BUTTON_EVENT = false;
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
	public final static int SLEEP_AFTER_TASK = 0;
	public final static String XML_BODY_BEGIN = "    <TRACE";
	public final static String XML_BODY_END = "/TRACE>";
	public final static CrawlerLog LOGGER = new SessionLogger();

	/** @author nicola amatucci */
	//Sensor events on/off
	public static boolean USE_SENSORS = true;
	public static boolean USE_GPS = false;
	public static boolean EXCLUDE_WIDGETS_INPUTS_IN_SENSORS_EVENTS = true; // aggiunge input prima di scatenare l'evento "sensore"
	
	//Sensors used
	public final static Integer[] SENSOR_TYPES = new Integer[] {
		//android.hardware.Sensor.TYPE_ACCELEROMETER,
		android.hardware.Sensor.TYPE_ORIENTATION // Inclinazione del dispositivo + bussola e accelerometro 
		//,
		//android.hardware.Sensor.TYPE_MAGNETIC_FIELD,
		//android.hardware.Sensor.TYPE_TEMPERATURE
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