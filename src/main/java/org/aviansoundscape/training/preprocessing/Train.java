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

        var dataSet = SetBuilder.buildDataSet(dataPath, mfccExtractor);

        var trainedNetwork = train(functionType, hiddenNeurons, dataSet);

        var testDataSet = SetBuilder.buildDataSet(testDataPath,
                dataSet.getInputSize(), mfccExtractor);

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

    private static void testNeuralNetwork(NeuralNetwork network, DataSet testSet) {

        for(DataSetRow dataRow : testSet.getRows()){
            var input = dataRow.getInput();

            network.setInput(input);
            network.calculate();

            double[] networkOutput = network.getOutput();

            System.out.println(" Output: " + Arrays.toString(networkOutput));
        }

    }
}
