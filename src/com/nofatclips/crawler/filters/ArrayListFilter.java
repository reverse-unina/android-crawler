package com.nofatclips.crawler.filters;

import java.util.ArrayList;
import java.util.Iterator;

import android.view.View;

import com.nofatclips.androidtesting.model.WidgetState;
import com.nofatclips.crawler.model.Filter;

public abstract class ArrayListFilter implements Filter {

	public ArrayListFilter () {
		super ();
	}
	
	@Override
	public Iterator<WidgetState> iterator() {
		return this.filteredItems.iterator();
	}

//	public void loadItems(Iterable<WidgetState> items) {
//		for (WidgetState v: items) {
//			if (isValidItem(v)) {
//				this.filteredItems.add(v);
//			}
//		}
//	}
	
	@Override
	public void loadItem(View v, WidgetState w) {
		if (isValidItem(v))
			this.filteredItems.add(w);
	}
	
	@Override
	public void clear () {
		this.filteredItems.clear();
	}
	
	@Override
	public int numWidgets () {
		return this.filteredItems.size();
	}	

	private ArrayList<WidgetState> filteredItems = new ArrayList<WidgetState>();

}
