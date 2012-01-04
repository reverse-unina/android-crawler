package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.nofatclips.androidtesting.model.InteractionType.*;
import static com.nofatclips.androidtesting.model.SimpleType.*;
import static com.nofatclips.crawler.Resources.*;

import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.UserAdapter;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;
import com.nofatclips.crawler.planning.interactors.*;

public class UserFactory {

	public static InteractionMap eventToTypeMap = new InteractionMap();
	public static InteractionMap inputToTypeMap = new InteractionMap();
	public static Map<String,List<String>> vetoesMap = new Hashtable<String,List<String>>();
	
	public final static String ALL = "ALL";

	public enum Category {
		EVENT, INPUT, SPECIAL
	}
	
	public static void addEvent (String eventType, String ... widgetTypes) {
		eventToTypeMap.put(eventType, widgetTypes);
	}

	public static void addInput (String inputType, String ... widgetTypes) {
		inputToTypeMap.put(inputType, widgetTypes);
	}
	
	public static void denyIds (String ... ids) {
		denyIdsForEvent(ALL, ids);
	}

	public static void denyIdsForEvent (String event, String ... ids) {
		if (!vetoesMap.containsKey(event)) {
			vetoesMap.put(event, new ArrayList<String>());
		}
		for (String id: ids) {
			vetoesMap.get(event).add(id);
		}
	}

	public static void denyIds (int ... ids) {
		for (Integer id: ids) {
			denyIdsForEvent(ALL, String.valueOf(id));
		}
	}

	public static void denyInteractionOnIds (String event, int ... ids) {
		for (Integer id: ids) {
			denyIdsForEvent(event, String.valueOf(id));
		}
	}

	public static InteractorAdapter addVetoes (InteractorAdapter i) {
		addVetoes (i, ALL);
		addVetoes (i, i.getInteractionType());
		return i;
	}
	
	public static void addVetoes (InteractorAdapter i, String event) {
		if (vetoesMap.containsKey(event)) {
			i.denyIds(vetoesMap.get(event));
		}		
	}
	
	public static boolean customizeEvent (String interaction) {
		return isRequired (Category.EVENT, interaction);
	}

	public static boolean customizeInput (String interaction) {
		return isRequired (Category.INPUT, interaction);
	}

	public static boolean isRequired (Category c, String interaction) {
		switch (c) {
		case EVENT:
			return UserFactory.eventToTypeMap.containsKey(interaction);
		case INPUT:
			return UserFactory.inputToTypeMap.containsKey(interaction);
		default:
			return false;
		}
	}

	public static String[] typesForEvent (String interaction) {
		return UserFactory.eventToTypeMap.get(interaction);
	}

	public static String[] typesForInput (String interaction) {
		return UserFactory.inputToTypeMap.get(interaction);
	}

	public static boolean doLongClick() {
		return LONG_CLICK_EVENT;
	}

	public static boolean doLongClickOnLists() {
		return LONG_CLICK_LIST_EVENT;
	}

	public static boolean doForceSeed () {
		return (RANDOM_SEED==0);
	}
	
	public static boolean isUserSimple () {
		int m = MAX_TASKS_PER_EVENT;
		return (m == 1);
	}
	
	public static UserAdapter getUser (Abstractor a) {
		
		UserAdapter u;

		if (isUserSimple()) {
			u = (doForceSeed())?new SimpleUser (a):new SimpleUser(a,new Random(RANDOM_SEED));
            // Events - Click
            Clicker c = (customizeEvent(CLICK))?new Clicker (typesForEvent(CLICK)):new Clicker (BUTTON);
            c.setEventWhenNoId(EVENT_WHEN_NO_ID);
            u.addEvent(addVetoes(c));

            // Events - Long Click
            if (doLongClick()) {
                    LongClicker l = (customizeEvent(LONG_CLICK))?new LongClicker (typesForEvent(LONG_CLICK)):new LongClicker (BUTTON, WEB_VIEW);
                    l.setEventWhenNoId(EVENT_WHEN_NO_ID);
                    u.addEvent(addVetoes(l));
            }
            
            // Events - Select List Item
            ListSelector ls = (customizeEvent(LIST_SELECT))?
                            new ListSelector (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_SELECT)):new ListSelector(MAX_EVENTS_PER_WIDGET);
            ls.setEventWhenNoId(true);
            u.addEvent(addVetoes(ls));
            
            // Events - Long Click List Item
            if (doLongClickOnLists()) {
                    ListLongClicker llc = (customizeEvent(LIST_LONG_SELECT))?
                                    new ListLongClicker (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_LONG_SELECT)):new ListLongClicker(MAX_EVENTS_PER_WIDGET);
                    llc.setEventWhenNoId(true);
                    u.addEvent(addVetoes(llc));
            }
            
