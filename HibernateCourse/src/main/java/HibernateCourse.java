import configuration.spring.AppConfig;
import entity.hibernate.Book;
import entity.hibernate.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.PersonJavaPersistenceService;
import service.SpringDataService;
import service.StudentHibernateService;

import java.util.ArrayList;

public class HibernateCourse {

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext();

            context.register(AppConfig.class);
            context.refresh();
            PersonJavaPersistenceService personService = context.getBean(PersonJavaPersistenceService.class);
//        personService.setEntityManager((EntityManager) new Config().entityManagerFactory());
//        service.test();
            StudentHibernateService studentService = context.getBean(StudentHibernateService.class);
//            studentService.testLazyLoading();
//        studentService.test();
//        studentService.testNativyQuery();
//        testLazyLoading(studentService);
//            studentService.testCache();
            studentService.testOptimisticLocking();


            SpringDataService springDataService = context.getBean(SpringDataService.class);
//            springDataService.addUser();
//            System.out.println(springDataService.getUser(2L));
//            System.out.println(springDataService.getUser(2L));
//            springDataService.getNonDuplicates();
//            springDataService.optimistLockingFalseTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
