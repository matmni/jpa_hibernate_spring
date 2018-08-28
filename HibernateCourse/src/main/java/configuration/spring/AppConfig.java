package configuration.spring;


import hibernate.Config;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan(basePackages = {"service", "entity.persistence", "entity.hibernate", "configuration.spring", "entity.spring"})
@EnableJpaRepositories(basePackages = {"spring.repository"})
@EnableTransactionManagement
public class AppConfig {

    @Bean(name = "entityManagerFactory")
    public SessionFactory sessionFactory() {
        return Config.sessionFactory();
    }

    @Bean // pokazać, że potrzebne po dodaniu beana entityManagerFactory - doczytać o transaction manager'rze
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }
}
