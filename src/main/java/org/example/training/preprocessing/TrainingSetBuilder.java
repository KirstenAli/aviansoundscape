package org.example.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter @Setter
public class TrainingSetBuilder {

    private TrainingSet constructTrainingSet(String directoryPath,
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

    public TrainingSet buildTrainingSet(String directoryPath,
                                        int longestInput,
                                        FileProcessor fileProcessor)
    {
        var trainingSet = new TrainingSet();
        trainingSet.setLongestInput(longestInput);

        return constructTrainingSet(directoryPath, trainingSet, fileProcessor);
    }

    public TrainingSet buildTrainingSet(String directoryPath,
                                        FileProcessor fileProcessor) {
        return constructTrainingSet(directoryPath, new TrainingSet(), fileProcessor);
    }

    private int getClassNum(String fileName){
        var realFileName = fileName.split("\\.");
        return Integer.parseInt(realFileName[0]);
    }
}
