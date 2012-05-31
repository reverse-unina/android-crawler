package com.nofatclips.crawler.guitree;

import static com.nofatclips.crawler.Resources.ACTIVITY_DESCRIPTION_IN_SESSION;
import static com.nofatclips.crawler.Resources.ACTIVITY_LIST_FILE_NAME;
import static com.nofatclips.crawler.Resources.ADDITIONAL_CRITERIAS;
import static com.nofatclips.crawler.Resources.CHECK_FOR_TRANSITION;
import static com.nofatclips.crawler.Resources.CLASS_NAME;
import static com.nofatclips.crawler.Resources.COMPARATOR;
import static com.nofatclips.crawler.Resources.EXPLORE_ONLY_NEW_STATES;
import static com.nofatclips.crawler.Resources.IN_AND_OUT_FOCUS;
import static com.nofatclips.crawler.Resources.MAX_NUM_TRACES;
import static com.nofatclips.crawler.Resources.MAX_TIME_CRAWLING;
import static com.nofatclips.crawler.Resources.MAX_TRACES_IN_RAM;
import static com.nofatclips.crawler.Resources.PACKAGE_NAME;
import static com.nofatclips.crawler.Resources.PAUSE_AFTER_TIME;
import static com.nofatclips.crawler.Resources.PAUSE_AFTER_TRACES;
import static com.nofatclips.crawler.Resources.SLEEP_AFTER_EVENT;
import static com.nofatclips.crawler.Resources.SLEEP_AFTER_RESTART;
import static com.nofatclips.crawler.Resources.SLEEP_ON_THROBBER;
import static com.nofatclips.crawler.Resources.TRACE_MAX_DEPTH;
import static com.nofatclips.crawler.Resources.USE_GPS;
import static com.nofatclips.crawler.Resources.TEST_LOCATION_PROVIDER;

import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import android.content.Context;
import android.location.LocationManager;

import com.nofatclips.androidtesting.guitree.GuiTree;
import com.nofatclips.androidtesting.model.Session;
import com.nofatclips.crawler.Engine;
import com.nofatclips.crawler.automation.Automation;
import com.nofatclips.crawler.automation.BasicRestarter;
import com.nofatclips.crawler.automation.SimpleTypeDetector;
import com.nofatclips.crawler.filters.AllPassFilter;
import com.nofatclips.crawler.filters.FormFilter;
import com.nofatclips.crawler.model.Filter;
import com.nofatclips.crawler.model.UserAdapter;
import com.nofatclips.crawler.planning.SimplePlanner;
import com.nofatclips.crawler.planning.TraceDispatcher;
import com.nofatclips.crawler.planning.UserFactory;
import com.nofatclips.crawler.storage.PersistenceFactory;
import com.nofatclips.crawler.strategy.StrategyFactory;

public class GuiTreeEngine extends Engine {

	public GuiTreeEngine () {
		super ();
		
		setScheduler(new TraceDispatcher());
		
		this.theAutomation = new Automation();
		this.theRestarter = new BasicRestarter();
		this.theAutomation.setRestarter(theRestarter);
		setRobot (this.theAutomation);
		setExtractor (this.theAutomation);
		setImageCaptor(this.theAutomation);
		
		try {
			GuiTree.setValidation(false);
			this.guiAbstractor = new GuiTreeAbstractor();
			this.theGuiTree = this.guiAbstractor.getTheSession();
			GregorianCalendar c=new GregorianCalendar();
			theGuiTree.setDateTime(c.getTime().toString());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAbstractor(this.guiAbstractor);
		setSession (this.theGuiTree);

		SimplePlanner p = new SimplePlanner();

		Filter inputFilter = new FormFilter();
		p.setInputFilter (inputFilter);
		this.guiAbstractor.addFilter (inputFilter);

		Filter eventFilter = new AllPassFilter(); //SimpleEventFilter();
		p.setEventFilter (eventFilter);
		this.guiAbstractor.addFilter (eventFilter);
		this.guiAbstractor.setTypeDetector(new SimpleTypeDetector());
		
		this.user = UserFactory.getUser(this.guiAbstractor);
		p.setUser(user);
		p.setFormFiller(user);
		p.setAbstractor(this.guiAbstractor);
		setPlanner (p);
		
		StrategyFactory sf = new StrategyFactory(COMPARATOR, ADDITIONAL_CRITERIAS);
		sf.setDepth(TRACE_MAX_DEPTH);
		sf.setMaxTraces(MAX_NUM_TRACES);
		sf.setMaxSeconds(MAX_TIME_CRAWLING);
		sf.setPauseSeconds(PAUSE_AFTER_TIME);
		sf.setCheckTransitions(CHECK_FOR_TRANSITION);
		sf.setPauseTraces(PAUSE_AFTER_TRACES);
		sf.setExploreNewOnly(EXPLORE_ONLY_NEW_STATES);
		setStrategy (sf.getStrategy());

		// Last object to instantiate: the other components register as listeners on the factory class
		PersistenceFactory pf = new PersistenceFactory (this.theGuiTree, getScheduler(), getStrategy());
		setPersistence (pf.getPersistence());
		
	}
	
	protected void setUp () {
		try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		theRestarter.setRestartPoint(theAutomation.getActivity());
		theGuiTree.setAppName(theAutomation.getAppName());
		theGuiTree.setSleepAfterEvent(SLEEP_AFTER_EVENT);
		theGuiTree.setSleepAfterRestart(SLEEP_AFTER_RESTART);
		theGuiTree.setSleepOnThrobber(SLEEP_ON_THROBBER);
		theGuiTree.setClassName(CLASS_NAME);
		theGuiTree.setPackageName(PACKAGE_NAME);
		theGuiTree.setComparationWidgets(COMPARATOR.describe());
		theGuiTree.setInAndOutFocus(IN_AND_OUT_FOCUS);
		if (!ACTIVITY_DESCRIPTION_IN_SESSION) {
			theGuiTree.setStateFileName(ACTIVITY_LIST_FILE_NAME);
		}
		
		/** @author nicola amatucci */
		if (USE_GPS)
		{
			//attivo il LocationManager e il provider di test
			theAutomation.locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
			theAutomation.locationManager.addTestProvider(TEST_LOCATION_PROVIDER, false, false, false, false, true, true, true, 0, 5);
			theAutomation.locationManager.setTestProviderEnabled(TEST_LOCATION_PROVIDER, true);
		}
		/** @author nicola amatucci */
	}
	
	
	/** @author nicola amatucci */
	@Override
	protected void tearDown() throws Exception
	{
		theAutomation.locationManager.removeTestProvider(TEST_LOCATION_PROVIDER);
		super.tearDown();
	}
	/** @author nicola amatucci */

	public Session getNewSession() {
		try {
			return new GuiTree();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean stepPersistence () {
		return (MAX_TRACES_IN_RAM>0);
	}
	
	private Automation theAutomation;
	private GuiTreeAbstractor guiAbstractor;
	private UserAdapter user;
	private BasicRestarter theRestarter;
	private GuiTree theGuiTree;
//	private Persistence diskWriter;	
}
