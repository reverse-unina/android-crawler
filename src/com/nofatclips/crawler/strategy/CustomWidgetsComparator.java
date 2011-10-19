package com.nofatclips.crawler.strategy;

import java.util.HashSet;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.WidgetState;

// Accetta in input (nel costruttore) un numero arbitrario di tipi di widget e compara le activity
// considerandone il nome ed i widget dei tipi selezionati. Una activity A sarà diversa da B se il
// nome di A è diverso da quello di B, o se fra i widget dei tipi selezionati posseduti da A ce
// n'è almeno uno che B non possiede.
// Sono presi in considerazione solo i widget dotati di ID.

// Es: Comparator COMPARATOR = new CustomWidgetsComparator("button", "editText");

public class CustomWidgetsComparator extends NameComparator {
	
	public final static boolean IGNORE_ACTIVITY_NAME = true;
	private boolean byName = true;
	
	public CustomWidgetsComparator (String... widgets) {
		super ();
		this.widgetClasses = widgets; 
	}
	
	public CustomWidgetsComparator (boolean ignore, String... widgets) {
		this(widgets);
		this.byName = !ignore;
	}
	
	public boolean matchClass (String type) {
		for (String storedType: this.widgetClasses) {
			if (storedType.equalsIgnoreCase(type)) return true;
		}
		return false;
	}
	
	protected boolean matchWidget (WidgetState campo, WidgetState altroCampo) {
		return altroCampo.getId().equals(campo.getId());
	}

	public boolean compare(ActivityState current, ActivityState stored) {
		if (byName && !super.compare(current,stored)) return false; // Different name, different activity, early return
		
		HashSet<String> checkedAlready = new HashSet<String>();
	
		// Check if current has at least one widget that stored ain't got
		Log.d("nofatclips","Looking for additional widgets");
		for (WidgetState campo: current) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d("nofatclips","Comparator found widget " + id + " (type = " + type + ")");
			
			if (matchClass(campo.getSimpleType()) && (id>0) ) {
				Log.v("nofatclips","Comparing " + type + " #" + id);
				boolean trovato = false;
				for (WidgetState altroCampo: stored) {
					if (matchWidget (altroCampo, campo)) {
						trovato = true;
						break;
					}
				}
				if (!trovato) return false;
				checkedAlready.add(campo.getId()); // store widgets checked in this step to skip them in the next step
			}
		}
		
		// Check if stored has at least one widget that current ain't got. Skip if already checked.
		Log.d("nofatclips","Looking for missing widgets");
		for (WidgetState campo: stored) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d("nofatclips","Comparator found widget " + id + " (type = " + type + ")");
			
			if ( matchClass(campo.getSimpleType()) && (id>0) && (!checkedAlready.contains(campo.getId())) ) {
				Log.v("nofatclips","Comparing " + type + " #" + id);
				boolean trovato = false;
				for (WidgetState altroCampo: current) {
					if (matchWidget(altroCampo, campo)) {
						trovato = true;
						break;
					}
				}
				if (!trovato) return false;
			}
		}
		
		return true; // All tests failed, can't found a difference between current and stored!
	}
	
	String[] widgetClasses;
	
}
