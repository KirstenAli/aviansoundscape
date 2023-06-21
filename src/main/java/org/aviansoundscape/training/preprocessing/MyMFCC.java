package org.aviansoundscape.training.preprocessing;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.mfcc.MFCC;

import java.util.ArrayList;
import java.util.List;

public class MyMFCC extends MFCC {

    private final List<float[]> mfccs = new ArrayList<>();
    public MyMFCC(int samplesPerFrame, int sampleRate) {
        super(samplesPerFrame, sampleRate);
    }

    public MyMFCC(int samplesPerFrame, float sampleRate, int amountOfCepstrumCoef, int amountOfMelFilters, float lowerFilterFreq, float upperFilterFreq) {
        super(samplesPerFrame, sampleRate, amountOfCepstrumCoef, amountOfMelFilters, lowerFilterFreq, upperFilterFreq);
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        var bool = super.process(audioEvent);
        mfccs.add(super.getMFCC());
        return bool;
    }

    public double[] getMFCCs() {
        return toDoubleArray(mfccs);
    }

    private static double[] toDoubleArray(List<float[]> listOfArrays) {
        int totalLength = 0;
        for (float[] array : listOfArrays) {
            totalLength += array.length;
        }

        double[] result = new double[totalLength];

        for (int i=0; i<listOfArrays.size(); i++){

            var floatArray = listOfArrays.get(i);

            for (float v : floatArray) {
                result[i] = v;
            }
        }

        return result;
    }
}
