package com.nofatclips.crawler;

import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.model.StrategyCriteria;
import com.nofatclips.crawler.planning.UserFactory;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;
import com.nofatclips.crawler.storage.*;
import com.nofatclips.crawler.strategy.comparator.*;
import com.nofatclips.crawler.planning.interactors.*;
import com.nofatclips.crawler.strategy.criteria.*;

import static com.nofatclips.androidtesting.model.SimpleType.*;
import static com.nofatclips.androidtesting.model.InteractionType.*;

public class Resources {

//	public final static String PACKAGE_NAME = "com.softmimo.android.mileagetracker";
//	public final static String CLASS_NAME = "com.softmimo.android.mileagetracker.ListView";
//	public final static String FILE_NAME = "mileage_tracker.xml"; // Output
//	public final static Comparator COMPARATOR = new NameComparator();
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "com.blazing_skies.caloriecalculator";
//	public final static String CLASS_NAME = "com.blazing_skies.caloriecalculator.PreMainActivity";
//	public final static String FILE_NAME = "calorie.xml"; // Output
//	public final static Comparator COMPARATOR = new EditTextComparator();
//	public final static int SLEEP_AFTER_EVENT = 0;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "com.evancharlton.mileage";
//	public final static String CLASS_NAME = "com.evancharlton.mileage.Mileage";
//	public final static String FILE_NAME = "mileage.xml"; // Output
////	public final static Comparator COMPARATOR = new CustomWidgetsComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, EDIT_TEXT, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 4000;
//	public final static int SLEEP_AFTER_RESTART = 4000;
//	public final static int MAX_EVENTS_PER_WIDGET = 7;
	
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
//	public final static String FILE_NAME = "wordpress2.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON, MENU_VIEW, DIALOG_VIEW, LIST_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 6000;
//	public final static int SLEEP_AFTER_RESTART = 1000;
//	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {
//		new AfterEventDontExplore("Remove Blog"),
//		new AfterWidgetDontExplore (2131165200),
//	};
//	static {
//		UserFactory.addEvent(CLICK, BUTTON, LINEAR_LAYOUT);
//		UserFactory.denyInteractionOnIds(TYPE_TEXT, 2131165215, 2131165217); // Don't change user and password
//		UserFactory.denyInteractionOnIds(CLICK, 2131165204, 2131165351); // Don't click Linear Layout container, don't check Geotag Posts
//	}
//	//	public final static InteractorAdapter[] ADDITIONAL_INPUTS = new InteractorAdapter[] { // Username and password
//	//	new FixedValueEditor().addIdValuePair(2131165215, "").addIdValuePair(2131165217, ""),
//	//};
	
