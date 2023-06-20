package org.aviansoundscape.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class AbstractDataSet {
    private final List<Row> rows;
    private int longestInput;

    public AbstractDataSet() {
        rows = new ArrayList<>();
    }

    abstract void add(Row row);
    public void build(){
        var maxClass = getMaxClass();

        for(Row row: rows){
            var input = buildInputs(row);
            var output = buildOutputArray(row, maxClass);

            row.setInputs(input);
            row.setOutputs(output);
        }
    }

    private double[] buildInputs(Row row){
        return convertFloatArrayToDoubleArray(row.getPreInputs(), longestInput);
    }

    private static double[] convertFloatArrayToDoubleArray(float[] floatArray, int length) {
        double[] doubleArray = new double[length];

        var floatArrayLength = Math.min(floatArray.length, length);

        for (int i = 0; i <floatArrayLength; i++){
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
