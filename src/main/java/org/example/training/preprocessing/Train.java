package org.example.training.preprocessing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.Arrays;

public class Train {

    public static void train(String dataPath){
        var trainingSet = TrainingSetBuilder.buildTrainingSet(dataPath, 100);
        var longestInput = trainingSet.getLongestInput();
        var dataSet = getDataSet(trainingSet);

        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                longestInput, longestInput-1, 1);

        myMlPerceptron.learn(dataSet);

        testNeuralNetwork(myMlPerceptron, dataSet);
    }

    private static DataSet getDataSet(TrainingSet trainingSet){

        DataSet dataSet = new DataSet(trainingSet.getLongestInput(), 1);

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
