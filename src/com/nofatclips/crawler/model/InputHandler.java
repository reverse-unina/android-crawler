package com.nofatclips.crawler.model;

import java.util.List;

import com.nofatclips.androidtesting.model.UserInput;
import com.nofatclips.androidtesting.model.WidgetState;

public interface InputHandler {

	public List<UserInput> handleInput (WidgetState w);

}
