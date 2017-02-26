package com.phistory.data.mvc.springframework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class for creating SQL database beans
 *
 * @author gonzalo
 */
@Configuration
@ComponentScan(basePackages = {"com.phistory.data.dao"})
@EnableTransactionManagement
@PersistenceUnit()
@EnableCaching
@Slf4j
public class SqlDatabaseConfig {

    @Bean
    public LocalSessionFactoryBean  sessionFactory(DataSource dataSource) throws ClassNotFoundException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.phistory.data.model");
        sessionFactoryBean.setHibernateProperties(this.createHibernateJpaProperties());

        return sessionFactoryBean;
    }

    private Properties createHibernateJpaProperties() {
        Properties hibernateJpaProperties = new Properties();
        hibernateJpaProperties.setProperty("hibernate.order_updates", "true");
        hibernateJpaProperties.setProperty("hibernate.id.new_generator_mappings", "true");
        hibernateJpaProperties.setProperty("hibernate.connection.SetBigStringTryClob", "true");
        hibernateJpaProperties.setProperty("hibernate.connection.release_mode", "auto");
        hibernateJpaProperties.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
        hibernateJpaProperties.setProperty("jadira.usertype.databaseZone", "jvm");
        hibernateJpaProperties.setProperty("jadira.usertype.javaZone", "jvm");

        return hibernateJpaProperties;
    }
}