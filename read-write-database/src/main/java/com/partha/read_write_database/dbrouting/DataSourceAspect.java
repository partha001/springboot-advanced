package com.partha.read_write_database.dbrouting;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@EnableAspectJAutoProxy
@Order(1)
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(DataSourceSelector)")
    private void pointCutofDataSource(){
    }

    @Before("pointCutofDataSource()")
    public void before(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSourceSelector myAnnotation = method.getAnnotation(DataSourceSelector.class);
        DataSources dataSource = myAnnotation.value();
        DBContextHolder.setCurrentDb(dataSource);
    }


    @After("pointCutofDataSource()")
    public void after(JoinPoint point){
        DBContextHolder.clear();
    }
}
