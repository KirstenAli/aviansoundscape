package org.aviansoundscape.training.preprocessing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.Arrays;

public class Train{
    public static void start(String dataPath, String testDataPath,
                             int hiddenNeurons, TransferFunctionType functionType){

        var mfccExtractor = new MFCCExtractor();

        var dataSet = buildDataSet(dataPath, mfccExtractor);

        var trainedNetwork = train(functionType, hiddenNeurons, dataSet);

        var testDataSet = buildDataSet(testDataPath, dataSet.getInputSize(), mfccExtractor);

        testNeuralNetwork(trainedNetwork, testDataSet);
    }

    private static MultiLayerPerceptron train(TransferFunctionType functionType,
                                              int hiddenNeurons, DataSet dataSet){

        var inputNeurons = dataSet.getInputSize();
        var outputNeurons = dataSet.getOutputSize();

        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(functionType,
                inputNeurons, hiddenNeurons, outputNeurons);

        multiLayerPerceptron.setLearningRule(new MyBackPropagation());

        multiLayerPerceptron.learn(dataSet);

        return multiLayerPerceptron;
    }

    private static int getOutputSize(TrainingSet trainingSet){
        return trainingSet.getRows().get(0).getOutputs().length;
    }

    private static void testNeuralNetwork(NeuralNetwork network, DataSet testSet) {

        for(DataSetRow dataRow : testSet.getRows()) {
            var input = dataRow.getInput();

            network.setInput(input);
            network.calculate();

            double[] networkOutput = network.getOutput();

            System.out.println(" Output: " + Arrays.toString(networkOutput));
        }

    }

    private static DataSet buildDataSet(String dataPath,
                                        FileProcessor fileProcessor){
        var trainingSet = new TrainingSetBuilder()
                .buildTrainingSet(dataPath, fileProcessor);

        return buildSet(trainingSet, trainingSet.getLongestInput());
    }

    private static DataSet buildDataSet(String dataPath, int longestInput,
                                        FileProcessor fileProcessor){

        var trainingSet = new TrainingSetBuilder()
                .buildTrainingSet(dataPath, longestInput, fileProcessor);

        return buildSet(trainingSet, longestInput);
    }

    private static DataSet buildSet(TrainingSet trainingSet, int longestInput){
        var outputSize = getOutputSize(trainingSet);

        DataSet dataSet = new DataSet(longestInput, outputSize);

        for(Row row: trainingSet.getRows()){
            dataSet.add(row.getInputs(), row.getOutputs());
        }

        return dataSet;
    }
}
