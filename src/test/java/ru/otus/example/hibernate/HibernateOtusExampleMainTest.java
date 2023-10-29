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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ru.otus.example.hibernate.entities.City;

import java.lang.invoke.MethodHandles;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
public class HibernateOtusExampleMainTest {

    private static final Log LOGGER = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    private static SessionFactory sessionFactory = null;
    private Session session = null;

    @BeforeAll
    static void setup() {
        ResourceBundle.clearCache();
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate-test.cfg.xml")
            .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(City.class)
                .getMetadataBuilder()
            .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
            LOGGER.info("The SessionFactory has been created!");

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @AfterAll
    static void tear() {
        if (sessionFactory != null) {
            sessionFactory.close();
            LOGGER.info("The SessionFactory has been destroyed!");
        }
    }

    @BeforeEach
    void setupThis(){
        session = sessionFactory.openSession();
        LOGGER.info("The session has been created!");
    }

    @AfterEach
    void tearThis(){
        if (session != null) {
            session.close();
            LOGGER.info("The session has been closed!");
        }
    }

    @Test
    @Order(1)
    public void mustInsertNewCity() {

        LOGGER.info("Inserting the city ...");

        session.beginTransaction();

        City city = new City();
        city.setName("Chicago");

        session.persist(city);

        session.getTransaction().commit();

        assertEquals(1, city.getId());

    }

    @Test
    @Order(2)
    public void mustSelectExistingCity() {

        LOGGER.info("Retrieving the city ...");

        Integer id = 1;

        City city = session.find(City.class, id);

        assertEquals("Chicago", city.getName());

    }

    @Test
    @Order(3)
    public void mustUpdateExistingCity() {

        Integer id = 1;
        String updatedName = "Dallas";

        LOGGER.info("Updating the city ...");

        City city = session.find(City.class, id);
        city.setName(updatedName);

        session.beginTransaction();
        session.merge(city);
        session.getTransaction().commit();

        City updatedCity = session.find(City.class, id);

        assertEquals(updatedName, updatedCity.getName());
    }

    @Test
    @Order(4)
    public void mustDeleteExistingCity() {
        LOGGER.info("Deleting the city ...");

        Integer id = 1;
        City city = session.find(City.class, id);

        session.beginTransaction();
        session.remove(city);
        session.getTransaction().commit();

        City deletedCity = session.find(City.class, id);

        Assertions.assertNull(deletedCity);
    }

}