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

public class MinimalCoverageOfValuesPlanner implements Planner {

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
		Log.i("castigliafrancesco", "Planning for new Activity " + a.getName());
		boolean semaphore=true;
		List<UserInput>[] mylists=null;
		int indexes[][]=null;
		List[] mask=null;
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
										
					//Calculation of the mylits sizes
					int [] sizesOfmylists = new int[numWidgets];				
					for(int i=0;i<numWidgets;i++){
						sizesOfmylists[i]=mylists[i].size();
					}				
					indexes=calculateIndexesOfCombinations(sizesOfmylists);				
					
					//Calculation of the index mask
					mask=new List[numWidgets];
					for(int  i=0;i<numWidgets;i++){
						mask[i]=new ArrayList();
						for(int  j=0;j<sizesOfmylists[i];j++){
							mask[i].add(1);
						}						
					}
					
					for(int i=0;i<indexes.length;i++){
						String s=new String();
						for(int k=0;k<numWidgets;k++){
							s = s.concat(Integer.toString((indexes[i][k])));
						}
						Log.i("castigliafrancesco", s);
					}				
					
					semaphore=false;
				}
				
				//Questo ciclo crea le combinazioni sufficienti a garantire 
				//almeno una volta la copertura di ciascun valore di input
				//facendo attenzione ad usare tutti gli inputs contenuti in
				//mylists ed a clonare gli stessi dopo che sono stati
				//usati in qualche combinazione precedente.
				//Infatti è importante associare almeno tutti gli input di
				//mylists a qualche transizione con createStep altrimenti
				//restano nell'xml senza elemento padre "transition" e quindi
				//manderebbero in crisi il crawler che eseguire solo le 
				//iterazioni di input senza il successivo evento.
				//A tal scopo marchiamo con -1 l'elemento input già adoperato
				//servendosi della maschera di indici corrispondente
				Collection<UserInput> inputsbase=new ArrayList<UserInput>();
				for(int i=0;i<indexes.length;i++){
					inputsbase.clear();
					for(int k=0;k<numWidgets;k++){													
						UserInput inp=mylists[k].get(indexes[i][k]);
						if(mask[k].get(indexes[i][k]).equals(-1)){
							//Occorre clonarlo perchè già usato
							inputsbase.add(((TestCaseInput) inp).clone());
						}
						else{							
							//Non occorre clonarlo perchè non è stato usato
							inputsbase.add(inp);//Viene usato
							mask[k].set(indexes[i][k],-1);  //Viene marcato come già usato
						}	
					}
					Transition t;
					if(i==0){						
						t = getAbstractor().createStep(a, inputsbase, evt);
					}
					else{
						t = getAbstractor().createStep(a, inputsbase, ((TestCaseEvent) evt).clone());
					}					
					p.addTask(t);							
					Log.i("castigliafrancesco", "MinimalCoverageOfValuesPlanner: Create trace for activity" + a.getName()+" Combination of "+numWidgets+" input widgets");
				}
			}
		}						
		UserEvent evt;
        Transition t;

		// Special handling for pressing back button
		if (BACK_BUTTON_EVENT && allowGoBack) {
			evt = getAbstractor().createEvent(null, BACK);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("castigliafrancesco", "Created trace to press the back button");
			p.addTask(t);
		}
		
		if (MENU_EVENTS) {
			evt = getAbstractor().createEvent(null, OPEN_MENU);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("castigliafrancesco", "Created trace to press the menu button");
			p.addTask(t);
		}

		// Special handling for scrolling down
		if (SCROLL_DOWN_EVENT) {
			evt = getAbstractor().createEvent(null, SCROLL_DOWN);
			t = getAbstractor().createStep(a, new HashSet<UserInput>(), evt);
			Log.i("castigliafrancesco", "Created trace to perform scrolling down");
			p.addTask(t);
		}

		return p;
	}
	
	private int[][] calculateIndexesOfCombinations(int[] sizesOfmylists) {
		int numWidgets=sizesOfmylists.length;		
		int numComb=1;
		for(int i=0;i<numWidgets;i++){			
			if(sizesOfmylists[i]>numComb)
				numComb=sizesOfmylists[i];
		}
		int indexes[][]=new int[numComb][numWidgets];
		for(int i=0;i<numComb;i++){
			for(int j=0;j<numWidgets;j++){
				indexes[i][j]=0;
			}
		}
		for(int j=0;j<numWidgets;j++){
			for(int i=0;i<sizesOfmylists[j];i++){
					indexes[i][j]=i;
			}										
		}
		return indexes;
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