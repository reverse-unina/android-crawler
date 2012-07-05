package com.nofatclips.crawler.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.nofatclips.androidtesting.model.InteractionType.*;
import static com.nofatclips.crawler.Resources.*;

import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.model.UserAdapter;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;
import com.nofatclips.crawler.planning.interactors.*;

public class UserFactory {

	public static InteractionMap eventToTypeMap = new InteractionMap();
	public static InteractionMap inputToTypeMap = new InteractionMap();
	public static Map<String,List<String>> vetoesMap = new Hashtable<String,List<String>>();
	public static Map<String,List<String>> overridesMap = new Hashtable<String,List<String>>();
	
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

	public static void forceIds (String ... ids) {
		forceIdsForEvent(ALL, ids);
	}

	public static void forceIdsForEvent (String event, String ... ids) {
		if (!overridesMap.containsKey(event)) {
			overridesMap.put(event, new ArrayList<String>());
		}
		for (String id: ids) {
			overridesMap.get(event).add(id);
		}
	}

	public static void forceIds (int ... ids) {
		for (Integer id: ids) {
			forceIdsForEvent(ALL, String.valueOf(id));
		}
	}

	public static void denyInteractionOnIds (String event, int ... ids) {
		for (Integer id: ids) {
			denyIdsForEvent(event, String.valueOf(id));
		}
	}

	public static InteractorAdapter addDosAndDonts (InteractorAdapter i) {
		addVetoes (i, ALL);
		addVetoes (i, i.getInteractionType());
		addOverrides (i, ALL);
		addOverrides (i, i.getInteractionType());
		return i;
	}
	
	public static void addVetoes (InteractorAdapter i, String event) {
		if (vetoesMap.containsKey(event)) {
			i.denyIds(vetoesMap.get(event));
		}		
	}

	public static void addOverrides (InteractorAdapter i, String event) {
		if (overridesMap.containsKey(event)) {
			i.forceIds(overridesMap.get(event));
		}		
	}

	public static boolean isRequiredEvent (String interaction) {
		return isRequired (Category.EVENT, interaction);
	}

	public static boolean isRequiredInput (String interaction) {
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
		} else {
			u = (doForceSeed())?new AlternativeUser (a):new AlternativeUser(a,new Random(RANDOM_SEED));
		}

