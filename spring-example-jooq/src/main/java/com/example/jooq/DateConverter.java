package com.example.jooq;

import org.jooq.Converter;

public class DateConverter implements Converter<java.sql.Date, java.util.Date> {
    @Override
    public java.util.Date from(java.sql.Date databaseObject) {
        return new java.util.Date(databaseObject.getTime());
    }

    @Override
    public java.sql.Date to(java.util.Date userObject) {
        return new java.sql.Date(userObject.getTime());
    }

    @Override
    public Class<java.sql.Date> fromType() {
        return java.sql.Date.class;
    }

    @Override
    public Class<java.util.Date> toType() {
        return java.util.Date.class;
    }
}
