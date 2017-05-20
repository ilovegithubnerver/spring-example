package com.example.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (RoutingDataSourceType.WRITE.equals(RoutingDataSourceHolder.get()))
            return RoutingDataSourceType.WRITE;
        return RoutingDataSourceType.READ;
    }
}
