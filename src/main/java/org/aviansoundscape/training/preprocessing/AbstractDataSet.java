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
        return standardizePreInputLength(row.getPreInputs(), longestInput);
    }

    public static double[] standardizePreInputLength(double[] preInput, int longestInput) {

        if(preInput.length==longestInput)
            return preInput;

        double[] standardizedPreInput = new double[longestInput];

        var minLength = Math.min(preInput.length, longestInput);

        System.arraycopy(preInput, 0, standardizedPreInput, 0, minLength);

        return standardizedPreInput;
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
