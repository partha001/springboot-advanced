package com.partha.read_write_database.dbrouting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceSelector {
    DataSources value() default DataSources.READWRITE;
}
