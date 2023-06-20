package org.aviansoundscape.training.preprocessing;

import org.neuroph.core.data.DataSet;

import java.io.File;

public class SetBuilder {

    public static DataSet buildTestSet(String directoryPath,
                                       int longestInput,
                                       FileProcessor fileProcessor){
        var initialTestSet = new TestSet();

        prepareInitialSet(directoryPath, initialTestSet, fileProcessor);

        initialTestSet.setLongestInput(longestInput);

        initialTestSet.build();

        return buildSet(initialTestSet, longestInput);
    }

    public static DataSet buildDataSet(String dataPath,
                                       FileProcessor fileProcessor){
        var initialDataSet = new TrainingSet();

        prepareInitialSet(dataPath, initialDataSet, fileProcessor);

        initialDataSet.build();

        return buildSet(initialDataSet, initialDataSet.getLongestInput());
    }

    private static void prepareInitialSet(String directoryPath,
                                          AbstractDataSet abstractDataSet,
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

                abstractDataSet.add(row);
            }
        }
    }

    private static DataSet buildSet(AbstractDataSet abstractDataSet, int longestInput){
        var outputSize = getOutputSize(abstractDataSet);

        DataSet dataSet = new DataSet(longestInput, outputSize);

        for(Row row: abstractDataSet.getRows()){
            dataSet.add(row.getInputs(), row.getOutputs());
        }

        return dataSet;
    }

    private static int getOutputSize(AbstractDataSet abstractDataSet){
        return abstractDataSet.getRows().get(0).getOutputs().length;
    }

    private static int getClassNum(String fileName){
        var realFileName = fileName.split("\\.");
        return Integer.parseInt(realFileName[0]);
    }
}
