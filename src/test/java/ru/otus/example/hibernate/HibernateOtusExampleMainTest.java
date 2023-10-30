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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.invoke.MethodHandles;
import java.util.ResourceBundle;

class HibernateOtusExampleMainTest {

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


}