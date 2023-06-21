package org.aviansoundscape.training.preprocessing;

import org.neuroph.core.data.DataSet;

import java.io.File;

public class SetBuilder{

    public static DataSet buildDataSet(String directoryPath,
                                       int longestInput,
                                       FileProcessor fileProcessor){
        var initialTestSet = new InitialDataSet();

        prepareInitialSet(directoryPath, initialTestSet, fileProcessor);

        initialTestSet.setInputLength(longestInput);

        initialTestSet.build();

        return buildSet(initialTestSet, longestInput);
    }

    public static DataSet buildDataSet(String dataPath,
                                       FileProcessor fileProcessor){
        var initialDataSet = new TrainingSet();

        prepareInitialSet(dataPath, initialDataSet, fileProcessor);

        initialDataSet.build();

        return buildSet(initialDataSet, initialDataSet.getInputLength());
    }

    private static void prepareInitialSet(String directoryPath,
                                          InitialDataSet initialDataSet,
                                          FileProcessor fileProcessor){

        var directory = new File(directoryPath);
        var fileNames = directory.list();

        if(fileNames!=null) {
            for(String fileName : fileNames) {
                var row = new Row();
                var preInputs = fileProcessor
                        .processFile(directoryPath+"/"+fileName);

                row.setPreInputs(preInputs);

                var className = getClassNum(fileName);
                row.setClassNum(className);

                initialDataSet.add(row);
            }
        }
    }

    private static DataSet buildSet(InitialDataSet initialDataSet, int longestInput){
        var outputSize = getOutputSize(initialDataSet);

        DataSet dataSet = new DataSet(longestInput, outputSize);

        for(Row row: initialDataSet.getRows()){
            dataSet.add(row.getInputs(), row.getOutputs());
        }

        return dataSet;
    }

    private static int getOutputSize(InitialDataSet initialDataSet){
        return initialDataSet.getRows().get(0).getOutputs().length;
    }

    private static int getClassNum(String fileName){
        var realFileName = fileName.split("\\.");
        return Integer.parseInt(realFileName[0]);
    }

    public static double[] toDoubleArray(float[] floatArray){
        double[] doubleArray = new double[floatArray.length];

        for (int i = 0; i < floatArray.length; i++)
            doubleArray[i] = floatArray[i];

        return doubleArray;
    }
}
