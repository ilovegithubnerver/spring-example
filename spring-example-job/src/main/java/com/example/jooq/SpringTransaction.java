package com.example.jooq;

import org.jooq.Transaction;
import org.springframework.transaction.TransactionStatus;

public class SpringTransaction implements Transaction {

    TransactionStatus tx;

    public SpringTransaction(TransactionStatus tx) {
        this.tx = tx;
    }
}
