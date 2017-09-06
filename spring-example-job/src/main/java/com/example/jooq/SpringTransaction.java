package com.example.jooq;

import org.jooq.Transaction;
import org.springframework.transaction.TransactionStatus;

/**
 * Created by liweitang on 2017/9/5.
 */
public class SpringTransaction implements Transaction {

    TransactionStatus tx;

    public SpringTransaction(TransactionStatus tx) {
        this.tx = tx;
    }
}
