package com.hhistory.data.mvc.springframework.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceUnit;

/**
 * Configuration class for creating SQL database beans
 *
 * @author gonzalo
 */
@Configuration
@ComponentScan(SqlDatabaseConfig.BASE_PACKAGE_NAME + "dao")
@EntityScan(SqlDatabaseConfig.BASE_PACKAGE_NAME + "model")
@EnableTransactionManagement
@PersistenceUnit
public class SqlDatabaseConfig {

    public static final String BASE_PACKAGE_NAME = "com.hhistory.data.";
}