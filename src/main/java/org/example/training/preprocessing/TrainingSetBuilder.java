package org.example.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter @Setter
public class TrainingSetBuilder {

    private static TrainingSet constructTrainingSet(String directoryPath,
                                                    int labelDivider,
                                                    TrainingSet trainingSet){

        if(labelDivider%10 != 0)
            throw new RuntimeException("Label divider must be a multiple of 10");

        var directory = new File(directoryPath);
        var fileNames = directory.list();

        if(fileNames!=null) {
            for(String fileName : fileNames) {
                var row = new Row();

                row.setPreInputs(fileToByteArray(directoryPath+ "/" +fileName));

                var realFileName = fileName.split("\\.");
                var output = Double.parseDouble(realFileName[0])/labelDivider;
                row.setOutputs(new double[]{output});

                trainingSet.add(row);
            }
        }

        return trainingSet.buildInputs();
    }

    public static TrainingSet buildTrainingSet(String directoryPath,
                                               int labelDivider,
                                               int longestInput) {
        var trainingSet = new TrainingSet();
        trainingSet.setLongestInput(longestInput);

        return constructTrainingSet(directoryPath, labelDivider, trainingSet);
    }

    public static TrainingSet buildTrainingSet(String directoryPath, int labelDivider) {
        return constructTrainingSet(directoryPath, labelDivider, new TrainingSet());
    }

    private double[] buildOutputArray(String fileName){
        return new double[]{1};
    }

    private static byte[] fileToByteArray(String filePath)  {
        Path path = Paths.get(filePath);

        try {
            return Files.readAllBytes(path);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
