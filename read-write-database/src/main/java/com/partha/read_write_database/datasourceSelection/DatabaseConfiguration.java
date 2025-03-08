package com.partha.read_write_database.datasourceSelection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DatabaseConfiguration {

    @Autowired
    ApplicationContext context;

    @Bean(name="readOnlyDataSourceProperties")
    @ConfigurationProperties(prefix="myapp.read-datasource")
    public DataSourceProperties readOnlyDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="readOnlyDataSource")
    @DependsOn("readOnlyDataSourceProperties")
    public DataSource readOnlyDataSource(DataSourceProperties readOnlyDataSourceProperties){
        return readOnlyDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="readWriteDataSourceProperties")
    @ConfigurationProperties(prefix="myapp.write-datasource")
    public DataSourceProperties readWriteDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(name="readWriteDataSource")
    @DependsOn("readWriteDataSourceProperties")
    public DataSource readWriteDataSource(DataSourceProperties readWriteDataSourceProperties){
        return readWriteDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name="multiRoutingDataSource")
    @DependsOn({"readWriteDataSource","readOnlyDataSource"})
    public DataSource dataSource()  {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        resolvedDataSources.put(DataSources.READONLY, context.getBean("readOnlyDataSource"));
        resolvedDataSources.put(DataSources.READWRITE, context.getBean("readWriteDataSource"));
        AbstractRoutingDataSource dataSource = new MultiRoutingDataSource();

        //DataSource defaultDatasource = dataSourceBuilder.build();
        dataSource.setDefaultTargetDataSource(context.getBean("readWriteDataSource", DataSource.class));
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    }

//    @Primary
//    @Bean(name="entityManagerFactory")
//    @DependsOn("multiRoutingDataSource")
//    public LocalSessionFactoryBean dbSessionFactory(){
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource());
//
//        Properties prop=new Properties();
//        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        sessionFactoryBean.setHibernateProperties(prop);
//        return sessionFactoryBean;
//    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean storingEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("multiRoutingDataSource") DataSource ds) {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean emf = builder
                .dataSource(ds)
                .packages("com.test.supplier1")
               // .persistenceUnit("primaryPU")
                .build();

        emf.setJpaProperties(properties);

        return emf;
    }





}
