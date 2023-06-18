package org.aviansoundscape.training.preprocessing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Row {

    @Setter(AccessLevel.NONE)
    private float[] preInputs;

    @Setter(AccessLevel.NONE)
    private int preInputLength;

    private double[] inputs;

    private double[] outputs;

    private int classNum;

    public void setPreInputs(float[] preInputs) {
        this.preInputs = preInputs;
        preInputLength = preInputs.length;
    }
}
