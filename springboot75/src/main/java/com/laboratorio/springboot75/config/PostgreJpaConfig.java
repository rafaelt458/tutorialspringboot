package com.laboratorio.springboot75.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.laboratorio.springboot75.repository.postgre",
        entityManagerFactoryRef = "postgreEntityManagerFactory",
        transactionManagerRef = "postgreTransactionManager"
)
public class PostgreJpaConfig {
    @Primary
    @Bean("postgreDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.postgre")
    public DataSourceProperties postgreDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("postgreDatasource")
    public DataSource postgreDatasource(
            @Qualifier("postgreDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean("postgreEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder postgreEntityManagerFactoryBuilder() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                dataSource -> properties,
                null
        );
    }

    @Primary
    @Bean("postgreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgreEntityManagerFactory(
            @Qualifier("postgreEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("postgreDatasource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.laboratorio.springboot75.model.postgre")
                .persistenceUnit("postgrePU")
                .build();
    }

    @Primary
    @Bean("postgreTransactionManager")
    public PlatformTransactionManager postgreTransactionManager(
            @Qualifier("postgreEntityManagerFactory")EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}