package com.nofatclips.crawler.strategy.comparator;

import java.util.HashSet;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.WidgetState;

import static com.nofatclips.androidtesting.model.SimpleType.LIST_VIEW;
import static com.nofatclips.crawler.Resources.COMPARE_LIST_COUNT;

// Accetta in input (nel costruttore) un numero arbitrario di tipi di widget e compara le activity
// considerandone il nome ed i widget dei tipi selezionati. Una activity A sarà diversa da B se il
// nome di A è diverso da quello di B, o se fra i widget dei tipi selezionati posseduti da A ce
// n'è almeno uno che B non possiede.
// Sono presi in considerazione solo i widget dotati di ID.

// Es: Comparator COMPARATOR = new CustomWidgetsComparator("button", "editText");

public class CustomWidgetsComparator extends NameComparator {
	
	public final static boolean IGNORE_ACTIVITY_NAME = true;
//	private boolean byName = true;
	protected String[] widgetClasses;
	
	public CustomWidgetsComparator (String... widgets) {
		this (false, widgets);
	}
	
	public CustomWidgetsComparator (boolean ignore, String... widgets) {
		super (!ignore);
		this.widgetClasses = widgets; 
	}
	
	public boolean matchClass (String type) {
		for (String storedType: this.widgetClasses) {
			if (storedType.equals(type)) return true;
		}
		return false;
	}
	
	protected boolean matchWidget (WidgetState campo, WidgetState altroCampo) {
		boolean listCount = !(COMPARE_LIST_COUNT && campo.getSimpleType().equals(LIST_VIEW) && ((altroCampo.getCount() != campo.getCount())));
		return (listCount && (altroCampo.getId().equals(campo.getId())) && (altroCampo.getSimpleType().equals(campo.getSimpleType())));
	}

	@Override
	public boolean compare(ActivityState currentActivity, ActivityState storedActivity) {
		HashSet<String> checkedAlready = new HashSet<String>();

		if (this.byName && !super.compare (currentActivity, storedActivity)) return false; // Different name, different activity, early return
			
		// Check if current has at least one widget that stored ain't got
		Log.d("nofatclips","Looking for additional widgets");
		for (WidgetState campo: currentActivity) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d("nofatclips","Comparator found widget " + id + " (type = " + type + ")");
			
			if (matchClass(campo.getSimpleType()) && (id>0) ) {
				Log.v("nofatclips","Comparing " + type + " #" + id);
				if (!lookFor(campo, storedActivity)) return false;
				checkedAlready.add(campo.getId()); // store widgets checked in this step to skip them in the next step
			}
		}
		
		// Check if stored has at least one widget that current ain't got. Skip if already checked.
		Log.d("nofatclips","Looking for missing widgets");
		for (WidgetState campo: storedActivity) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d("nofatclips","Comparator found widget " + id + " (type = " + type + ")");
			
			if ( matchClass(campo.getSimpleType()) && (id>0) && (!checkedAlready.contains(campo.getId())) ) {
				Log.v("nofatclips","Comparing " + type + " #" + id);
				if (!lookFor(campo, currentActivity)) return false;
			}
		}
		
		return true; // All tests failed, can't found a difference between current and stored!
	}
	
	protected boolean lookFor (WidgetState campo, ActivityState activity) {
		for (WidgetState altroCampo: activity) {
			if (matchWidget (altroCampo, campo)) {
				return true;
			}
		}
		return false;
	}
	
	public String describe() {
		StringBuilder values = new StringBuilder();
		String comma = "";
		for (String value: this.widgetClasses) {
			values.append(comma).append(value);
			comma = ",";
		}
		return values.toString();
	}
	
}