            // Events - Swap Tab
            TabSwapper ts = (customizeEvent(SWAP_TAB))?new TabSwapper (typesForEvent(SWAP_TAB)):new TabSwapper ();
            if (TAB_EVENTS_START_ONLY) {
                    ts.setOnlyOnce(true);
            }
            u.addEvent(ts);
            
            // Inputs - Click
            Clicker c2 = (customizeInput(CLICK))?new Clicker (typesForInput(CLICK)):new Clicker (TOGGLE_BUTTON, CHECKBOX, RADIO);
            c2.setEventWhenNoId(false);

            // Inputs - Slider
            Slider sl = (customizeInput(SET_BAR))?new Slider (typesForInput(SET_BAR)):new Slider();
            sl.setEventWhenNoId(false);
            
            // Inputs - Edit Text
            RandomEditor re = (customizeInput(TYPE_TEXT))?new RandomEditor(typesForInput(TYPE_TEXT)):new RandomEditor();
            re.setEventWhenNoId(false);
            u.addInput (addVetoes(c2), addVetoes(sl), addVetoes(re));			
			
		} else {
			u = (doForceSeed())?new AlternativeUser (a):new AlternativeUser(a,new Random(RANDOM_SEED));
			// Events - Click
			Clicker c = (customizeEvent(CLICK))?new Clicker (typesForEvent(CLICK)):new Clicker (BUTTON);
			c.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addVetoes(c));

			// Events - Long Click
			if (doLongClick()) {
				LongClicker l = (customizeEvent(LONG_CLICK))?new LongClicker (typesForEvent(LONG_CLICK)):new LongClicker (BUTTON, WEB_VIEW);
				l.setEventWhenNoId(EVENT_WHEN_NO_ID);
				u.addEvent(addVetoes(l));
			}
			
			// Events - Spinner
			SpinnerSelector eventSS = (customizeEvent(SPINNER_SELECT))?
					new SpinnerSelector (MAX_EVENTS_PER_WIDGET, typesForEvent(SPINNER_SELECT)):new SpinnerSelector(MAX_EVENTS_PER_WIDGET, SPINNER);
			eventSS.setEventWhenNoId(true);
			u.addEvent(addVetoes(eventSS));						
			
			// Events - Select List Item
			ListSelector ls = (customizeEvent(LIST_SELECT))?
					new ListSelector (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_SELECT)):new ListSelector(MAX_EVENTS_PER_WIDGET);
			ls.setEventWhenNoId(true);
			u.addEvent(addVetoes(ls));
			
			// Events - Long Click List Item
			if (doLongClickOnLists()) {
				ListLongClicker llc = (customizeEvent(LIST_LONG_SELECT))?
						new ListLongClicker (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_LONG_SELECT)):new ListLongClicker(MAX_EVENTS_PER_WIDGET);
				llc.setEventWhenNoId(true);
				u.addEvent(addVetoes(llc));
			}
			
			// Events - Swap Tab
			TabSwapper ts = (customizeEvent(SWAP_TAB))?new TabSwapper (typesForEvent(SWAP_TAB)):new TabSwapper ();
			if (TAB_EVENTS_START_ONLY) {
				ts.setOnlyOnce(true);
			}
			u.addEvent(ts);
						
			// Inputs - Click for Toggle And Radio
			Clicker  c2= (customizeInput(CLICK))?new Clicker (typesForInput(CLICK)):new Clicker (TOGGLE_BUTTON, RADIO);
			c2.setEventWhenNoId(false);			

			// Inputs - Click for Check (Return Yes and No click on check)
			Checker inputC = (customizeInput(CLICK))?new Checker (typesForInput(CLICK)):new Checker (CHECKBOX);
			inputC.setEventWhenNoId(false);			
						
			// Inputs - Slider
			Slider sl = (customizeInput(SET_BAR))?new Slider (typesForInput(SET_BAR)):new Slider();
			sl.setEventWhenNoId(false);
						
			// Inputs - Edit Text
			TextEditor te = (customizeInput(TYPE_TEXT))?new TextEditor(typesForInput(TYPE_TEXT)):new TextEditor();
			te.setEventWhenNoId(false);			
			
			//Inputs - Spinner
			SpinnerSelector inputSS = (customizeInput(SPINNER_SELECT))?
					new SpinnerSelector(typesForInput(SPINNER_SELECT)):new SpinnerSelector(SPINNER_INPUT);
			inputSS.setEventWhenNoId(false);
		
			u.addInput (addVetoes(c2), addVetoes(inputC), addVetoes(sl), addVetoes(te), addVetoes(inputSS));
		}
		
		// Additional Events
		for (InteractorAdapter i: ADDITIONAL_EVENTS) {
			u.addEvent(addVetoes(i));			
		}
				
		// Additional Inputs
		for (InteractorAdapter i: ADDITIONAL_INPUTS) {
			u.addInput(addVetoes(i));
		}
		
		return u;
	}
	
	@SuppressWarnings("serial")
	public static class InteractionMap extends HashMap<String,String[]> {}
	
}