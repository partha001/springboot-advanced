package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.TenantDatabaseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class MultitenantConfiguration {

    @Value("${default.datasource.url}")
    private String defaultUrl;

    @Value("${default.datasource.username}")
    private String defaultUsername;

    @Value("${default.datasource.password}")
    private String defaultPassword;

    @Bean
    public DataSource dataSource()  {
        List<TenantDatabaseDto> connectionList = readDataSourceConfiguration();
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (TenantDatabaseDto dto : connectionList) {
            String tenantId = dto.getName();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(dto.getDriverClass());
            dataSourceBuilder.username(dto.getUsername());
            dataSourceBuilder.password(dto.getPassword());
            dataSourceBuilder.url(dto.getUrl());
            resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        }


        AbstractRoutingDataSource dataSource = new MultitenantDataSource();

        //setting the default datasource here.  here these properties are being hardcoded but. this can also come from some file.
        //dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/mydatabase");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("password");

        DataSource defaultDatasource = dataSourceBuilder.build();
        dataSource.setDefaultTargetDataSource(defaultDatasource);
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();

        return dataSource;
    }


    private List<TenantDatabaseDto> readDataSourceConfiguration()  {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TenantDatabaseDto> list = new ArrayList<TenantDatabaseDto>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(defaultUrl, defaultUsername, defaultPassword);
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id, name, url, username, password, driverClassName from DataSourceConfiguration ");
            while (rs.next()) {
                log.info("id:{}   name:{}   url:{}   username:{}   password:{}   driverClassName:{}",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("url"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("driverClassName")
                );
                TenantDatabaseDto dto = TenantDatabaseDto.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .url(rs.getString("url"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .driverClass(rs.getString("driverClassName"))
                        .build();
                list.add(dto);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(stmt!=null ) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return list;
    }

}