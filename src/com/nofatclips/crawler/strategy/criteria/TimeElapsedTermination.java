package com.nofatclips.crawler.strategy.criteria;

import com.nofatclips.crawler.model.Strategy;

import android.os.SystemClock;
import android.util.Log;

public class TimeElapsedTermination implements TerminationCriteria {
        
        private long max;
        private long start;
        
        public TimeElapsedTermination () {
                this(3600);
        }
        
        public TimeElapsedTermination (long maxTime) {
                this.max = maxTime;
                this.start = SystemClock.uptimeMillis();
        }
        
        public boolean termination () {
                long current = (SystemClock.uptimeMillis()-this.start)/1000;
                Log.i ("nofatclips", "Check for termination. Time elapsed: " + current + "s; time limit: " + this.max + "s");
                return (current>=max);
        }
        
        @Override
        public void setStrategy(Strategy theStrategy) {}

}