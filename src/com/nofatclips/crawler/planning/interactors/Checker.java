package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.CLICK;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;

public class Checker extends InteractorAdapter {

	public Checker (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public Checker (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}
	
	@Override
	public boolean canUseWidget (WidgetState w) {
		return (w.isClickable() && super.canUseWidget(w));
	}
	
	@Override
	public List<UserInput> getInputs (WidgetState w) {
		ArrayList<UserInput> inputs = new ArrayList<UserInput>();
		if (canUseWidget(w)) {
			Log.d("castigliafrancesco", "Handling input '" + getInteractionType() + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			inputs.add(generateInput(w));
			String InteractionType="No click";
			Log.d("castigliafrancesco", "Handling input '" + InteractionType + "' on widget id=" + w.getId() + " type=" + w.getSimpleType() + " name=" + w.getName());
			inputs.add(getAbstractor().createInput(w,"","No click"));			
		}
		return inputs;
	}
	
	
	@Override
	public String getInteractionType () {
		return CLICK;
	}

}