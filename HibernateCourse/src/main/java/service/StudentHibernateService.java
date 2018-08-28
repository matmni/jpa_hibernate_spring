package service;

import entity.hibernate.Author;
import entity.hibernate.Book;
import entity.hibernate.Computer;
import entity.hibernate.Student;
import entity.spring.User;
import hibernate.Config;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StudentHibernateService {

    @Autowired
    private Student student;
    private SessionFactory sessionFactory;

    public StudentHibernateService() {
        sessionFactory = Config.sessionFactory();
    }

    public void testOptimisticLocking() {

        Session session = sessionFactory.openSession();
        Session session2 = sessionFactory.openSession();

        Transaction t1 = session.beginTransaction();
        User user = (User) session.createCriteria(User.class).add(Restrictions.eq("id", 27L)).uniqueResult();
        user.setPortalName("test" + new Random().nextInt()); // hibernate nie zrobi update'u jak się nic nie zmieniło.
        Transaction t2 = session2.beginTransaction();
        session2.merge(user);
        t1.commit();
        t2.commit();
    }

    public void testNativyQuery() {
        Session session = sessionFactory.openSession();
        Query query = session.createNativeQuery("select c.name from City c left join Student s on (c.post_code = s.post_code) where s.id = :id");
        query.setParameter("id", 62L);
        System.out.println(query.getResultList());
    }

    public void testJpqlQuery() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select c.name from City c left join Student s on (c.postCode = s.postCode) where s.id = :id");
        query.setParameter("id", 62L);
        System.out.println(query.getResultList());
    }

    public Student testLazyLoading() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Student> students = session.createCriteria(Student.class).list();
        session.getTransaction().commit();
        session.close();
        ArrayList<Book> books = new ArrayList<>();
        books.addAll(students.get(0).getBooks());
        books.get(0).getAuthor().getName();

        return students.get(0);
    }

    public void testCache() {
        Session session = sessionFactory.openSession();
        List<Student> students = session.createCriteria(Student.class).add(Restrictions.eq("id", 62L)).list();

        Assert.isTrue(org.hibernate.Hibernate.isInitialized(students), "Should be true");
        Assert.isTrue(!org.hibernate.Hibernate.isInitialized(students.get(0).getBooks()), "Should be false");
        students.get(0).getBooks().size();
        Assert.isTrue(org.hibernate.Hibernate.isInitialized(students.get(0).getBooks()), "Should be true");
        System.out.println(students.size());


        // when I change data in DB hb won't ask to DB. But when I add session.clear() - cache will be empty.
        List<Student> students2 = session.createCriteria(Student.class).add(Restrictions.eq("id", 62L)).list(); // not use SQL - first lvl caching.

        // Hibernate does not cache persistent objects.
        Session session2 = sessionFactory.openSession();
        Session session3 = sessionFactory.openSession();
        List<Student> students3 = session2.createCriteria(Student.class).add(Restrictions.eq("id", 62L)).list();
        List<Student> students4 = session3.createCriteria(Student.class).add(Restrictions.eq("id", 62L)).list();
    }

    public void test() {
        prepareStudent();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();


    }

    private void prepareStudent() {
        student.setName("Mateusz");
        student.setSurname("Mnich");
        Author author = new Author();
        author.setName("testowy");
        author.setSurname("testowe");
        Book book = new Book();
        book.setTitle("Tytuł");
        book.setStudent(student);
        book.setAuthor(author);
        student.getBooks().add(book);
        book = new Book();
        book.setTitle("Tytuł2");
        book.setStudent(student);
        book.setAuthor(author);
        student.getBooks().add(book);
        Computer computer = new Computer();
        computer.setSerialNumber("SERIAL");
        computer.getStudents().add(student);
        computer.setDeviceName("Komputer Mateusza");
        computer.setLocalization("Kraków");
        student.getComputers().add(computer);
        Computer computer2 = new Computer();
        computer2.setSerialNumber("SERIAL");
        computer2.getStudents().add(student);
        student.getComputers().add(computer2);
    }


}
