package ru.otus.example.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.otus.example.hibernate.entities.City;
import ru.otus.example.hibernate.entities.Email;

import java.lang.invoke.MethodHandles;

public class HibernateOtusExampleMainTest {

    private static final Log LOGGER = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    private static SessionFactory sessionFactory = null;
    private Session session = null;

    @BeforeAll
    static void setup(){
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate-test.cfg.xml")
            .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Email.class)
                .getMetadataBuilder()
            .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @BeforeEach
    void setupThis(){
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearThis(){
        session.getTransaction().commit();
    }

    @AfterAll
    static void tear(){
        sessionFactory.close();
    }

    @Test
    void mustInsertCityEntity() {
        City city = new City();
        city.setName("Chicago");
        Assertions.assertNull(city.getId());

        session.persist(city);
        LOGGER.info("The city with id : " + city.getId() + " has been inserted!");
        Assertions.assertNotNull(city.getId());
    }

    @Test
    void mustInsertEmailEntity() {
        Email email = new Email();
        email.setInbox("super@inbox.com");

        Assertions.assertNull(email.getId());

        session.persist(email);
        LOGGER.info("The email with id : " + email.getId() + " has been inserted!");
        Assertions.assertNotNull(email.getId());
    }

}