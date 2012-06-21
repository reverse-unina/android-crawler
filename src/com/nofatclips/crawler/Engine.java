package com.nofatclips.crawler;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Element;

import com.nofatclips.androidtesting.model.*;
import com.nofatclips.androidtesting.xml.XmlGraph;
import com.nofatclips.crawler.automation.ScreenshotFactory;
import com.nofatclips.crawler.model.*;
import com.nofatclips.crawler.planning.TraceDispatcher;
import com.nofatclips.crawler.storage.PersistenceFactory;
import com.nofatclips.crawler.storage.ResumingPersistence;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import static com.nofatclips.crawler.Resources.*;

@SuppressWarnings("rawtypes")
public abstract class Engine extends ActivityInstrumentationTestCase2 implements SaveStateListener {
	
	@SuppressWarnings("unchecked")
	public Engine() {
		super(PACKAGE_NAME,theClass);
		PersistenceFactory.registerForSavingState(this);
	}
	
	public abstract Session getNewSession();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (getImageCaptor()!=null) {
			ScreenshotFactory.setImageCaptor(getImageCaptor());
		}
		getRobot().bind(this);
		getExtractor().extractState();
		Activity a = getExtractor().getActivity();
		getPersistence().setFileName(FILE_NAME);
		getPersistence().setContext(a);
		ActivityDescription d = getExtractor().describeActivity();
		getAbstractor().setBaseActivity(d);
		if (!resume()) {
			Log.i("nofatclips", "Starting a new session");
			ActivityState baseActivity = getAbstractor().getBaseActivity(); 
			getStrategy().addState(baseActivity);
			if (screenshotEnabled()) {
				takeScreenshot (baseActivity);
			}
			planFirstTests(baseActivity);
		}
	}

	public void testAndCrawl() {
		for (Trace theTask: getScheduler()) {
			GregorianCalendar c=new GregorianCalendar();
			theTask.setDateTime(c.getTime().toString());
			getStrategy().setTask(theTask);
			getRobot().process(theTask);
			ActivityDescription d = getExtractor().describeActivity();
			ActivityState theActivity = getAbstractor().createActivity(d);
			getStrategy().compareState(theActivity);
			if (screenshotNeeded()) {
				takeScreenshot(theActivity);
			}
			getRobot().wait(SLEEP_AFTER_TASK);
			if (!getStrategy().checkForTransition()) continue;
			getAbstractor().setFinalActivity (theTask, theActivity);
			getPersistence().addTrace(theTask);
			if (!(theActivity.isExit()) && getStrategy().checkForExploration()) {
				planTests(theTask, theActivity);
			}
			if ( (getStrategy().checkForTermination()) || (getStrategy().checkForPause()) ) break;
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		if ((getStrategy().getTask() != null) && (getStrategy().getTask().isFailed())) {
			getSession().addFailedTrace(getStrategy().getTask());
		}
		getPersistence().save();
		getRobot().finalize();
		super.tearDown();
	}
	
	public boolean resume() {
		boolean flag = ENABLE_RESUME;
		if (!flag) {
			Log.i("nofatclips", "Resume not enabled.");
			return false;
		}
		if (!(getPersistence() instanceof ResumingPersistence)) {
			Log.i("nofatclips", "The instance of Persistence does not implement Resuming.");
			return false;
		}
		
		ResumingPersistence r = (ResumingPersistence)getPersistence();
		if (!r.canHasResume()) return false;
		Log.i("nofatclips", "Attempting to resume previous session");
		List<String> entries;
		Session sandboxSession = getNewSession();
		Element e;
		
		// Importing task list
		entries = r.readTaskFile();
		List<Trace> taskList = new ArrayList<Trace>();
		Trace t;
		for (String trace: entries) {
			sandboxSession.parse(trace);
			e = ((XmlGraph)sandboxSession).getDom().getDocumentElement();
			t = getAbstractor().importTask (e);
			if (t.isFailed()) {
				Log.i("nofatclips", "Importing crashed trace #" + t.getId() + " from disk");
				getSession().addCrashedTrace(t);
			} else {
				Log.i("nofatclips", "Importing trace #" + t.getId() + " from disk");
				taskList.add(t);
			}
		}
		getScheduler().addTasks(taskList);
		
		// Importing activity list
		if (getStrategy().getComparator() instanceof StatelessComparator) {
			Log.i("nofatclips","Stateless comparator: the state file will not be loaded.");
		} else {
			entries = r.readStateFile();
			List<ActivityState> stateList = new ArrayList<ActivityState>();
			ActivityState s;
			for (String state: entries) {
				sandboxSession.parse(state);
				e = ((XmlGraph)sandboxSession).getDom().getDocumentElement();
				s = getAbstractor().importState (e);
				stateList.add(s);
				Log.i("nofatclips", "Imported activity state " + s.getId() + " from disk");
			}
			for (ActivityState state: stateList) {
				getStrategy().addState(state);
			}
		}

		r.loadParameters();
		r.setNotFirst();
		r.saveStep();

		return true;
	}
	
	private void planFirstTests (ActivityState theActivity) {
		Plan thePlan = getPlanner().getPlanForBaseActivity(theActivity);
		planTests (null, thePlan);
	}
	
	private void planTests (Trace theTask, ActivityState theActivity) {
		Plan thePlan = getPlanner().getPlanForActivity(theActivity);
		planTests (theTask, thePlan);
	}
	
	private void planTests (Trace theTask, Plan thePlan) {
		for (Transition t: thePlan) {
			Trace newTrace = getAbstractor().createTrace(theTask, t);
			newTrace.setId(nextId());
			getScheduler().addTasks(newTrace);
		}		
	}
	
	public SessionParams onSavingState () {
		return new SessionParams (PARAM_NAME, this.id);
	}
	
	public void onLoadingState(SessionParams sessionParams) {
		this.id = sessionParams.getInt(PARAM_NAME);
		Log.d("nofatclips","Restored trace count to " + this.id);
	}
	
	public Robot getRobot() {
		return this.theRobot;
	}

	public void setRobot(Robot theRobot) {
		this.theRobot = theRobot;
	}

	public Extractor getExtractor() {
		return this.theExtractor;
	}

	public void setExtractor(Extractor theExtractor) {
		this.theExtractor = theExtractor;
	}
	
	public ImageCaptor getImageCaptor() {
		return this.theImageCaptor;
	}

	public void setImageCaptor(ImageCaptor theImageCaptor) {
		this.theImageCaptor = theImageCaptor;
	}

	public Abstractor getAbstractor() {
		return this.theAbstractor;
	}

	public void setAbstractor(Abstractor theAbstractor) {
		this.theAbstractor = theAbstractor;
	}

	public Planner getPlanner() {
		return this.thePlanner;
	}

	public void setPlanner(Planner thePlanner) {
		this.thePlanner = thePlanner;
	}
	
	public TraceDispatcher getScheduler () {
		return this.theScheduler;
	}
	
	public void setScheduler (TraceDispatcher theScheduler) {
		this.theScheduler = theScheduler;
	}

	public Strategy getStrategy() {
		return this.theStrategy;
	}

	public void setStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;
	}
	
	public Persistence getPersistence() {
		return this.thePersistence;
	}

	public void setPersistence(Persistence thePersistence) {
		this.thePersistence = thePersistence;
	}
	
	public Session getSession() {
		return this.theSession;
	}

	public void setSession(Session theSession) {
		this.theSession = theSession;
	}
	
	protected String nextId () {
		int num = id;
		id++;
		return String.valueOf(num);
	}
	
	public String getListenerName () {
		return ACTOR_NAME;
	}
	
	public boolean retry() {
		return RETRY_FAILED_TRACES;
	}
	
	public boolean screenshotEnabled() {
		return SCREENSHOT_FOR_STATES;
	}
	
	public boolean screenshotEveryTrace() {
		return !SCREENSHOT_ONLY_NEW_STATES;
	}
	
	public boolean screenshotNeeded() {
		if (!screenshotEnabled()) return false; // Function disable, always return false
		if (screenshotEveryTrace()) return true; // Function enable for all states, always return true
		return !(getStrategy().isLastComparationPositive()); // Function enabled for new states only: return true if comparation was false
	}
	
	public String screenshotName (String stateId) {
		return stateId + "." + ScreenshotFactory.getFileExtension();
	}
	
	private void takeScreenshot(ActivityState theActivity) {
		if (theActivity.isExit()) return;
		String fileName = screenshotName(theActivity.getUniqueId());
		if (ScreenshotFactory.saveScreenshot(fileName)) {
			theActivity.setScreenshot(fileName);
		}
	}

	public final static String ACTOR_NAME = "Engine";
	
	private Robot theRobot;
	private Extractor theExtractor;
	private Abstractor theAbstractor;
	private Planner thePlanner;
	private TraceDispatcher theScheduler;
	private Strategy theStrategy;
	private Persistence thePersistence;
	private Session theSession;
	private ImageCaptor theImageCaptor;
	
	private final static String PARAM_NAME = "taskId";
	private int id = 0;

}
