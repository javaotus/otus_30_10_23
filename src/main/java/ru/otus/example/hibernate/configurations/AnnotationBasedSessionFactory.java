package ru.otus.example.hibernate.configurations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.otus.example.hibernate.entities.Bank;

import java.lang.invoke.MethodHandles;

public class AnnotationBasedSessionFactory {

    private static final Log LOGGER = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    public static SessionFactory getAnnotationBasedSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("annotation-based/hibernate.cfg.xml");
            configuration.addAnnotatedClass(Bank.class);

            LOGGER.info("Hibernate Annotation Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
            .build();

            LOGGER.info("Hibernate Annotation serviceRegistry created");

            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}