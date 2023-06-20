package org.aviansoundscape.training.preprocessing;

import lombok.Getter;
import lombok.Setter;
import org.neuroph.core.data.DataSet;

import java.io.File;

@Getter @Setter
public class DataSetBuilder {

    private static TrainingSet buildInitialDataSet(String directoryPath,
                                                   TrainingSet trainingSet,
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

                trainingSet.add(row);
            }
        }

        return trainingSet.build();
    }

    private static TrainingSet buildInitialDataSet(String directoryPath,
                                                   int longestInput,
                                                   FileProcessor fileProcessor) {
        var trainingSet = new TrainingSet();
        trainingSet.setLongestInput(longestInput);

        return buildInitialDataSet(directoryPath, trainingSet, fileProcessor);
    }

    private static TrainingSet buildInitialDataSet(String directoryPath,
                                                   FileProcessor fileProcessor) {
        return buildInitialDataSet(directoryPath, new TrainingSet(), fileProcessor);
    }

    public static DataSet buildDataSet(String dataPath,
                                        FileProcessor fileProcessor){
        var initialDataSet = buildInitialDataSet(dataPath, fileProcessor);

        return buildSet(initialDataSet, initialDataSet.getLongestInput());
    }

    public static DataSet buildDataSet(String dataPath, int longestInput,
                                        FileProcessor fileProcessor){

        var initialDataSet = buildInitialDataSet(dataPath, longestInput, fileProcessor);

        return buildSet(initialDataSet, longestInput);
    }

    private static DataSet buildSet(TrainingSet trainingSet, int longestInput){
        var outputSize = getOutputSize(trainingSet);

        DataSet dataSet = new DataSet(longestInput, outputSize);

        for(Row row: trainingSet.getRows()){
            dataSet.add(row.getInputs(), row.getOutputs());
        }

        return dataSet;
    }

    private static int getOutputSize(TrainingSet trainingSet){
        return trainingSet.getRows().get(0).getOutputs().length;
    }

    private static int getClassNum(String fileName){
        var realFileName = fileName.split("\\.");
        return Integer.parseInt(realFileName[0]);
    }
}
