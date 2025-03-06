package com.partha.read_write_database.dbrouting;

public class DBContextHolder {

    //private static final ThreadLocal<DataSources> threadLocal = new ThreadLocal<DataSources>();
    private static final ThreadLocal<DataSources> threadLocal = ThreadLocal.withInitial(() -> DataSources.READWRITE);
    public static void setCurrentDb(DataSources dbType){
        threadLocal.set(dbType);
    }

    public static DataSources getCurrentDb(){
        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }
}
