package com.partha.read_write_database.dbrouting;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getCurrentDb();
    }
}
