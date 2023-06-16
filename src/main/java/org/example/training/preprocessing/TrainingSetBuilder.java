package org.example.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter @Setter
public class TrainingSetBuilder {

    private static TrainingSet constructTrainingSet(String directoryPath,
                                                    TrainingSet trainingSet){

        var directory = new File(directoryPath);
        var fileNames = directory.list();

        if(fileNames!=null) {
            for(String fileName : fileNames) {
                var row = new Row();

                row.setPreInputs(MFCCExtractor.extractMFCC(directoryPath+ "/" +fileName));

                var output = buildOutputArray(fileName, fileNames.length);
                row.setOutputs(output);

                trainingSet.add(row);
            }
        }

        return trainingSet.buildInputs();
    }

    public static TrainingSet buildTrainingSet(String directoryPath,
                                               int longestInput) {
        var trainingSet = new TrainingSet();
        trainingSet.setLongestInput(longestInput);

        return constructTrainingSet(directoryPath, trainingSet);
    }

    public static TrainingSet buildTrainingSet(String directoryPath) {
        return constructTrainingSet(directoryPath, new TrainingSet());
    }

    private static double[] buildOutputArray(String fileName, int numOfFiles){
        var realFileName = fileName.split("\\.");
        var num = Integer.parseInt(realFileName[0]);

        var output = new double[numOfFiles];
        output[num-1] = 1;

        return output;
    }
}
