package org.example.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MultitenantConfiguration {

//	@Value("${defaultTenant}")
//	private String defaultTenant;

	@Bean
	@ConfigurationProperties(prefix = "tenants")
	public DataSource dataSource() throws FileNotFoundException {
		//File[] files = Paths.get("allTenants").toFile().listFiles();
		File[] files = ResourceUtils.getFile("classpath:allTenants").listFiles();
		Map<Object, Object> resolvedDataSources = new HashMap<>();

		//inserting all tenant detasoures
		for (File propertyFile : files) {
			Properties tenantProperties = new Properties();
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();


			log.info("name:{}  url:{}",tenantProperties.getProperty("name"), tenantProperties.getProperty("datasource.url") );



			try {
				tenantProperties.load(new FileInputStream(propertyFile));
				String tenantId = tenantProperties.getProperty("name");

				dataSourceBuilder.driverClassName(tenantProperties.getProperty("spring.datasource.driverClassName"));
				dataSourceBuilder.username(tenantProperties.getProperty("spring.datasource.username"));
				dataSourceBuilder.password(tenantProperties.getProperty("spring.datasource.password"));
				dataSourceBuilder.url(tenantProperties.getProperty("spring.datasource.url"));

				log.info("name:{}  url:{}",tenantId, tenantProperties.getProperty("spring.datasource.url") );

				resolvedDataSources.put(tenantId, dataSourceBuilder.build());
			} catch (IOException exp) {
				throw new RuntimeException("Problem in tenant datasource:" + exp);
			}           
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



}