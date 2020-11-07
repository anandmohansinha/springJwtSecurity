
package com.example.jwt.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * with this configuration i explicitly created EntityManagerBean otherwise spring boot itself create it
 */
/*
@Configuration
@EnableTransactionManagement
// below annotation we are telling to spring boot that use my custom repository
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactory1",
                     basePackages = {
                        "com.example.jwt.repository"},
                        transactionManagerRef="transactionManager")
public class JwtDataSourceConfiguration {
    public JwtDataSourceConfiguration() {
        System.out.println("************************************************************************************************************");
    }

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.jwt")
    public DataSource jwtDataSource() {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "entityManagerFactory1")
    public LocalContainerEntityManagerFactoryBean jwtEntityManagerFactory(@Qualifier("dataSource") DataSource jwtDataSource,
                                                                          EntityManagerFactoryBuilder builder) {
        return builder.dataSource(jwtDataSource).packages("com.example.jwt.entity.jwt").build();
        // do i need to write PersistenceUnit ??
        // do i need to give properties of hibernate properties of default value it will take
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory1") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

*/
