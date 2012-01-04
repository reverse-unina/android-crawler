package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.SPINNER_SELECT;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.IterativeInteractorAdapter;

public class SpinnerSelector extends IterativeInteractorAdapter {
	
	public SpinnerSelector (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public SpinnerSelector (int maxItems, String ... simpleTypes) {
		super (maxItems, simpleTypes);
	}

	public SpinnerSelector (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public SpinnerSelector (Abstractor theAbstractor, int maxItems, String ... simpleTypes) {
		super (theAbstractor, maxItems, simpleTypes);
	}

	@Override
	//Generation events for Spinner
	//This method is only used by SpinnerSelector
	public List<UserEvent> getEvents (WidgetState w) {
		ArrayList<UserEvent> events = new ArrayList<UserEvent>();
		if (canUseWidget(w)) {
			final int fromItem = 0;
			final int toItem = getToItem(fromItem, w.getCount()-1);
			if (toItem<fromItem) return events;			
			Log.d("castigliafrancesco", "Handling event " + getInteractionType() + " for items [" + fromItem + "," + toItem + "] on " + w.getSimpleType() + " #" + w.getId() + " count=" + w.getCount());
			for (int i=fromItem; i<=toItem; i++) {
				events.add(generateEvent(w, String.valueOf(i)));
			}
		}
		return events;
	}	
		
	@Override
	//Generation inputs for Spinner
	//This method is only used by SpinnerSelector
	public List<UserInput> getInputs (WidgetState w) {
		ArrayList<UserInput> inputs = new ArrayList<UserInput>();		
		if (canUseWidget(w)) {
			final int fromItem = 0;
			final int toItem = w.getCount()-1;
			if (toItem<fromItem) return inputs;			
			Log.d("castigliafrancesco", "Handling input " + getInteractionType() + " for items [" + fromItem + "," + toItem + "] on " + w.getSimpleType() + " #" + w.getId() + " count=" + w.getCount());
			for (int i=fromItem; i<=toItem; i++) {
				inputs.add(generateInput(w, String.valueOf(i)));
			}
		}
		return inputs;
	}		
	
	@Override
	public String getInteractionType () {
		return SPINNER_SELECT;
	}
}