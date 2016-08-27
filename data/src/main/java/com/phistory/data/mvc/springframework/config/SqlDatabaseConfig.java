package com.tcp.data.mvc.springframework.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for creating SQL database beans 
 * 
 * @author gonzalo
 *
 */
@Configuration()
@ComponentScan(basePackages = {"com.tcp.data.dao"})
@EnableTransactionManagement()
@EnableCaching
@Slf4j
public class SqlDatabaseConfig
{
    private static ObjectPool<PoolableConnection> objectPool = null;
    private static PoolingDataSource<?> poolingDataSource = null;
    private static LocalSessionFactoryBean sessionFactoryBean = null;
    private static DriverManagerDataSource dataSource = null;  
    private static PoolableConnectionFactory poolableConnectionFactory = null;
    @Resource
	private Environment environment; 
    
    // First, we'll create a ConnectionFactory that the pool will use to create Connections. We'll use the DriverManagerConnectionFactory
    @Bean
    public ConnectionFactory connectionFactory()
    {    	 
    	log.info("Creating bean connectionFactory");
    	
        ConnectionFactory dataSourceConnectionFactory = new DriverManagerConnectionFactory(environment.getProperty("db.url"),
        									  											   environment.getProperty("db.username"),
        									  											   environment.getProperty("db.password"));
        
        return dataSourceConnectionFactory;
    }
    
    // Next we'll create the PoolableConnectionFactory, which wraps the "real" Connections created by the ConnectionFactory with
    // the classes that implement the pooling functionality.
    @Bean
    public PoolableConnectionFactory poolableConnectionFactory()
    {
    	log.info("Creating bean poolableConnectionFactory");
    	
    	if (poolableConnectionFactory == null)
    	{
    		poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory(), null);
    		poolableConnectionFactory.setPool(objectPool());
    	}    	
        
        return poolableConnectionFactory;
    }
    
    // Now we'll need a ObjectPool that serves as the actual pool of connections.
    //@Bean
    public ObjectPool<PoolableConnection> objectPool()
    {
    	log.info("Creating bean objectPool");
    	
		if (objectPool == null)
		{
			objectPool = new GenericObjectPool<>(poolableConnectionFactory());			
		}       
      
       return objectPool;
    }
    
    @Bean
    public DataSource driverManagerDataSource()
    {        
    	log.info("Creating bean dataSource");
    	
        if(dataSource == null)
        {
        	dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getProperty("db.driverClassName")); 
            dataSource.setUrl(environment.getProperty("db.url"));
            dataSource.setUsername(environment.getProperty("db.username"));
            dataSource.setPassword(environment.getProperty("db.password"));
        }        
        
        return dataSource;
    }
    
    // Finally, we create the PoolingDriver itself, passing in the object pool we created.
    //@Bean()
    @DependsOn("poolableConnectionFactory")
    public PoolingDataSource<?> poolingDataSource()
    {
    	log.info("Creating bean poolingDataSource");
    	
		if (poolingDataSource == null)
		{ 
			poolingDataSource = new PoolingDataSource<>(objectPool());
		}
        
        return poolingDataSource;
    }   
    
    @Bean()
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
    	log.info("Creating bean entityManagerFactory");
    	
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(driverManagerDataSource());
        entityManagerFactory.setPackagesToScan("com.tcp.data.model");
        entityManagerFactory.setJpaProperties(this.createHibernateJpaProperties());
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        
        return entityManagerFactory;
    } 
    
    @Bean(name="sessionFactory", autowire=Autowire.BY_NAME, destroyMethod="destroy")
    public LocalSessionFactoryBean sessionFactory() throws IOException
    {    	
    	log.info("Creating bean sessionFactory");
    	
		if (sessionFactoryBean == null)
		{
			sessionFactoryBean = new LocalSessionFactoryBean();
			sessionFactoryBean.setDataSource(driverManagerDataSource());
			sessionFactoryBean.setPackagesToScan("com.tcp.data.model");
			sessionFactoryBean.setHibernateProperties(createHibernateJpaProperties());
		}        
        
        return sessionFactoryBean;
    }
        
    @Bean
    public HibernateTransactionManager transactionManager()
    {
       log.info("Creating bean transactionManager");
    	
       HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactoryBean.getObject());
  
       return transactionManager;
    }
    
    @Bean
    public OpenSessionInViewFilter openSessionInViewFilter()
    {
    	log.info("Creating bean openSessionInViewFilter");
    	
        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
        openSessionInViewFilter.setSessionFactoryBeanName("sessionFactory");
        
        return openSessionInViewFilter;
    }
    
    @Bean
    public CacheManager cacheManager()
    {
    	log.info("Creating bean cacheManager");
    	
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("default")));
        
        return cacheManager;
    }
    
    private Properties createHibernateJpaProperties()
    {        
        Properties hibernateJpaProperties = new Properties();
        
        hibernateJpaProperties.setProperty("hibernate.dialect", environment.getProperty("db.hibernateDialect"));
        hibernateJpaProperties.setProperty("hibernate.show_sql", environment.getProperty("db.hibernate.show_sql"));
        hibernateJpaProperties.setProperty("hibernate.format_sql", environment.getProperty("db.hibernate.format_sql"));
        hibernateJpaProperties.setProperty("hibernate.order_updates", "true");
        hibernateJpaProperties.setProperty("hibernate.id.new_generator_mappings", "true");
        hibernateJpaProperties.setProperty("hibernate.connection.SetBigStringTryClob", "true");
        hibernateJpaProperties.setProperty("hibernate.connection.release_mode", "auto");
        hibernateJpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("db.hibernateHbm2ddl"));
        hibernateJpaProperties.setProperty("hibernate.bytecode.provider", "cglib");
        hibernateJpaProperties.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
        hibernateJpaProperties.setProperty("jadira.usertype.databaseZone", "jvm");
        hibernateJpaProperties.setProperty("jadira.usertype.javaZone", "jvm");
        
        return hibernateJpaProperties;
    }
}