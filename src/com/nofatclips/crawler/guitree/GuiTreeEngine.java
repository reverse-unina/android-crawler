package com.nofatclips.crawler.guitree;

import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import com.nofatclips.androidtesting.guitree.GuiTree;
import com.nofatclips.crawler.Engine;
import com.nofatclips.crawler.automation.Automation;
import com.nofatclips.crawler.automation.BasicRestarter;
import com.nofatclips.crawler.automation.SimpleTypeDetector;
import com.nofatclips.crawler.filters.FormFilter;
import com.nofatclips.crawler.filters.SimpleEventFilter;
import com.nofatclips.crawler.model.Filter;
import com.nofatclips.crawler.planning.AlternativePlanner;
import com.nofatclips.crawler.planning.AlternativeUser;
import com.nofatclips.crawler.planning.TraceDispatcher;
import com.nofatclips.crawler.storage.DiskPersistence;
import com.nofatclips.crawler.storage.StepDiskPersistence;
import com.nofatclips.crawler.strategy.MaxStepsTermination;
import com.nofatclips.crawler.strategy.SimpleStrategy;

import static com.nofatclips.crawler.Resources.*;

public class GuiTreeEngine extends Engine {

	public GuiTreeEngine () {
		super ();
				
		setScheduler(new TraceDispatcher());
		
		this.theAutomation = new Automation();
		this.theRestarter = new BasicRestarter();
		this.theAutomation.setRestarter(theRestarter);
		setRobot (this.theAutomation);
		setExtractor (this.theAutomation);
		
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

		AlternativePlanner p = new AlternativePlanner();

		Filter inputFilter = new FormFilter();
		p.setInputFilter (inputFilter);
		this.guiAbstractor.addFilter (inputFilter);

		Filter eventFilter = new SimpleEventFilter();
		p.setEventFilter (eventFilter);
		this.guiAbstractor.addFilter (eventFilter);
		this.guiAbstractor.setTypeDetector(new SimpleTypeDetector());
		
		this.user = new AlternativeUser(this.guiAbstractor);
		p.setUser(user);
		p.setFormFiller(user);
		p.setAbstractor(this.guiAbstractor);
		setPlanner (p);
		
		setStrategy(new SimpleStrategy (COMPARATOR));
		if (MAX_NUM_TRACES>0) {
			getStrategy().addTerminationCriteria(new MaxStepsTermination(MAX_NUM_TRACES));
		}
		
		d = (stepPersistence())?new StepDiskPersistence (MAX_TRACES_IN_RAM):new DiskPersistence();
		d.setSession(this.theGuiTree);
		setPersistence (d);
		
	}
	
	protected void setUp () {
		try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.setContext(theAutomation.getActivity());
		theRestarter.setRestartPoint(theAutomation.getActivity());
		theGuiTree.setAppName(theAutomation.getAppName());
		theGuiTree.setSleepAfterEvent(SLEEP_AFTER_EVENT);
		theGuiTree.setSleepAfterRestart(SLEEP_AFTER_RESTART);
		theGuiTree.setClassName(CLASS_NAME);
		theGuiTree.setPackageName(PACKAGE_NAME);
	}
	
	public boolean stepPersistence () {
		return (MAX_TRACES_IN_RAM>0);
	}
	
	private Automation theAutomation;
	private GuiTreeAbstractor guiAbstractor;
	private AlternativeUser user;
	private BasicRestarter theRestarter;
	private GuiTree theGuiTree;
	private DiskPersistence d;
	
}