	public final static String PACKAGE_NAME = "PackageAndroid.Android";
	public final static String CLASS_NAME = "PackageAndroid.Android.ProvaAndroidActivity";
	public final static String FILE_NAME = "prova.xml"; // Output
	public final static Comparator COMPARATOR = new CustomWidgetsDeepCountListComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
	public final static int SLEEP_AFTER_EVENT = 4000;
	public final static int SLEEP_AFTER_RESTART = 4000;

//	public final static String PACKAGE_NAME = "com.porfirio.orariprocida2011";
//	public final static String CLASS_NAME = "com.porfirio.orariprocida2011.OrariProcida2011Activity";
//	public final static String FILE_NAME = "orariprocida2011.xml";
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepCountListComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 4000;
//	public final static int SLEEP_AFTER_RESTART = 4000;	
	
//	public final static String PACKAGE_NAME = "ee.smkv.calc.loan";
//	public final static String CLASS_NAME = "ee.smkv.calc.loan.MainActivity";
//	public final static String FILE_NAME = "simpleLoanCalculator.xml";
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepCountListComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, BUTTON, LIST_VIEW, MENU_VIEW, IMAGE_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 6000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	static {
//		UserFactory.addEvent(CLICK, BUTTON, TEXT_VIEW);
//	}
	

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
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not 

//	// API Demos
//	
//	public final static String PACKAGE_NAME = "com.example.android.apis";
//	public final static String CLASS_NAME = "com.example.android.apis.ApiDemos";
//	public final static String FILE_NAME = "apidemos.xml"; // Output
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
//	public final static String FILE_NAME = "guess.xml"; // Output
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
//	public final static String FILE_NAME = "loan.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(TEXT_VIEW, DIALOG_VIEW, MENU_VIEW, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 0;

	/*
	 * 				Default Parameters
	 */
	
	// Strategy Parameters
	public final static int MAX_NUM_TRACES = 0; // After performing this amount of traces, the crawler exits (0 = no length limit)
	public final static int PAUSE_AFTER_TRACES = 0; // After performing this amount of traces, the crawler pauses (0 = no pause)
	public final static long MAX_TIME_CRAWLING = 0; // In seconds (0 = no time limit)
	public final static long PAUSE_AFTER_TIME = 0; // In seconds (0 = no pause)
	public final static int TRACE_MAX_DEPTH = 0; // Max number of transitions in a trace (0 = no depth limit)
	public final static boolean CHECK_FOR_TRANSITION = false;
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
	public final static StrategyCriteria[] ADDITIONAL_CRITERIAS = new StrategyCriteria[] {};
	
	// Persistence Parameters
	public final static int MAX_TRACES_IN_RAM = 20; // After performing this amount of traces, the crawler saves to disk, empties the session and continues (0 = keep all in RAM)
	public final static boolean ENABLE_RESUME = false;
	public final static String TASK_LIST_FILE_NAME = "tasklist.xml"; // Save state for resume
	public final static String ACTIVITY_LIST_FILE_NAME = "activities.xml"; // Save state for resume
	public final static String PARAMETERS_FILE_NAME = "parameters.obj";
//	public final static String FILE_NAME = "guitree.xml"; // Output
	
	// User/Planner Parameters
	public final static int MAX_EVENTS_PER_WIDGET = 2; // For GroupViews (0 = try all items in the group)
	public final static int MAX_TASKS_PER_EVENT = 0; // How many input sequences to generate for each event on a widget; 0 = no limit
	public final static int PLANNER = 2;// It's only used when MAX_NUM_TRACES == 0 												  									
	//PLANNER = 1: CombinationPlanner generates all combinations of the input widgets
	//PLANNER = 2: AdiacentPlanner generates all adiacent combinations of the input widgets
	//PLANNER = 3: MinimalCoverageOfValuePlanner generates minimum number of combinations to assure total coverage of input values (of widgets)
	//PLANNER = incorrect value: MinimalCoverageOfValuePlanner as default planner
	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not
	public final static boolean BACK_BUTTON_EVENT = true;
	public final static boolean MENU_EVENTS = true;
	public final static boolean TAB_EVENTS_START_ONLY = true; // true -> click on tabs only on the start activity
	public final static boolean LONG_CLICK_LIST_EVENT = true;
	public final static boolean LONG_CLICK_EVENT = false;
	public final static boolean SCROLL_DOWN_EVENT = false;
	public final static long RANDOM_SEED = 93874383493L; // 0 = Random
	public final static InteractorAdapter[] ADDITIONAL_EVENTS = new InteractorAdapter[] {};
	public final static InteractorAdapter[] ADDITIONAL_INPUTS = new InteractorAdapter[] {};

	// Crawling Parameters
	public final static boolean IN_AND_OUT_FOCUS = true;	
	
	// More Parameters
	public final static boolean RETRY_FAILED_TRACES = true; // Crashed and failed traces are retried once in case the failure had an asynchronous cause
	public final static int SLEEP_ON_THROBBER = 30000; // How long to wait on spinning wheels (in ms -- 0 = don't wait)
	public final static String XML_BODY_BEGIN = "    <TRACE";
	public final static String XML_BODY_END = "/TRACE>";
	public final static CrawlerLog LOGGER = new SessionLogger();

	public static Class<?> theClass;
	static {
		try {
			theClass = Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}