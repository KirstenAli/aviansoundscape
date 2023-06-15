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

    public static TrainingSet buildTrainingSet(String directoryPath){

        var directory = new File(directoryPath);
        var fileNames = directory.list();
        var trainingSet = new TrainingSet();

        if(fileNames!=null) {
            for(String fileName : fileNames) {
                var row = new Row();

                row.setPreInputs(fileToByteArray(fileName));
                row.setOutputs(new double[]{Double.parseDouble(fileName)});

                trainingSet.add(row);
            }
        }

        return trainingSet.buildInputs();
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
