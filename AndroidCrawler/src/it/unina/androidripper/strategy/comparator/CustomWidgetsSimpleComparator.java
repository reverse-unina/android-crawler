package it.unina.androidripper.strategy.comparator;

import java.util.HashSet;

import android.util.Log;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.androidtesting.model.WidgetState;

import static it.unina.androidripper.Resources.TAG;
import static com.nofatclips.androidtesting.model.SimpleType.*;
import static it.unina.androidripper.strategy.comparator.Resources.*;

// Accetta in input (nel costruttore) un numero arbitrario di tipi di widget e compara le activity
// considerandone il nome ed i widget dei tipi selezionati. Una activity A sara' diversa da B se il
// nome di A e' diverso da quello di B, o se fra i widget dei tipi selezionati posseduti da A ce
// n'e' almeno uno che B non possiede.
// Sono presi in considerazione solo i widget dotati di ID.

// Es: Comparator COMPARATOR = new CustomWidgetsSimpleComparator("button", "editText");

public class CustomWidgetsSimpleComparator extends NameComparator {
	
	protected String[] widgetClasses;
	
	public CustomWidgetsSimpleComparator (String... widgets) {
		super ();
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
		boolean menuCount = !(COMPARE_MENU_COUNT && campo.getSimpleType().equals(MENU_VIEW) && ((altroCampo.getCount() != campo.getCount())));
		return (listCount && menuCount && (altroCampo.getId().equals(campo.getId())) && (altroCampo.getSimpleType().equals(campo.getSimpleType())));
	}

	@Override
	public boolean compare(ActivityState currentActivity, ActivityState storedActivity) {

		if (!super.compare (currentActivity, storedActivity)) return false; // Different name, different activity, early return
		
		HashSet<String> checkedAlready = new HashSet<String>();
		// Check if current has at least one widget that stored ain't got
		Log.d(TAG,"Looking for additional widgets");
		for (WidgetState campo: currentActivity) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d(TAG,"Comparator found widget " + id + " (type = " + type + ")");
			
			if (matchClass(campo.getSimpleType()) && (id>0) ) {
				Log.v(TAG,"Comparing " + type + " #" + id);
				if (!lookFor(campo, storedActivity)) return false;
				checkedAlready.add(campo.getId()); // store widgets checked in this step to skip them in the next step
			}
		}
		
		// Check if stored has at least one widget that current ain't got. Skip if already checked.
		Log.d(TAG,"Looking for missing widgets");
		for (WidgetState campo: storedActivity) {
			int id = Integer.valueOf(campo.getId());
			String type = campo.getSimpleType();
			Log.d(TAG,"Comparator found widget " + id + " (type = " + type + ")");
			
			if ( matchClass(campo.getSimpleType()) && (id>0) && (!checkedAlready.contains(campo.getId())) ) {
				Log.v(TAG,"Comparing " + type + " #" + id);
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