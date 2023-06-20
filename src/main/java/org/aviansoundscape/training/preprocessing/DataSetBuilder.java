package org.aviansoundscape.training.preprocessing;

import lombok.Getter;
import lombok.Setter;
import org.neuroph.core.data.DataSet;

import java.io.File;

@Getter @Setter
public class DataSetBuilder {

    private static void buildInitialDataSet(String directoryPath,
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
    }

    private static TrainingSet buildInitialDataSet(String directoryPath,
                                                   int longestInput,
                                                   FileProcessor fileProcessor) {
        var initialDataSet = new TrainingSet();

        buildInitialDataSet(directoryPath, initialDataSet, fileProcessor);

        initialDataSet.setLongestInput(longestInput);
        return initialDataSet.build();
    }

    private static TrainingSet buildInitialDataSet(String directoryPath,
                                                   FileProcessor fileProcessor) {
        var initialDataSet = new TrainingSet();

        buildInitialDataSet(directoryPath, initialDataSet,
                fileProcessor);

        return initialDataSet.build();
    }

    public static DataSet buildDataSet(String dataPath,
                                        FileProcessor fileProcessor){
        var trainingSet = buildInitialDataSet(dataPath, fileProcessor);

        return buildSet(trainingSet, trainingSet.getLongestInput());
    }

    public static DataSet buildDataSet(String dataPath, int longestInput,
                                        FileProcessor fileProcessor){

        var trainingSet = buildInitialDataSet(dataPath, longestInput, fileProcessor);

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

    private static int getOutputSize(TrainingSet trainingSet){
        return trainingSet.getRows().get(0).getOutputs().length;
    }

    private static int getClassNum(String fileName){
        var realFileName = fileName.split("\\.");
        return Integer.parseInt(realFileName[0]);
    }
}
