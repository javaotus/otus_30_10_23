package ru.otus.example.hibernate;

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

public class HelloTest {

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
    void createSessionFactoryWithXML() {
        City city = new City();
        city.setName("Chicago");
        Assertions.assertNull(city.getId());

        session.persist(city);

        Assertions.assertNotNull(city.getId());
    }
}