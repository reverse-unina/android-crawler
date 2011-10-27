package com.nofatclips.crawler.strategy.comparator;

import com.nofatclips.androidtesting.model.ActivityState;
import com.nofatclips.crawler.model.Comparator;

public class NameComparator implements Comparator {

        @Override
        public boolean compare(ActivityState a, ActivityState b) {
                return a.getName().equals(b.getName());
        }

}
