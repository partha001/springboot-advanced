package com.partha.read_write_database.datasourceSelection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
//@EnableCaching
@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "${jpa.repositories.base-package}",
//        entityManagerFactoryRef = "entityManager",
//        transactionManagerRef = "multiTransactionManager")
public class DBConfiguration {

    /** read-only datasource related beans **/
    @Bean(name="readOnlyDataSourceProperties")
    @ConfigurationProperties(prefix="")
    public DataSourceProperties readOnlyDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="readOnlyDatasource")
    @DependsOn("readOnlyDataSourceProperties")
    public DataSource readOnlyDataSource(DataSourceProperties readOnlyDataSourceProperties){
        return readOnlyDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="readOnlyJdbcTemplate")
    @DependsOn("readOnlyDataSource")
    public JdbcTemplate readOnlyJdbcTemplate(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource){
        return new JdbcTemplate(readOnlyDataSource);
    }

    @Bean(name="readOnlyNamedJdbcTemplate")
    @DependsOn("readOnlyDataSource")
    public NamedParameterJdbcTemplate readOnlyNamedJdbcTemplate(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource){
        return new NamedParameterJdbcTemplate(readOnlyDataSource);
    }


    /** read-write datasource related beans **/
    @Bean(name="readWriteDataSourceProperties")
    @ConfigurationProperties(prefix="")
    public DataSourceProperties readWriteDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(name="readWriteDatasource")
    @DependsOn("readWriteDataSourceProperties")
    public DataSource readWriteDataSource(DataSourceProperties readWriteDataSourceProperties){
        return readWriteDataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean(name="readWriteJdbcTemplate")
    @DependsOn("readWriteDataSource")
    public JdbcTemplate readWriteJdbcTemplate(@Qualifier("readWriteDataSource") DataSource readWriteDataSource){
        return new JdbcTemplate(readWriteDataSource);
    }

    @Bean(name="readWriteNamedJdbcTemplate")
    @DependsOn("readWriteDataSource")
    public NamedParameterJdbcTemplate readWriteNamedJdbcTemplate(@Qualifier("readWriteDataSource") DataSource readWriteDataSource){
        return new NamedParameterJdbcTemplate(readWriteDataSource);
    }

    /** multi-routing logic starts here **/

    @Primary
    @Bean(name="multiRoutingDataSource")
    @DependsOn({"readWriteDataSource","readOnlyDataSource"})
    public DataSource multiRoutingDataSource(){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSources.READONLY, readOnlyDataSource(readOnlyDataSourceProperties()));
        targetDataSources.put(DataSources.READWRITE, readWriteDataSource(readWriteDataSourceProperties()));
        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(readWriteDataSource(readWriteDataSourceProperties()));
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }

    @Primary
    @Bean(name="entityManagerFactory")
    @DependsOn("multiRoutingDataSource")
    public LocalSessionFactoryBean dbSessionFactory(){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        //sessionFactoryBean.setPackagesToScan("packageName");
        return sessionFactoryBean;
    }

    @Primary
    @Bean(name="transactionManager")
    @DependsOn("entityManagerFactory")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dbSessionFactory().getObject());
        return transactionManager;
    }

//    @Primary
//    public CacheManager cacheManager(){
//        return new ConcurrentMapCacheManager();
//    }

}
