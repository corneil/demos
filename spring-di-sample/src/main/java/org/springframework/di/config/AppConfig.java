package org.springframework.di.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@PropertySource("db.properties")
public class AppConfig {
    @Autowired
    Environment env;

    @Bean
    public Destination destination() {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("jms/queue/Profile");
        bean.setExpectedType(Destination.class);
        return (Destination) bean.getObject();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("jms/queue/ConnectionFactory");
        bean.setExpectedType(ConnectionFactory.class);
        return (ConnectionFactory) bean.getObject();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }
}
