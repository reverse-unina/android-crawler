package com.nofatclips.crawler;

import com.nofatclips.crawler.model.Comparator;
import com.nofatclips.crawler.storage.*;
import com.nofatclips.crawler.strategy.*;

import static com.nofatclips.androidtesting.model.SimpleType.*;

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

	public final static String PACKAGE_NAME = "com.evancharlton.mileage";
	public final static String CLASS_NAME = "com.evancharlton.mileage.Mileage";
	public final static String FILE_NAME = "mileage.xml"; // Output
	public final static Comparator COMPARATOR = new CustomWidgetsComparator(CustomWidgetsComparator.IGNORE_ACTIVITY_NAME, EDIT_TEXT, BUTTON);
	public final static int SLEEP_AFTER_EVENT = 4000;
	public final static int SLEEP_AFTER_RESTART = 4000;
	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "net.sf.andbatdog.batterydog";
//	public final static String CLASS_NAME = "net.sf.andbatdog.batterydog.BatteryDog";
//	public final static String FILE_NAME = "batterydog.xml"; // Output
//	public final static Comparator COMPARATOR = new NameComparator();
//	public final static int SLEEP_AFTER_EVENT = 0;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "org.wordpress.android";
//	public final static String CLASS_NAME = "org.wordpress.android.splashScreen";
//	public final static String FILE_NAME = "wordpress.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 4000;
//	public final static int SLEEP_AFTER_RESTART = 6000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "com.bwx.bequick";
//	public final static String CLASS_NAME = "com.bwx.bequick.ShowSettingsActivity";
//	public final static String FILE_NAME = "quick_settings.xml"; // Output
//	public final static Comparator COMPARATOR = new EditTextComparator();
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 0;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "net.bible.android.activity";
//	public final static String CLASS_NAME = "net.bible.android.activity.StartupActivity";
//	public final static String FILE_NAME = "bible.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 20000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not 

//	public final static String PACKAGE_NAME = "com.ichi2.anki";
//	public final static String CLASS_NAME = "com.ichi2.anki.StudyOptions";
//	public final static String FILE_NAME = "anki_guitree.xml"; // Output
//	public final static Comparator COMPARATOR = new ButtonComparator();
//	public final static int SLEEP_AFTER_EVENT = 10000;
//	public final static int SLEEP_AFTER_RESTART = 4000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not 

//	public final static String PACKAGE_NAME = "com.example.android.apis";
//	public final static String CLASS_NAME = "com.example.android.apis.ApiDemos";
//	public final static String FILE_NAME = "apidemos.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsDeepComparator(TEXT_VIEW);
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not 

//	public final static String PACKAGE_NAME = "com.googlecode.andoku";
//	public final static String CLASS_NAME = "com.googlecode.andoku.MainActivity";
//	public final static String FILE_NAME = "andoku.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "de.fmaul.android.cmis";
//	public final static String CLASS_NAME = "de.fmaul.android.cmis.HomeActivity";
//	public final static String FILE_NAME = "cmis.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 2000;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = false; // Whether to inject events on widgets without ID or not

//	public final static String PACKAGE_NAME = "cz.romario.opensudoku";
//	public final static String CLASS_NAME = "cz.romario.opensudoku.gui.FolderListActivity";
//	public final static String FILE_NAME = "opensudoku.xml"; // Output
//	public final static Comparator COMPARATOR = new CustomWidgetsComparator(EDIT_TEXT, BUTTON);
//	public final static int SLEEP_AFTER_EVENT = 200;
//	public final static int SLEEP_AFTER_RESTART = 2000;
//	public final static boolean EVENT_WHEN_NO_ID = true; // Whether to inject events on widgets without ID or not

	public final static int MAX_NUM_TRACES = 13; // After performing this amount of traces, the crawler exits
	public final static int MAX_TRACES_IN_RAM = 0; // After performing this amount of traces, the crawler saves to disk, empties the session and continues (0 = keep all in RAM)
	public final static int MAX_EVENTS_PER_WIDGET = 1;
	public final static boolean BACK_BUTTON_EVENT = true;
	public final static boolean SCROLL_DOWN_EVENT = false;
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