		if (isRequiredEvent(CLICK)) {
			Clicker c = new Clicker (typesForEvent(CLICK));
			c.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(c));
		}

		if (isRequiredEvent(LONG_CLICK)) {
			LongClicker l = new LongClicker (typesForEvent(LONG_CLICK));
			l.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(l));
		}
		
		if (isRequiredEvent(LIST_SELECT)) {			
			ListSelector ls = new ListSelector (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_SELECT));
			ls.setEventWhenNoId(true);
			u.addEvent(addDosAndDonts(ls));
		}
		
		if (isRequiredEvent(LIST_LONG_SELECT)) {
			ListLongClicker llc = new ListLongClicker (MAX_EVENTS_PER_WIDGET, typesForEvent(LIST_LONG_SELECT));
			llc.setEventWhenNoId(true);
			u.addEvent(addDosAndDonts(llc));			
		}
		
		if (isRequiredEvent(SWAP_TAB)) {
			TabSwapper ts = new TabSwapper (typesForEvent(SWAP_TAB));
			if (TAB_EVENTS_START_ONLY) {
				ts.setOnlyOnce(true);
			}
			u.addEvent(ts);			
		}
		
		for (InteractorAdapter i: ADDITIONAL_EVENTS) {
			i.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(i));			
		}
		
		if (isRequiredInput(CLICK)) {
			Clicker c2 = new Clicker (typesForInput(CLICK));
			c2.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(c2));
		}

		if (isRequiredInput(SET_BAR)) {
			Slider sl = new Slider (typesForInput(SET_BAR));
			sl.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(sl));
		}
		
		if (isRequiredInput(TYPE_TEXT)) {
			RandomEditor re = new RandomEditor(typesForInput(TYPE_TEXT));
			re.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(re));
		}

		if (isRequiredInput(WRITE_TEXT)) {
			RandomWriter re = new RandomWriter(typesForInput(WRITE_TEXT));
			re.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(re));
		}

		if (isRequiredInput(SPINNER_SELECT)) {
			RandomSpinnerSelector rss = new RandomSpinnerSelector(typesForInput(SPINNER_SELECT));
			rss.setEventWhenNoId(false);
			u.addInput(addDosAndDonts(rss));
		}
		
		for (InteractorAdapter i: ADDITIONAL_INPUTS) {
			i.setEventWhenNoId(false);
			u.addInput(addDosAndDonts(i));
		}
		
		return u;
	}
	
	/** @author nicola amatucci */
	public static UserAdapter getUserForEvents (Abstractor a, String widgetType, ArrayList<String> eventTypes)
	{
		String[] widgetTypes = { widgetType }; 
		UserAdapter u;

		if (isUserSimple()) {
			u = (doForceSeed())?new SimpleUser (a):new SimpleUser(a,new Random(RANDOM_SEED));
		} else {
			u = (doForceSeed())?new AlternativeUser (a):new AlternativeUser(a,new Random(RANDOM_SEED));
		}

		if ( eventTypes.contains(CLICK) ) {
			Clicker c = new Clicker (widgetTypes);
			c.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(c));
		}

		if ( eventTypes.contains(LONG_CLICK) ) {
			LongClicker l = new LongClicker (widgetTypes);
			l.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(l));
		}
		
		if ( eventTypes.contains(LIST_SELECT) ) {			
			ListSelector ls = new ListSelector (MAX_EVENTS_PER_WIDGET, widgetTypes);
			ls.setEventWhenNoId(true);
			u.addEvent(addDosAndDonts(ls));
		}
		
		if ( eventTypes.contains(LIST_LONG_SELECT) ) {
			ListLongClicker llc = new ListLongClicker (MAX_EVENTS_PER_WIDGET, widgetTypes);
			llc.setEventWhenNoId(true);
			u.addEvent(addDosAndDonts(llc));			
		}
		
		if ( eventTypes.contains(SWAP_TAB) ) {
			TabSwapper ts = new TabSwapper (widgetTypes);
			if (TAB_EVENTS_START_ONLY) {
				ts.setOnlyOnce(true);
			}
			u.addEvent(ts);			
		}
		
		/*
		for (InteractorAdapter i: ADDITIONAL_EVENTS) {
			i.setEventWhenNoId(EVENT_WHEN_NO_ID);
			u.addEvent(addDosAndDonts(i));			
		}
		*/
		
		if (isRequiredInput(CLICK)) {
			Clicker c2 = new Clicker (typesForInput(CLICK));
			c2.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(c2));
		}

		if (isRequiredInput(SET_BAR)) {
			Slider sl = new Slider (typesForInput(SET_BAR));
			sl.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(sl));
		}
		
		if (isRequiredInput(TYPE_TEXT)) {
			RandomEditor re = new RandomEditor(typesForInput(TYPE_TEXT));
			re.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(re));
		}

		if (isRequiredInput(WRITE_TEXT)) {
			RandomWriter re = new RandomWriter(typesForInput(WRITE_TEXT));
			re.setEventWhenNoId(false);
			u.addInput (addDosAndDonts(re));
		}

		if (isRequiredInput(SPINNER_SELECT)) {
			RandomSpinnerSelector rss = new RandomSpinnerSelector(typesForInput(SPINNER_SELECT));
			rss.setEventWhenNoId(false);
			u.addInput(addDosAndDonts(rss));
		}
		
		for (InteractorAdapter i: ADDITIONAL_INPUTS) {
			i.setEventWhenNoId(false);
			u.addInput(addDosAndDonts(i));
		}
		
		return u;
	}
/** @author nicola amatucci */	
	
	@SuppressWarnings("serial")
	public static class InteractionMap extends HashMap<String,String[]> {}
	
}