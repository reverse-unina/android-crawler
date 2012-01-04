package com.nofatclips.crawler.automation;

import android.view.View;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.nofatclips.crawler.model.TypeDetector;

import static com.nofatclips.androidtesting.model.SimpleType.*;

// Detect the simple type of the widget based on the name of the class. Faster but won't work always.
// A more reliable implementation should use instanceof, though it won't work with internal (not public) classes.

public class SimpleTypeDetector implements TypeDetector {

	public String getSimpleType(View v) {
		String type = v.getClass().getName(); 
		if (type.endsWith("null"))
			return NULL;
		if (type.endsWith("RadioButton"))
			return RADIO;
		if (type.endsWith("CheckBox") || type.endsWith("CheckedTextView"))
			return CHECKBOX;
		if (type.endsWith("ToggleButton"))
			return TOGGLE_BUTTON;
		if (type.endsWith("IconMenuView"))
			return MENU_VIEW;
		if (type.endsWith("DatePicker"))
			return DATE_PICKER;
		if (type.endsWith("TimePicker"))
			return TIME_PICKER;
		if (type.endsWith("IconMenuItemView"))
			return BUTTON;
		if (type.endsWith("DialogTitle"))
			return DIALOG_VIEW;
		if (type.endsWith("Button"))
			return BUTTON;
		if (type.endsWith("EditText"))
			return EDIT_TEXT;
		if (type.endsWith("Spinner")){
			Spinner s=(Spinner)v;
			if((s.getOnItemSelectedListener())==null)
				return SPINNER_INPUT;
			else	
				return SPINNER;
		}			
		if (type.endsWith("SeekBar"))
			return SEEK_BAR;
		if (v instanceof RatingBar && (!((RatingBar)v).isIndicator()))
			return RATING_BAR;
		if (type.endsWith("TabHost"))
			return TAB_HOST;
		if (type.endsWith("ListView") || type.endsWith("ExpandedMenuView"))
			return LIST_VIEW;
		if (type.endsWith("TextView"))
			return TEXT_VIEW;
		if (type.endsWith("ImageView"))
			return IMAGE_VIEW;
		if (type.endsWith("LinearLayout"))
			return LINEAR_LAYOUT;
		if ((v instanceof WebView) || type.endsWith("WebView"))
			return WEB_VIEW;
		return "";
	}

}
