/*
 * This file is generated by jOOQ.
*/
package com.example.dao;


import com.example.dao.tables.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Schema extends SchemaImpl {

    private static final long serialVersionUID = 646641938;

    /**
     * The reference instance of <code>schema</code>
     */
    public static final Schema SCHEMA = new Schema();

    /**
     * The table <code>schema.user</code>.
     */
    public final User USER = com.example.dao.tables.User.USER;

    /**
     * No further instances allowed
     */
    private Schema() {
        super("schema", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            User.USER);
    }
}
