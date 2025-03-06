package com.partha.read_write_database.datasourceSelection;

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
        DataSources dataSource = DataSources.READWRITE;
        Class<?> clazz = point.getTarget().getClass();
        String  methodName = point.getSignature().getName();
        Class<?>[] argClazz = ((MethodSignature)point.getSignature()).getParameterTypes();
        try{
            dataSource = getDataSource(clazz, methodName, argClazz);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        DBContextHolder.setCurrentDb(dataSource);

    }

    private DataSources getDataSource(Class<?> clazz,String methodName, Class<?>[] argClass) throws NoSuchMethodException{
        Method method = clazz.getDeclaredMethod(methodName, argClass);

        if(method.isAnnotationPresent(DataSourceSelector.class)) {
            DataSourceSelector annotation = method.getAnnotation(DataSourceSelector.class);
            return annotation.value();
        }

        if(clazz.isAnnotationPresent(DataSourceSelector.class)) {
            DataSourceSelector annotation = method.getAnnotation(DataSourceSelector.class);
            return annotation.value();
        }
        return DataSources.READWRITE; //default
    }

    @After("pointCutofDataSource()")
    public void after(JoinPoint point){
        DBContextHolder.clear();
    }
}
