package com.nofatclips.crawler;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.androidtesting.model.Trace;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.ActivityDescription;
import com.nofatclips.crawler.model.Extractor;
import com.nofatclips.crawler.model.Persistence;
import com.nofatclips.crawler.model.Plan;
import com.nofatclips.crawler.model.Planner;
import com.nofatclips.crawler.model.Robot;
import com.nofatclips.crawler.model.Strategy;
import com.nofatclips.crawler.planning.TraceDispatcher;

import android.test.ActivityInstrumentationTestCase2;

import static com.nofatclips.crawler.Resources.*;

@SuppressWarnings("rawtypes")
public abstract class Engine extends ActivityInstrumentationTestCase2 {
	
	@SuppressWarnings("unchecked")
	public Engine() {
		super(PACKAGE_NAME,theClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getRobot().bind(this);
		getExtractor().extractState();
		ActivityDescription d = getExtractor().describeActivity();
		getAbstractor().setBaseActivity(d);
		planFirstTests(getAbstractor().getBaseActivity());
		getStrategy().addState(getAbstractor().getBaseActivity());
		getPersistence().setFileName(FILE_NAME);
	}
	
	public void testAndCrawl() {
		for (Trace theTask: getScheduler()) {
			getStrategy().setTask(theTask);
			getRobot().process(theTask);
			ActivityDescription d = getExtractor().describeActivity();
			ActivityState theActivity = getAbstractor().createActivity(d);
			getStrategy().compareState(theActivity);
			if (!getStrategy().checkForTransition()) continue;
			theTask.setFinalActivity (theActivity);
			getPersistence().addTrace(theTask);
			if (theActivity.getId() != "exit") {
				if (getStrategy().checkForExploration()) {
					planTests(theTask, theActivity);
				}
			}
			if (getStrategy().checkForTermination(theActivity)) break;
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		getPersistence().save();
		getRobot().finalize();
		super.tearDown();
	}

	private void planFirstTests (ActivityState theActivity) {
		Plan thePlan = getPlanner().getPlanForBaseActivity(theActivity);
		planTests (null, thePlan);
	}
	
	private void planTests (Trace theTask, ActivityState theActivity) {
		Plan thePlan = getPlanner().getPlanForActivity(theActivity); // numTabs=1 => Ignore TabHost
		planTests (theTask, thePlan);
	}
	
	private void planTests (Trace theTask, Plan thePlan) {
		for (Transition t: thePlan) {
			Trace newTrace = getAbstractor().createTrace(theTask, t);
			newTrace.setId(nextId());
			getScheduler().addTasks(newTrace);
		}		
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

	private Robot theRobot;
	private Extractor theExtractor;
	private Abstractor theAbstractor;
	private Planner thePlanner;
	private TraceDispatcher theScheduler;
	private Strategy theStrategy;
	private Persistence thePersistence;
	private Session theSession;
	private int id = 0;

}
