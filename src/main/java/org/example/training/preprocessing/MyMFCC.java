package org.example.training.preprocessing;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.mfcc.MFCC;

import java.util.ArrayList;
import java.util.List;

public class MyMFCC extends MFCC {

    private final List<float[]> mfcc = new ArrayList<>();
    public MyMFCC(int samplesPerFrame, int sampleRate) {
        super(samplesPerFrame, sampleRate);
    }

    public MyMFCC(int samplesPerFrame, float sampleRate, int amountOfCepstrumCoef, int amountOfMelFilters, float lowerFilterFreq, float upperFilterFreq) {
        super(samplesPerFrame, sampleRate, amountOfCepstrumCoef, amountOfMelFilters, lowerFilterFreq, upperFilterFreq);
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        var bool = super.process(audioEvent);
        mfcc.add(super.getMFCC());
        return bool;
    }

    @Override
    public float[] getMFCC() {
        return convertFloatListToArray(mfcc);
    }

    private static float[] convertFloatListToArray(List<float[]> listOfArrays) {
        int totalLength = 0;
        for (float[] array : listOfArrays) {
            totalLength += array.length;
        }

        float[] result = new float[totalLength];
        int currentIndex = 0;

        for (float[] array : listOfArrays) {
            System.arraycopy(array, 0, result, currentIndex, array.length);
            currentIndex += array.length;
        }

        return result;
    }
}
