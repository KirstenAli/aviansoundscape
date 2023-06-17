package org.example;

import org.example.training.preprocessing.Train;
import org.neuroph.util.TransferFunctionType;

public class Main {
    public static void main(String[] args) {
        var hiddenNeurons = 100;
        Train.start("birdaudioclips", hiddenNeurons, TransferFunctionType.SIGMOID);
    }
}