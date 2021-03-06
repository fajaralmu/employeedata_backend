package com.fajar.employeedata.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.fajar.employeedata.entities.Employee;
import com.fajar.employeedata.entities.Position;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HibernateSessionConfig {
	private static SessionFactory factory;

	@Autowired
	private DriverManagerDataSource driverManagerDataSource;
	@Autowired
	private EntityManagerFactory entityManagerFactoryBean;

	@Bean
	public SessionFactory generateSession() {
		try {
			org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

			configuration.setProperties(additionalProperties());

			addAnnotatedClass(configuration);

			factory = configuration./* setInterceptor(new HibernateInterceptor()). */buildSessionFactory();
			log.info("Session Factory has been initialized");
			return factory;
		} catch (Exception ex) {

			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	private void addAnnotatedClass(org.hibernate.cfg.Configuration configuration) {

		 
			configuration.addAnnotatedClass(Position.class);
			configuration.addAnnotatedClass(Employee.class);

	}

	private Properties additionalProperties() {

		String dialect = entityManagerFactoryBean.getProperties().get("hibernate.dialect").toString();
		String showSql = entityManagerFactoryBean.getProperties().get("hibernate.show_sql").toString();
		String ddlAuto = entityManagerFactoryBean.getProperties().get("hibernate.hbm2ddl.auto").toString();
		String use_jdbc_metadata_defaults = entityManagerFactoryBean.getProperties().get("hibernate.temp.use_jdbc_metadata_defaults").toString();
		Class<? extends Driver> driverClass = org.postgresql.Driver.class;// com.mysql.jdbc.Driver.class;
		try {
			String connectionUrl =(driverManagerDataSource.getConnection().getMetaData().getURL());
			log.info("CONNECTION URL: {}", connectionUrl);
			driverClass = DriverManager.getDriver(connectionUrl).getClass();
			log.info("DRIVER CLASSNAME: {}", driverClass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		printProps(entityManagerFactoryBean.getProperties(), "entityManagerFactoryBean");
//		printProps(driverManagerDataSource.getConnectionProperties(), "driverManagerDataSource");
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.connection.url", driverManagerDataSource.getUrl());
		properties.setProperty("hibernate.connection.username", driverManagerDataSource.getUsername());
		properties.setProperty("hibernate.connection.password", driverManagerDataSource.getPassword());

		properties.setProperty("hibernate.connection.driver_class", driverClass.getCanonicalName());
		properties.setProperty("hibernate.current_session_context_class", "thread");
		properties.setProperty("hibernate.show_sql", showSql);
//		properties.setProperty("hibernate.connection.pool_size", "1");
		properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults",use_jdbc_metadata_defaults);
		properties.setProperty("hbm2ddl.auto", ddlAuto);
		return properties;
	}
}
