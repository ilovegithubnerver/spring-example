/**
 * This class is generated by jOOQ
 */
package com.example.dao.tables;


import com.example.dao.Keys;
import com.example.dao.Schema;
import com.example.dao.tables.records.ExampleRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 示例
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Example extends TableImpl<ExampleRecord> {

    private static final long serialVersionUID = -416683129;

    /**
     * The reference instance of <code>schema.example</code>
     */
    public static final Example EXAMPLE = new Example();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExampleRecord> getRecordType() {
        return ExampleRecord.class;
    }

    /**
     * The column <code>schema.example.id</code>. Id
     */
    public final TableField<ExampleRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Id");

    /**
     * The column <code>schema.example.name</code>. 名称
     */
    public final TableField<ExampleRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "名称");

    /**
     * The column <code>schema.example.code</code>. 编码
     */
    public final TableField<ExampleRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR.length(20).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "编码");

    /**
     * The column <code>schema.example.state</code>. 状态
     */
    public final TableField<ExampleRecord, String> STATE = createField("state", org.jooq.impl.SQLDataType.CHAR.length(1).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), this, "状态");

    /**
     * The column <code>schema.example.grade</code>. 等级
     */
    public final TableField<ExampleRecord, Boolean> GRADE = createField("grade", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "等级");

    /**
     * The column <code>schema.example.score</code>. 得分
     */
    public final TableField<ExampleRecord, Integer> SCORE = createField("score", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "得分");

    /**
     * The column <code>schema.example.price</code>. 价格
     */
    public final TableField<ExampleRecord, BigDecimal> PRICE = createField("price", org.jooq.impl.SQLDataType.DECIMAL.precision(14, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "价格");

    /**
     * The column <code>schema.example.birth</code>. 生日
     */
    public final TableField<ExampleRecord, Date> BIRTH = createField("birth", org.jooq.impl.SQLDataType.DATE, this, "生日");

    /**
     * The column <code>schema.example.sleep</code>. 睡眠
     */
    public final TableField<ExampleRecord, Date> SLEEP = createField("sleep", org.jooq.impl.SQLDataType.DATE, this, "睡眠");

    /**
     * The column <code>schema.example.createdDate</code>. 创建时间
     */
    public final TableField<ExampleRecord, Date> CREATEDDATE = createField("createdDate", org.jooq.impl.SQLDataType.DATE.defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.DATE)), this, "创建时间");

    /**
     * The column <code>schema.example.createdBy</code>. 创建人
     */
    public final TableField<ExampleRecord, Long> CREATEDBY = createField("createdBy", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("-1", org.jooq.impl.SQLDataType.BIGINT)), this, "创建人");

    /**
     * The column <code>schema.example.modifiedDate</code>. 最后修改时间
     */
    public final TableField<ExampleRecord, Date> MODIFIEDDATE = createField("modifiedDate", org.jooq.impl.SQLDataType.DATE.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.DATE)), this, "最后修改时间");

    /**
     * The column <code>schema.example.modifiedBy</code>. 操作人
     */
    public final TableField<ExampleRecord, Long> MODIFIEDBY = createField("modifiedBy", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("-1", org.jooq.impl.SQLDataType.BIGINT)), this, "操作人");

    /**
     * Create a <code>schema.example</code> table reference
     */
    public Example() {
        this("example", null);
    }

    /**
     * Create an aliased <code>schema.example</code> table reference
     */
    public Example(String alias) {
        this(alias, EXAMPLE);
    }

    private Example(String alias, Table<ExampleRecord> aliased) {
        this(alias, aliased, null);
    }

    private Example(String alias, Table<ExampleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "示例");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Schema getSchema() {
        return Schema.SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ExampleRecord, Long> getIdentity() {
        return Keys.IDENTITY_EXAMPLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ExampleRecord> getPrimaryKey() {
        return Keys.KEY_EXAMPLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ExampleRecord>> getKeys() {
        return Arrays.<UniqueKey<ExampleRecord>>asList(Keys.KEY_EXAMPLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Example as(String alias) {
        return new Example(alias, this);
    }

    /**
     * Rename this table
     */
    public Example rename(String name) {
        return new Example(name, null);
    }
}
