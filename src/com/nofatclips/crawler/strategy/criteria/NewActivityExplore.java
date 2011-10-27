package com.nofatclips.crawler.strategy.criteria;

import com.nofatclips.crawler.model.Strategy;

public class NewActivityExplore implements ExplorationCriteria {

        private Strategy theStrategy;

        @Override
        public void setStrategy(Strategy theStrategy) {
                this.theStrategy = theStrategy;
        }

        @Override
        public boolean exploration() {
                return !theStrategy.isLastComparationPositive();
        }

}