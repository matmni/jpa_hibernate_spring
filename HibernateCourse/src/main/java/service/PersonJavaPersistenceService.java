package service;

import entity.persistence.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class PersonJavaPersistenceService {

    @Autowired
    Person person;

    private EntityManager entityManager;

    public PersonJavaPersistenceService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("unit-test");
        entityManager = emfactory.createEntityManager();
    }

    public void test() {
        prepareNewPerson();
        System.out.println(person.getName() + " " + person.getAddress().getCity());
        addPerson();
//        modifyPerson();
//        deletePerson("Wojtek");
    }

    public void deletePerson(String name) {
        Person existingPerson = getPersonFromDbUsingJPQL(name);
        if (existingPerson != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(existingPerson);
            entityManager.getTransaction().commit();
        }
    }

    public void addPerson() {
        Person existingPerson = getPersonFromDb("Adam");
        if (existingPerson == null) {
            person.setName("Wojtek");
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
        }
    }

    public void modifyPerson() {
        Person existingPerson = getPersonFromDb("Adam");
        if (existingPerson != null) {
            existingPerson.setName("Michał");
            entityManager.getTransaction().begin();
            entityManager.merge(person);
            entityManager.getTransaction().commit();
        }
    }

    private Person getPersonFromDbUsingJPQL(String name) {
        Query query = entityManager.createQuery("select p from Person p where p.name = :name", Person.class);
        query.setParameter("name", name);
        return (Person) query.getSingleResult();
    }

    private Person getPersonFromDb(String name) {
        Person existingPerson = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        Root<Person> personRoot = query.from(Person.class);
        query.select(personRoot).where(criteriaBuilder.equal(personRoot.get("name"), name));
        TypedQuery<Person> typedQuery = entityManager.createQuery(query);
        try {
            existingPerson = typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No result found");
        }
        return existingPerson;
    }

    private void prepareNewPerson() {
        person.setName("Mati");
        person.setSurname("Mnich");
        person.getAddress().setCity("Kraków");
        person.getAddress().setStreet("Życzkowskiego");
        person.getAddress().setZipCode("22-333");
        person.getBillingAddress().setCity("Gliwice");
        person.getBillingAddress().setZipCode("44-100");
        person.getBillingAddress().setStreet("Główna");
    }
}
