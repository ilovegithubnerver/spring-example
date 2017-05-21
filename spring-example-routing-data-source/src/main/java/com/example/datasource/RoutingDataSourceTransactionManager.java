package com.example.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

public class RoutingDataSourceTransactionManager extends DataSourceTransactionManager {

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean isReadOnly = definition.isReadOnly();
        if (isReadOnly) {
            RoutingDataSourceHolder.set(RoutingDataSourceType.READ);
        } else {
            RoutingDataSourceHolder.set(RoutingDataSourceType.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        RoutingDataSourceHolder.clear();
    }
}
