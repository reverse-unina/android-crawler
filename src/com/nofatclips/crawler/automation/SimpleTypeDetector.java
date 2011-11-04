package com.nofatclips.crawler.automation;

import android.view.View;

import com.nofatclips.crawler.model.TypeDetector;

import static com.nofatclips.androidtesting.model.SimpleType.*;

// Detect the simple type of the widget based on the name of the class. Faster but won't work always.
// A more reliable implementation should use instanceof.

public class SimpleTypeDetector implements TypeDetector {

	@Override
	public String getSimpleType(View v) {
		String type = v.getClass().getName(); 
		if (type.endsWith("null"))
			return NULL;
		if (type.endsWith("RadioButton"))
			return RADIO;
		if (type.endsWith("CheckBox") || type.endsWith("CheckedTextView"))
			return CHECKBOX;
		if (type.endsWith("IconMenuView"))
			return MENU_VIEW;
		if (type.endsWith("IconMenuItemView"))
			return BUTTON;
		if (type.endsWith("Button"))
			return BUTTON;
		if (type.endsWith("EditText"))
			return EDIT_TEXT;
		if (type.endsWith("TabHost"))
			return TAB_HOST;
		if (type.endsWith("ListView") || type.endsWith("ExpandedMenuView"))
			return LIST_VIEW;
		if (type.endsWith("TextView"))
			return TEXT_VIEW;
		if (type.endsWith("ImageView"))
			return IMAGE_VIEW;
		return "";
	}

}
