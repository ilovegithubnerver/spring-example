package com.example.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (RoutingDataSourceType.READ.equals(RoutingDataSourceHolder.get()))
            return RoutingDataSourceType.READ;
        return RoutingDataSourceType.WRITE;
    }
}
