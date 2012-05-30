package com.nofatclips.crawler.model;

public interface StatelessComparator extends Comparator {

	/*
	 * Just a tagging interface. Comparators that implements this interface are declaring that they don't need the
	 * activity state list to function properly. Example: the NullComparator.
	 */
	
}
