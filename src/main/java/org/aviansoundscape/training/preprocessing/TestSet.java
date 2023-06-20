package org.aviansoundscape.training.preprocessing;

public class TestSet extends AbstractDataSet{
    @Override
    void add(Row row) {
        getRows().add(row);
    }
}
