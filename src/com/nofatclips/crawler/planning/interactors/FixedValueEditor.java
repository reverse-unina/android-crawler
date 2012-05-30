package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.TYPE_TEXT;
import static com.nofatclips.androidtesting.model.SimpleType.EDIT_TEXT;

import java.util.Hashtable;
import java.util.Map;

import com.nofatclips.androidtesting.model.UserEvent;
import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.planning.adapters.InteractorAdapter;

public class FixedValueEditor extends InteractorAdapter {

	private Map<String,String> idValuePairs = new Hashtable<String,String>();
	
	public FixedValueEditor () {
		this (EDIT_TEXT);
	}
	
	public FixedValueEditor (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	@Override
	public boolean canUseWidget(WidgetState w) {
		if (super.canUseWidget(w)) {
			for (Map.Entry<String,String> c: this.idValuePairs.entrySet()) {
				if (w.getId().equals(c.getKey())) return true;
			}
		}
		return false;
	}

	@Override
	protected UserEvent generateEvent(WidgetState w) {
		return super.generateEvent(w, this.idValuePairs.get(w.getId()));
	}

	@Override
	protected UserInput generateInput(WidgetState w) {
		return super.generateInput(w, this.idValuePairs.get(w.getId()));
	}

	public String getInteractionType() {
		return TYPE_TEXT;
	}
	
	public void setIdValuePairs (Map<String,String> pairs) {
		this.idValuePairs = pairs;
	}
	
	public FixedValueEditor addIdValuePair (String id, String value) {
		this.idValuePairs.put(id, value);
		return this;
	}

	public FixedValueEditor addIdValuePair (int id, String value) {
		return addIdValuePair(String.valueOf(id), value);
	}

}
