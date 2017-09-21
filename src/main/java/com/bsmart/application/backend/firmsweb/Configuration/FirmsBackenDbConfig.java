package com.bsmart.application.backend.firmsweb.Configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class FirmsBackenDbConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "firmswebbackenddb.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
