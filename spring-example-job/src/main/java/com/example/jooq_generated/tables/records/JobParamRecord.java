/**
 * This class is generated by jOOQ
 */
package com.example.jooq_generated.tables.records;


import com.example.jooq_generated.tables.JobParam;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


/**
 * 定时任务参数
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobParamRecord extends TableRecordImpl<JobParamRecord> implements Record4<String, String, String, String> {

    private static final long serialVersionUID = 1318872683;

    /**
     * Setter for <code>job_param.JOB_NAME</code>. 任务名
     */
    public void setJobName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>job_param.JOB_NAME</code>. 任务名
     */
    public String getJobName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>job_param.PARAM_KEY</code>. 参数名
     */
    public void setParamKey(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>job_param.PARAM_KEY</code>. 参数名
     */
    public String getParamKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>job_param.PARAM_VALUE</code>. 参数值
     */
    public void setParamValue(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>job_param.PARAM_VALUE</code>. 参数值
     */
    public String getParamValue() {
        return (String) get(2);
    }

    /**
     * Setter for <code>job_param.IS_ENABLE</code>. 0 禁用 1 启动
     */
    public void setIsEnable(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>job_param.IS_ENABLE</code>. 0 禁用 1 启动
     */
    public String getIsEnable() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return JobParam.JOB_PARAM.JOB_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JobParam.JOB_PARAM.PARAM_KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JobParam.JOB_PARAM.PARAM_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JobParam.JOB_PARAM.IS_ENABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getJobName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getParamKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getParamValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIsEnable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobParamRecord value1(String value) {
        setJobName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobParamRecord value2(String value) {
        setParamKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobParamRecord value3(String value) {
        setParamValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobParamRecord value4(String value) {
        setIsEnable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobParamRecord values(String value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JobParamRecord
     */
    public JobParamRecord() {
        super(JobParam.JOB_PARAM);
    }

    /**
     * Create a detached, initialised JobParamRecord
     */
    public JobParamRecord(String jobName, String paramKey, String paramValue, String isEnable) {
        super(JobParam.JOB_PARAM);

        set(0, jobName);
        set(1, paramKey);
        set(2, paramValue);
        set(3, isEnable);
    }
}
