package org.example;

import org.example.training.preprocessing.Train;
import org.example.training.preprocessing.TrainingSetBuilder;

public class Main {
    public static void main(String[] args) {
        //Train.train("birdaudioclips");

        var results = TrainingSetBuilder.buildTrainingSet("birdaudioclips", 100);

        System.out.println(results.getLongestInput());
    }
}