package org.aviansoundscape.training.preprocessing;

import org.neuroph.nnet.learning.BackPropagation;

public class MyBackPropagation extends BackPropagation {

    @Override
    protected void beforeEpoch() {
        System.out.println("Error:" + getPreviousEpochError());
        super.beforeEpoch();
    }
}
