package com.nofatclips.crawler.planning;

import static com.nofatclips.androidtesting.model.InteractionType.BACK;
import static com.nofatclips.androidtesting.model.InteractionType.OPEN_MENU;
import static com.nofatclips.androidtesting.model.InteractionType.SCROLL_DOWN;
import static com.nofatclips.crawler.Resources.BACK_BUTTON_EVENT;
import static com.nofatclips.crawler.Resources.MENU_EVENTS;
import static com.nofatclips.crawler.Resources.SCROLL_DOWN_EVENT;
import static com.nofatclips.crawler.Resources.TAB_EVENTS_START_ONLY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.guitree.TestCaseEvent;
import com.nofatclips.androidtesting.guitree.TestCaseInput;
import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.Transition;
import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.EventHandler;
import com.nofatclips.crawler.model.Filter;
import com.nofatclips.crawler.model.InputHandler;
import com.nofatclips.crawler.model.Plan;
import com.nofatclips.crawler.model.Planner;
import com.nofatclips.crawler.model.UserAdapter;

public class AlternativePlanner implements Planner {

	public final static boolean ALLOW_SWAP_TAB = true;
	public final static boolean NO_SWAP_TAB = false;
	public final static boolean ALLOW_GO_BACK = true;
	public final static boolean NO_GO_BACK = false;
	
	@Override
	public Plan getPlanForActivity (ActivityState a) {
		return getPlanForActivity(a, !TAB_EVENTS_START_ONLY, ALLOW_GO_BACK);
	}

	@Override
	public Plan getPlanForBaseActivity (ActivityState a) {
		return getPlanForActivity(a, ALLOW_SWAP_TAB, NO_GO_BACK);
	}

	public Plan getPlanForActivity (ActivityState a, boolean allowSwapTabs, boolean allowGoBack) {
		Plan p = new Plan();
		Log.i("nofatclips", "Planning for new Activity " + a.getName());
		boolean semaphore=true;
		List<UserInput>[] mylists=null;
		int supNumWidgets=0;
		int numWidgets=0;
		for (WidgetState w: getEventFilter()) {
			Collection<UserEvent> events = getUser().handleEvent(w);
			for (UserEvent evt: events) {
				if (evt == null) continue;				
				//Il semaforo è indispensabile per creare una sola volta la
				//lista di valori di input per ciascun widget con il metodo
				//handleInput.
				//Cosa ben più importante è che i valori random generati devono 
				//restare uguali per i diversi task eseguibili sull'activity
				//Infatti potrebbero esserci più eventi nell' activity in
				//questione ed è importante che i tasks vengano costruiti con
				//combinazioni di input derivate da liste che in corrispondenza
				//di valori random restino le stesse
				if(semaphore){
					//Calculating upper limit on the number of widgets
					supNumWidgets=0;
					Iterator<WidgetState> it=a.iterator();
					while(it.hasNext()){
						it.next();
						supNumWidgets++;
					}
					
					//Loading of the input lists for each widget
					mylists=new List[supNumWidgets];				
					numWidgets=0;
					for(WidgetState formWidget: getInputFilter()){
						List<UserInput> list=getFormFiller().handleInput(formWidget);
						if(list.size()!=0){						
							mylists[numWidgets]=list;
							numWidgets++;
						}						
					}
					semaphore=false;
				}
				else {
					//Cloning of the input list for each widget
					for(int i=0;i<numWidgets;i++){
						for(int k=0;k<mylists[i].size();k++){
							mylists[i].set(k,((TestCaseInput)mylists[i].get(k)).clone());
						}
					}
				}
				
				//Creation of first combination
				Collection<UserInput> inputsbase=new ArrayList<UserInput>();
				for(int i=0;i<numWidgets;i++){
						inputsbase.add(mylists[i].get(0));
				}				
				Transition t = getAbstractor().createStep(a, inputsbase, evt);				
				p.addTask(t);
				Log.i("castigliafrancesco", "Create trace for activity" + a.getName()+" First Combination of "+numWidgets+" input widgets");

				//Creation of the other combinations
				Collection<UserInput> tempinputsbase=new ArrayList<UserInput>();
				for(int i=0;i<numWidgets;i++){									
					for(int j=1;j<=mylists[i].size()-1;j++){
						tempinputsbase.clear();
						for(int l=0;l<numWidgets;l++){
							if(l==i){
								UserInput inp=mylists[i].get(j);
								tempinputsbase.add(inp);
								continue;
							}
							UserInput inp=mylists[l].get(0);
							tempinputsbase.add(((TestCaseInput) inp).clone());
						}	
						Log.i("castigliafrancesco", "Create trace for activity" + a.getName()+" Alternative Combination of input windgets");
						t = getAbstractor().createStep(a, tempinputsbase,((TestCaseEvent) evt).clone());					
						p.addTask(t);				
					}								
				}					
			}
		}						
		UserEvent evt;
        Transition t;

		// Special handling for pressing back button
		if (BACK_BUTTON_EVENT && allowGoBack) {
			evt = getAbstractor().createEvent(null, BACK);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to press the back button");
			p.addTask(t);
		}
		
		if (MENU_EVENTS) {
			evt = getAbstractor().createEvent(null, OPEN_MENU);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to press the menu button");
			p.addTask(t);
		}

		// Special handling for scrolling down
		if (SCROLL_DOWN_EVENT) {
			evt = getAbstractor().createEvent(null, SCROLL_DOWN);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("nofatclips", "Created trace to perform scrolling down");
			p.addTask(t);
		}

		return p;
	}
	
	public Filter getEventFilter() {
		return this.eventFilter;
	}

	public void setEventFilter(Filter eventFilter) {
		this.eventFilter = eventFilter;
	}
	
	public Filter getInputFilter() {
		return this.inputFilter;
	}
	
	public void setInputFilter(Filter inputFilter) {
		this.inputFilter = inputFilter;
	}

	public EventHandler getUser() {
		return this.user;
	}

	public void setUser(EventHandler user) {
		this.user = user;
	}

	public InputHandler getFormFiller() {
		return this.formFiller;
	}

	public void setFormFiller(InputHandler formFiller) {
		this.formFiller = formFiller;
	}

	public Abstractor getAbstractor() {
		return this.abstractor;
	}

	public void setAbstractor(Abstractor abstractor) {
		this.abstractor = abstractor;
	}

	private Filter eventFilter;
	private Filter inputFilter;
	private EventHandler user;
	private InputHandler formFiller;
	private Abstractor abstractor;

}