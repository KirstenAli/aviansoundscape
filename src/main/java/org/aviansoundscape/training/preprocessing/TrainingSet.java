package org.aviansoundscape.training.preprocessing;

public class TrainingSet extends InitialDataSet {

    @Override
    public void add(Row row){
        if(row.getPreInputLength() > getLongestInput())
            setLongestInput(row.getPreInputLength());

        getRows().add(row);
    }
}
