package org.example.training.preprocessing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.Arrays;

public class Train {

    public static void train(String dataPath, int hiddenNeurons){
        var trainingSet = TrainingSetBuilder.buildTrainingSet(dataPath);
        var outputSize = trainingSet.getRows().get(0).getOutputs().length;
        var longestInput = trainingSet.getLongestInput();

        var dataSet = getDataSet(trainingSet, outputSize);

        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                longestInput, hiddenNeurons, outputSize);

        myMlPerceptron.learn(dataSet);

        testNeuralNetwork(myMlPerceptron, dataSet);
    }

    private static DataSet getDataSet(TrainingSet trainingSet, int outputSize){

        DataSet dataSet = new DataSet(trainingSet.getLongestInput(), outputSize);

        for(Row row: trainingSet.getRows()){
            dataSet.add(row.getInputs(), row.getOutputs());
        }

        return dataSet;
    }

    public static void testNeuralNetwork(NeuralNetwork network, DataSet testSet) {

        for(DataSetRow dataRow : testSet.getRows()) {
            var input = dataRow.getInput();

            network.setInput(input);
            network.calculate();

            double[] networkOutput = network.getOutput();

            System.out.println(" Output: " + Arrays.toString(networkOutput) );
        }

    }
}
