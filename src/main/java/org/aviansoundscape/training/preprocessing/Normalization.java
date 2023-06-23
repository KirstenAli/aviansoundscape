package org.aviansoundscape.training.preprocessing;

public class Normalization{

    private double mean;

    public double[] zNormalization(double[] input){
        double[] normalizedData = new double[input.length];
        var standardDeviation = standardDeviation(input);

        for (int i = 0; i < input.length; i++)
            normalizedData[i] = (input[i] - mean) / standardDeviation;

        return normalizedData;
    }

    public static double mean(double[] input){
        double sum = 0.0;
        for (double value : input)
            sum += value;

        return sum/input.length;
    }

    public double standardDeviation(double[] input){
        double squaredSum = 0.0;
        mean = mean(input);

        for (double value : input)
            squaredSum += Math.pow(value - mean, 2);

        return squaredSum;
    }
}
