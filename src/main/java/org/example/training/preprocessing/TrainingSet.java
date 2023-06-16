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

    public TrainingSet buildInputs(){
        for(Row row: rows){
            var input = convertByteArrayToDoubleArray(row.getPreInputs(), longestInput);

            row.setInputs(input);
        }

        return this;
    }

    private static double[] convertByteArrayToDoubleArray(byte[] byteArray, int length) {
        double[] doubleArray = new double[length];

        for (int i = 0; i <byteArray.length; i++) {
            doubleArray[i] = byteArray[i];
        }

        return doubleArray;
    }
}
