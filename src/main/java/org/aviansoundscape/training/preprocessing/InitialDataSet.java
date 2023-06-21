package org.aviansoundscape.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InitialDataSet {
    private final List<Row> rows;
    private int longestInput;

    public InitialDataSet() {
        rows = new ArrayList<>();
    }

    public void add(Row row){
        rows.add(row);
    }
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
        return normalizeArrayLength(row.getPreInputs(), longestInput);
    }

    public static double[] normalizeArrayLength(double[] preInput, int standardLength){

        if(preInput.length==standardLength)
            return preInput;

        double[] standardizedPreInput = new double[standardLength];

        var minLength = Math.min(preInput.length, standardLength);

        System.arraycopy(preInput, 0, standardizedPreInput, 0, minLength);

        return standardizedPreInput;
    }

    private int getMaxClass(){
        var maxClass=Integer.MIN_VALUE;

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
