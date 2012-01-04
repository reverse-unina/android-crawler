package com.nofatclips.crawler.planning.interactors;

import static com.nofatclips.androidtesting.model.InteractionType.TYPE_TEXT;
import static com.nofatclips.androidtesting.model.SimpleType.EDIT_TEXT;

import java.util.List;

import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Abstractor;
import com.nofatclips.crawler.planning.adapters.RandomInteractorAdapter;

public class TextEditor extends RandomInteractorAdapter {

	private InputValuesOfTextWidget TextValue = new InputValuesOfTextWidget();
	
	public TextEditor () {
		this (EDIT_TEXT);
	}
	
	public TextEditor (String ... simpleTypes) {
		super (simpleTypes);
	}
	
	public TextEditor (Abstractor theAbstractor) {
		this ();
		setAbstractor(theAbstractor);
	}
	
	public TextEditor (Abstractor theAbstractor, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
	}

	public TextEditor (Abstractor theAbstractor, int minValue) {
		this (theAbstractor);
		setMin(minValue);
	}

	public TextEditor (Abstractor theAbstractor, int minValue, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
		setMin(minValue);
	}

	public TextEditor (Abstractor theAbstractor, int minValue, int maxValue) {
		this (theAbstractor);
		setMin(minValue);
		setMax(maxValue);
	}

	public TextEditor (Abstractor theAbstractor, int minValue, int maxValue, String ... simpleTypes) {
		super (theAbstractor, simpleTypes);
		setMin(minValue);
		setMax(maxValue);
	}
	
	@Override
	public List<UserInput> getInputs (WidgetState w) {
		String[] inputs = TextValue.InputValueOfText(w);
		//variable "inputs" is never equals null 
		if((inputs!=null)&(inputs[0].equals("random"))){
			inputs[0] = String.valueOf(getValue(w));
		}
		return getInputs (w,inputs);
	}	

	@Override
	public String getInteractionType() {
		return TYPE_TEXT;
	}
	
}

