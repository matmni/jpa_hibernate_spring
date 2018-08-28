package hibernate;

import entity.hibernate.*;
import entity.spring.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.StudentHibernateService;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class Config {

    private static SessionFactory sessionFactory = sessionFactory();

    public static SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry registry = null;
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                registryBuilder.applySettings(prepareProperties());
                registry = registryBuilder.build();
                Configuration conf = new Configuration();
                conf.addAnnotatedClass(Student.class);
                conf.addAnnotatedClass(Author.class);
                conf.addAnnotatedClass(Book.class);
                conf.addAnnotatedClass(Computer.class);
                conf.addAnnotatedClass(City.class);
                conf.addAnnotatedClass(User.class);
                sessionFactory = conf.buildSessionFactory(registry);
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    private static Properties prepareProperties() {
        Properties prop = new Properties();
        try {
            prop.load(StudentHibernateService.class.getClass().getResourceAsStream("/dao.properties"));
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
