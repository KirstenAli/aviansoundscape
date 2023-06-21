package org.aviansoundscape.training.preprocessing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InitialDataSet {
    private final List<Row> rows;
    private int inputLength;

    public InitialDataSet() {
        rows = new ArrayList<>();
    }

    public void add(Row row){
        rows.add(row);
    }

    public void build(){
        var maxClass = getMaxClass();

        for(Row row: rows){
            var input = normalizeArrayLength(row.getPreInputs(), inputLength);
            var output = buildOutputArray(row, maxClass);

            row.setInputs(input);
            row.setOutputs(output);
        }
    }

    public static double[] normalizeArrayLength(double[] preInput, int length){

        if (length==0)
            throw new RuntimeException("Input length is zero");

        if(preInput.length==length)
            return preInput;

        double[] result = new double[length];

        var minLength = Math.min(preInput.length, length);

        System.arraycopy(preInput, 0, result, 0, minLength);

        return result;
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
