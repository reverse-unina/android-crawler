package com.nofatclips.crawler.model;

import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;
import java.util.Collection;

public interface InputHandler {

	//public UserInput handleInput (WidgetState w);
	public Collection<UserInput> handleInput (WidgetState w);
}
