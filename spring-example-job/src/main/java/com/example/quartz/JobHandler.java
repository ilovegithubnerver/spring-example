package com.example.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JobHandler implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Object target;

    public JobHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() != Job.class)
            return method.invoke(proxy, target);
        JobExecutionContext context = (JobExecutionContext) args[0];
        JobDetail jobDetail = context.getJobDetail();
        logger.info("开始执行Job: name={}, group={}, data={}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getJobDataMap().getWrappedMap());
        Object retVal = method.invoke(target, args);
        logger.info("Job执行结束: name={}, group={}, data={}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getJobDataMap().getWrappedMap());
        return retVal;
    }
}
