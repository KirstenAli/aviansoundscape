package org.example.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TrainingSet {
    private final List<Row> rows;
    private int longestInput;

    public TrainingSet() {
        rows = new ArrayList<>();
    }

    public void add(Row row){
        if(row.getPreInputLength() > longestInput)
            longestInput = row.getPreInputLength();

        rows.add(row);
    }

    public TrainingSet build(){
        var maxClass = getMaxClass();

        for(Row row: rows){
            var input = convertFloatArrayToDoubleArray(row.getPreInputs(), longestInput);
            var output = buildOutputArray(row, maxClass);

            row.setInputs(input);
            row.setOutputs(output);
        }

        return this;
    }

    private static double[] convertFloatArrayToDoubleArray(float[] floatArray, int length) {
        double[] doubleArray = new double[length];

        for (int i = 0; i <floatArray.length; i++){
            doubleArray[i] = floatArray[i];
        }

        return doubleArray;
    }

    private int getMaxClass(){
        var maxClass=0;

        for (Row row: rows){
            if(row.getClassNum()>maxClass)
                maxClass=row.getClassNum();
        }
        return maxClass;
    }

    private double[] buildOutputArray(Row row, int maxClass){
        var output = new double[maxClass];
        output[row.getClassNum()-1] = 1;

        return output;
    }
}
