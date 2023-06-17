package org.example;

import org.example.training.preprocessing.Train;
import org.neuroph.util.TransferFunctionType;

public class Main {
    public static void main(String[] args) {
        var hiddenNeurons = 100;
        Train.train("birdaudioclips", hiddenNeurons, TransferFunctionType.SIGMOID);
    }
}