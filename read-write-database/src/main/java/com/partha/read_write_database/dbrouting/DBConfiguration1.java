//package com.partha.read_write_database.dbrouting;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//
//
////optinally one can uncomment . however the program works fine even without them with default configurations
////@EnableTransactionManagement @EnableTransactionManagement and @EnableJpaRepositories
////@EnableJpaRepositories(
////        //basePackages = "${jpa.repositories.base-package}", //one can make it parameterized also as shown
////        basePackages = "com.partha.read_write_database.repositories",
////        entityManagerFactoryRef = "entityManagerFactory",
////        transactionManagerRef = "transactionManager"
////)
//@Configuration
//public class DBConfiguration1 {
//
//    /** read-only datasource related beans **/
//    @Bean(name="readOnlyDataSourceProperties")
//    //@ConfigurationProperties(prefix="spring.datasource.read-datasource")
//    @ConfigurationProperties(prefix="myapp.read-datasource")
//    public DataSourceProperties readOnlyDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//    @Bean(name="readOnlyDataSource")
//    @DependsOn("readOnlyDataSourceProperties")
//    public DataSource readOnlyDataSource(DataSourceProperties readOnlyDataSourceProperties){
//        return readOnlyDataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean(name="readOnlyJdbcTemplate")
//    @DependsOn("readOnlyDataSource")
//    public JdbcTemplate readOnlyJdbcTemplate(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource){
//        return new JdbcTemplate(readOnlyDataSource);
//    }
//
//    @Bean(name="readOnlyNamedJdbcTemplate")
//    @DependsOn("readOnlyDataSource")
//    public NamedParameterJdbcTemplate readOnlyNamedJdbcTemplate(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource){
//        return new NamedParameterJdbcTemplate(readOnlyDataSource);
//    }
//
//
//    /** read-write datasource related beans **/
//    @Bean(name="readWriteDataSourceProperties")
//    //@ConfigurationProperties(prefix="spring.datasource.write-datasource")
//    @ConfigurationProperties(prefix="myapp.write-datasource")
//    public DataSourceProperties readWriteDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//
//    @Bean(name="readWriteDataSource")
//    @DependsOn("readWriteDataSourceProperties")
//    public DataSource readWriteDataSource(DataSourceProperties readWriteDataSourceProperties){
//        return readWriteDataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//
//    @Bean(name="readWriteJdbcTemplate")
//    @DependsOn("readWriteDataSource")
//    public JdbcTemplate readWriteJdbcTemplate(@Qualifier("readWriteDataSource") DataSource readWriteDataSource){
//        return new JdbcTemplate(readWriteDataSource);
//    }
//
//    @Bean(name="readWriteNamedJdbcTemplate")
//    @DependsOn("readWriteDataSource")
//    public NamedParameterJdbcTemplate readWriteNamedJdbcTemplate(@Qualifier("readWriteDataSource") DataSource readWriteDataSource){
//        return new NamedParameterJdbcTemplate(readWriteDataSource);
//    }
//
//    /** multi-routing logic starts here **/
//
//    @Primary
//    @Bean(name="multiRoutingDataSource")
//    @DependsOn({"readWriteDataSource","readOnlyDataSource"})
//    public DataSource multiRoutingDataSource(){
//        Map<Object,Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSources.READONLY, readOnlyDataSource(readOnlyDataSourceProperties()));
//        targetDataSources.put(DataSources.READWRITE, readWriteDataSource(readWriteDataSourceProperties()));
//        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
//        multiRoutingDataSource.setDefaultTargetDataSource(readWriteDataSource(readWriteDataSourceProperties()));
//        multiRoutingDataSource.setTargetDataSources(targetDataSources);
//        return multiRoutingDataSource;
//    }
//
//    @Primary
//    @Bean(name="entityManagerFactory")
//    @DependsOn("multiRoutingDataSource")
//    public LocalSessionFactoryBean dbSessionFactory(){
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(multiRoutingDataSource());
//        sessionFactoryBean.setPackagesToScan("com.partha.read_write_database.entities"); //it is to be noted that the entity packages need to be passed here
//
//        Properties prop=new Properties();
//        //additional hibernate properties can be set using this
//        sessionFactoryBean.setHibernateProperties(prop);
//        return sessionFactoryBean;
//    }
//
//    @Primary
//    @Bean(name="transactionManager")
//    @DependsOn("entityManagerFactory")
//    public PlatformTransactionManager transactionManager(){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(dbSessionFactory().getObject());
//        return transactionManager;
//    }
//
//
//}
